package com.unir.products.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.unir.products.data.ProductRepository;
import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;

@Service//Esta anotación le indica al contenedor de Spring de que esta clase es un bean
//del nivel de servicio Es decir, debe ser gestionada por su contenedor. Además,como no estamos especiﬁcando ningún tipo de scope, se usará el que viene por
//omisión, singleton (al igual que con ProductRepository).
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;//Con esa dependencia le estamos indicando al contenedor de Spring
    //que debe tratar de buscar un bean que implemente la interfaz ProductRepository y
    //que, si lo encuentra, le asigne su valor a la variable repository.

    @Override
    public List<Product> getProducts() {
    List<Product> products = repository.findAll();
        return products.isEmpty() ? null : products;
    }

    @Override
    public Product getProduct(String productId) {
        return repository.findById(Long.valueOf(productId)).orElse(null);
    }

    @Override
    public Boolean removeProduct(String productId) {
        Product product = repository.findById(Long.valueOf(productId)).orElse(null);
        if (product != null) {
            repository.delete(product);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
        if (request != null && StringUtils.hasLength(request.getName().trim()) &&
            StringUtils.hasLength(request.getDescription().trim()) &&
            StringUtils.hasLength(request.getCountry().trim()) &&
            request.getVisible () != null) {
                
            Product product = Product.builder().name(request.getName()).description(request.getDescription()).
            country(request.getCountry()).visible(request.getVisible()).build();
            return repository.save(product);

        } else {
            return null;
        }
    }
}

