package com.unir.products.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;
import com.unir.products.moduloReporte.service.ReportGeneratorService;
import com.unir.products.service.ProductService;
import com.unir.products.util.ReportGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {

	private final ProductService service;

	@Autowired
	private final ReportGenerator reporteService;

	@Autowired
	private final ReportGeneratorService reportGeneratorService;
	// public ProductsController(ProductService service) {
	// this.service = service;
	// }

	@GetMapping("/generate")
	public ResponseEntity<ByteArrayResource> generateReport(@RequestParam int idCli, @RequestParam int idOrden)
			throws JRException, IOException {
		// Llamamos al servicio para generar la factura PDF
		return reportGeneratorService.exportInvoice(idCli, idOrden);
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(@RequestHeader Map<String, String> headers) {
		// log.info("headers: {}",headers);
		List<Product> products = service.getProducts();

		// Product producto = new Product();
		// producto.setName("Sample Product"); // Asignar el nombre
		// producto.setCountry("USA"); // Asignar el país
		// producto.setDescription("This is a sample product."); // Asignar la
		// descripción
		// producto.setVisible(true); // Asignar si está visible

		// List<Product> singleProductList = new ArrayList<>();
		// singleProductList.add(products);
		if (products != null) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.ok(Collections.emptyList());
		}
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProducts(@PathVariable String productId) {
		// log.info("Request received for product", productId);
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
		if (createdProduct != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}