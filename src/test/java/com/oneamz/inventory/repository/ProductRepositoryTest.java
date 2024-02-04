package com.oneamz.inventory.repository;

import com.oneamz.inventory.model.Category;
import com.oneamz.inventory.model.Product;
import com.oneamz.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        Category category = new Category();
        category.setName("Test Category");

        product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setDescription("This is a test product");
        product.setQuantity(10);
        product.setCategory(category);
    }

    @Test
    public void testCreateProduct() {
        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
        assertThat(savedProduct.getPrice()).isEqualTo(BigDecimal.valueOf(10.00));
        assertThat(savedProduct.getDescription()).isEqualTo("This is a test product");
        assertThat(savedProduct.getQuantity()).isEqualTo(10);
        assertThat(savedProduct.getCategory()).isNotNull();
    }

    @Test
    public void testFindAllProducts() {
        productRepository.save(product);

        List<Product> products = productRepository.findAll();

        assertThat(products).isNotEmpty();
        assertThat(products).contains(product);
    }

    @Test
    public void testFindProductById() {
        Product savedProduct = productRepository.save(product);

        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(savedProduct.getId());
        assertThat(foundProduct.getName()).isEqualTo("Test Product");
        assertThat(foundProduct.getPrice()).isEqualTo(BigDecimal.valueOf(10.00));
        assertThat(foundProduct.getDescription()).isEqualTo("This is a test product");
        assertThat(foundProduct.getQuantity()).isEqualTo(10);
        assertThat(foundProduct.getCategory()).isNotNull();
    }

    @Test
    public void testUpdateProduct() {
        Product savedProduct = productRepository.save(product);

        savedProduct.setName("Updated Test Product");
        savedProduct.setPrice(BigDecimal.valueOf(15.00));
        savedProduct.setDescription("This is an updated test product");
        savedProduct.setQuantity(20);

        Product updatedProduct = productRepository.save(savedProduct);

        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getId()).isEqualTo(savedProduct.getId());
        assertThat(updatedProduct.getName()).isEqualTo("Updated Test Product");
        assertThat(updatedProduct.getPrice()).isEqualTo(BigDecimal.valueOf(15.00));
        assertThat(updatedProduct.getDescription()).isEqualTo("This is an updated test product");
        assertThat(updatedProduct.getQuantity()).isEqualTo(20);
    }

    @Test
    public void testDeleteProduct() {
        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());

        Product deletedProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        assertThat(deletedProduct).isNull();
    }
}
