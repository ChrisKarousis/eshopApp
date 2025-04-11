package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.Category;
import com.ckarousis.eshopapp.model.Product;
import com.ckarousis.eshopapp.repository.CategoryRepository;
import com.ckarousis.eshopapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> productOptional = getProduct(id);
        if (productOptional.isPresent()) {
            Product p = productOptional.get();
            p.setName(product.getName());
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            p.setStock(product.getStock());
            return productRepository.save(p);
        } else {
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
