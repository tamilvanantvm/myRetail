package myRetail.service;

import myRetail.dao.Product;
import myRetail.dao.ProductResourceObject;

public interface ProductService {

	Product getProductById(long id) throws Exception;

	Product updateProductPrice(ProductResourceObject product);

}
