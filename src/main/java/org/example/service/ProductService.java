package org.example.service;

import org.example.entity.ProductEntity;
import org.example.model.Product;
import org.example.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final Function<ProductEntity,Product> toRecord = entity -> new Product(entity.getId(),entity.getName(), entity.getPrice());
    private static final Function<Product,ProductEntity> toEntity = product -> new ProductEntity(product.id(),product.name(), product.price());

    @Cacheable(value = "products", key = "#id", unless = "#result==null")
    public Product getProduct(Long id) {
        System.out.println("getting product from db : "+id);
        return productRepository.findById(id).map(toRecord).orElse(null);
    }

    @CachePut(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        System.out.println("saving product to db : "+product.id());
        productRepository.save(toEntity.apply(product));
        return product;
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        System.out.println("deleting product from db : "+id);
        productRepository.deleteById(id);
    }
}