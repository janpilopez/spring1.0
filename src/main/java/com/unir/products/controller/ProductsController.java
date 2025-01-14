package com.unir.products.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;
import com.unir.products.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {
	
	private final ProductService service ;

	//El constructor requiredargscontrollerl o genera, no es necesario
	//public ProductsController(ProductService service) {
	//	this.service = service;
	//}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(@RequestHeader Map<String, String> headers) {
		//log.info("headers: {}",headers);
		List<Product> products = service.getProducts();
		
        //Product producto = new Product();
        //producto.setName("Sample Product"); // Asignar el nombre
        //producto.setCountry("USA"); // Asignar el país
        //producto.setDescription("This is a sample product."); // Asignar la descripción
        //producto.setVisible(true); // Asignar si está visible
        
        //List<Product> singleProductList = new ArrayList<>();
        //singleProductList.add(products);
		if (products != null) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.ok(Collections.emptyList());
		}
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProducts(@PathVariable String productId) {
		//log.info("Request received for product", productId);
		Product products = service.getProduct(productId);
		if (products != null) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/products/{productId}")

	public ResponseEntity<Void> deleteProducts(@PathVariable String productId) {
		Boolean removed = service.removeProduct(productId);
		if (Boolean.TRUE.equals(removed)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/products")

	public ResponseEntity<Product> getProduct(@RequestBody CreateProductRequest request) {
		Product createdProduct = service.createProduct(request);
		if (createdProduct !=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
		} else {
			return ResponseEntity.badRequest ().build();
		}
	}
}