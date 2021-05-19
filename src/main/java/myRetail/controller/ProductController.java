package myRetail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import myRetail.dao.Product;
import myRetail.dao.ProductResourceObject;
import myRetail.service.ProductDAO;
import myRetail.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductDAO productDAO;

	@GetMapping(path = "/test", produces = "application/json")
	public String pingTest() {
		return "Product API service is up";
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Product> getProductDetailsById(@PathVariable long id) throws Exception {
		logger.debug("ProductController.getProductDetailsById() :: Invoked");
		return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
	}

	@PutMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Product> updateProductPriceById(@RequestBody Product productRequest, @PathVariable long id) {
		logger.debug("ProductController.updateProductPriceById() :: Invoked");
		ProductResourceObject productRO = productDAO.resourceObject(productRequest);		
		return new ResponseEntity<>(productService.updateProductPrice(productRO), HttpStatus.OK);
	}
}
