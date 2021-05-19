package myRetail.service;

import org.springframework.stereotype.Component;

import myRetail.dao.Price;
import myRetail.dao.Product;
import myRetail.dao.ProductResourceObject;

@Component
public class ProductDAO {

	public Product resourceObject(ProductResourceObject product) {

		Product productsData = null;
		if (product != null) {
			productsData = new Product();

			productsData.setId(product.getProductId());
			productsData.setTitle(product.getTitle());

			Price priceData = new Price();
			priceData.setCurrencyCode(product.getCurrency_code());
			priceData.setPrice(product.getCurrentPrice());
			productsData.setPrice(priceData);
		}
		return productsData;
	}

	public ProductResourceObject resourceObject(Product productData) {
		ProductResourceObject productDao = new ProductResourceObject(productData.getId());
		productDao.setCurrentPrice(productData.getPrice().getPrice());
		return productDao;
	}

}