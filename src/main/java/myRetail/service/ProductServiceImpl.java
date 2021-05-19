package myRetail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import myRetail.dao.Product;
import myRetail.dao.ProductRepository;
import myRetail.dao.ProductResourceObject;
import myRetail.exception.ProductIDNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	private static final String HOST = "https://redsky.target.com/v3/pdp/tcin/";
	private static final String EXCLUDES_PATH = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate";

	@Autowired
	private ProductDAO productDAO;

	private ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	RestTemplate restfulTemplate = new RestTemplate();

	public String getURIPath(long productId) {
		return HOST + productId + EXCLUDES_PATH;
	}

	public Product getProductById(long id) throws JsonMappingException, JsonProcessingException {

		logger.debug("ProductServiceImpl.getProductById():: Start");

		ProductResourceObject product = productRepository.findByProductId(id);

		if (product == null) {
			logger.error("ProductServiceImpl.getProductById():: Exception-" + "ProductId is not exist -" + id);
			throw new ProductIDNotFoundException("Product Id - "+ id +" does not exist in database");
		}

		String productTitle = getProductTitle(id);

		product.setTitle(productTitle);

		logger.debug("ProductServiceImpl.getProductById():: end");

		return productDAO.resourceObject(product);
	}

	@ExceptionHandler(Exception.class)
	private String getProductTitle(long productId) throws JsonMappingException, JsonProcessingException {

		logger.debug("ProductServiceImpl.getProductTitle():: Start");

		String responseBody = null;
		ResponseEntity<String> response = null;
		String productTitle = null;

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getURIPath(productId)).build();

		response = restfulTemplate.getForEntity(uriComponents.encode().toUri(), String.class);
		responseBody = response.getBody();

		ObjectMapper mapper = new ObjectMapper();

		JsonNode productRootNode = mapper.readTree(responseBody);

		if (productRootNode != null)
			productTitle = productRootNode.get("product").get("item").get("product_description").get("title").asText();

		logger.debug("ProductServiceImpl.getProductTitle():: End");

		return productTitle;
	}

	public Product updateProductPrice(ProductResourceObject product) {

		logger.debug("ProductServiceImpl.updateProductPrice():: Satrt");

		long productId = product.getProductId();

		
		ProductResourceObject existingProduct = productRepository.findByProductId(productId);

		if (existingProduct == null) {
			throw new ProductIDNotFoundException("Product id - "+ productId +" does not exist in database");

		}

		existingProduct.setCurrentPrice(product.getCurrentPrice());

		
		ProductResourceObject updatedProduct = productRepository.save(existingProduct);

		logger.debug("ProductServiceImpl.updateProductPrice():: end");

		return productDAO.resourceObject(updatedProduct);
	}
}
