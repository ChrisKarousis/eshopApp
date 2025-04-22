package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.Product;
import com.ckarousis.eshopapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eshop/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getproduct(@PathVariable Long id) {
        return productService.getProduct(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    @PutMapping("/{id}/update-image")
    public ResponseEntity<Product> updateProductImage(@PathVariable("id") Long id,
                                                      @RequestParam("image") String imageUrl) {
        Product updatedProduct = productService.updateImage(id, imageUrl);
        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping
    public Product createproduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
