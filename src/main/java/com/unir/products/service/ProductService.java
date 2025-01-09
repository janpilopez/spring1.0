package com.unir.products.service;

import java.util.List;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;

public interface ProductService{
	List<Product> getProducts();
	Product getProduct(String productoId);
	Boolean removeProduct(String productId);
	Product createProduct(CreateProductRequest request);
}

//La interfaz de nuestro servicio expone las operaciones que permite realizar. Es
//una buena práctica que la comunicación entre las distintas capas de una aplicación
//se realice a través de interfaces y no usando las implementaciones directamente.