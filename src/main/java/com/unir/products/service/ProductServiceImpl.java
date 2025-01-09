package com.unir.products.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unir.products.data.ProductRepository;
import com.unir.products.model.pojo.Product;

@Service
public class ProductServiceImpl implements ProductService {
@Autowired

private ProductRepository repository;

@Override
public List<Product> getProducts() {
 List<Product> products = repository.findAll();
 return products.isEmpty() ? null : products:
}

@Override
public Product getProduct(String productId) {
 return repository.findById(Long.valueOf(productId)).orElse(null);
}

@Override
public Boolean removeProduct(String productId) {
Product product =
repository.findById(Long.valueOf(productId)).orElse(null);
if (product != null) {
repository.delete(product);
return Boolean.TRUE;
} else {
return Boolean.FALSE;
}
}
@Override
public Product createProduct(CreateProductRequest request) {
if (request != null &&amp;
StringUtils.hasLength(request.getName().trim())
&&amp;
StringUtils.hasLength(request.getDescription().trim())
&&amp;
StringUtils.hasLength(request.getCountry().trim()) &&amp;
request.getVisible () != null) {
Product product =
Product.builder().name(request.getName()).description(request.getDescription()).
country(request.getCountry()).visible(request.getVisible()).build();
return repository.save(product);
} else {
return null;
}
}