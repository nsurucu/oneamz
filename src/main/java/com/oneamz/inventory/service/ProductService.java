package com.oneamz.inventory.service;

import com.oneamz.inventory.exception.InvalidDiscountPercentageException;
import com.oneamz.inventory.exception.ResourceNotFoundException;
import com.oneamz.inventory.model.Product;
import com.oneamz.inventory.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;



    @Transactional
    public Product createProduct(Product product) {
        logger.info("Creating a new product: {}", product);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        logger.info("Retrieving all products");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        logger.info("Retrieving product with ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product updateProduct(Long id, Product productDetails) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            product.setDescription(productDetails.getDescription());
            product.setQuantity(productDetails.getQuantity());
            return productRepository.save(product);
        } catch (ResourceNotFoundException e) {
            logger.error("Product not found with id: {}", id);
            throw e;
        }
    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            logger.error("Product not found with id: {}", id);
            throw  e;
        }
    }



    public BigDecimal calculateTotalInventoryValue() {
        logger.info("Calculating total inventory value");
        List<Product> products = getAllProducts();
        BigDecimal totalValue = BigDecimal.ZERO;
        for (Product product : products) {
            totalValue = totalValue.add(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        }
        return totalValue;
    }

    public Product applyDiscount(Long productId, BigDecimal discountPercentage) {
        try {
            if (discountPercentage.compareTo(BigDecimal.ZERO) < 0) {
                throw new InvalidDiscountPercentageException("Discount percentage must be between 0 and 100: " + discountPercentage);
            }
            Product product = getProductById(productId);
            BigDecimal discountAmount = product.getPrice().multiply(discountPercentage.divide(BigDecimal.valueOf(100)));
            product.setPrice(product.getPrice().subtract(discountAmount));
            return productRepository.save(product);
        } catch (ResourceNotFoundException e) {
            logger.error("Product not found with id: {}", productId);
            throw e;
        } catch (InvalidDiscountPercentageException e) {
            logger.error("Invalid discount percentage: {}", discountPercentage);
            throw e;
        }
    }

}
