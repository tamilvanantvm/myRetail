package myRetail.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import myRetail.dao.ProductResourceObject;

@Repository("ProductRepository")
public interface ProductRepository extends MongoRepository<ProductResourceObject, String> {

	@Query("{ 'pid' : ?0 }")
	public ProductResourceObject findByProductId(long pid);

	@SuppressWarnings("unchecked")
	public ProductResourceObject save(ProductResourceObject productDao);

}