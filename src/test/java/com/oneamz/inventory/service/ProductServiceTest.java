package com.oneamz.inventory.service;

import com.oneamz.inventory.exception.InvalidDiscountPercentageException;
import com.oneamz.inventory.exception.ResourceNotFoundException;
import com.oneamz.inventory.model.Category;
import com.oneamz.inventory.model.Product;
import com.oneamz.inventory.repository.ProductRepository;
import com.oneamz.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProductTest() {
        Category category = new Category();
        category.setDescription("catdes");
        category.setId(1L);
        category.setName("catename");
        Product product = Product.builder().id(1L).category(category).description("des").name("p1").quantity(1).price(new BigDecimal(12)).build();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.createProduct(product);

        assertEquals(product, savedProduct);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getAllProductsTest() {
        List<Product> products = new ArrayList<>();

        Category category = new Category();
        category.setDescription("catdes");
        category.setId(1L);
        category.setName("catename");
        Product product1 = Product.builder().id(1L).category(category).description("des").name("p1").quantity(1).price(new BigDecimal(12)).build();
        Product product2 = Product.builder().id(1L).category(category).description("des").name("p1").quantity(1).price(new BigDecimal(12)).build();
       products.add(product1);
       products.add(product2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> retrievedProducts = productService.getAllProducts();

        assertEquals(products, retrievedProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductByIdTest() {
        Long productId = 1L;
        Product product = Product.builder().id(1L).category(null).description("des").name("p1").quantity(1).price(new BigDecimal(12)).build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product retrievedProduct = productService.getProductById(productId);

        assertEquals(product, retrievedProduct);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductByIdNotFoundTest() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
    }

    // ... (Test cases for other methods)

    @Test
    void updateProductTest() {
        Long productId = 1L;
        Product existingProduct = Product.builder().id(1L).category(null).description("des").name("p1").quantity(1).price(new BigDecimal(12)).build();
        Product updatedProductDetails = Product.builder().id(1L).category(null).description("des").name("p2").quantity(2).price(new BigDecimal(3)).build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProductDetails);

        Product updatedProduct = productService.updateProduct(productId, updatedProductDetails);

        assertEquals(updatedProductDetails, updatedProduct);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }
    @Test
    void updateProductNotFoundTest() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, new Product()));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }
    @Test
    void deleteProductTest() {
        Long productId = 1L;
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }
    @Test
    void deleteProductNotFoundTest() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).deleteById(productId);
    }
    @Test
    void calculateTotalInventoryValueTest() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id(1L).category(null).description("des1").name("p1").quantity(1).price(new BigDecimal(12)).build());
        products.add(Product.builder().id(1L).category(null).description("des2").name("p2").quantity(2).price(new BigDecimal(3)).build());
        when(productRepository.findAll()).thenReturn(products);

        BigDecimal totalValue = productService.calculateTotalInventoryValue();

        assertEquals(BigDecimal.valueOf(525), totalValue);
    }
    @Test
    void applyDiscountTest() {
        Long productId = 1L;
        Product product = Product.builder().id(1L).category(null).description("des").name("p1").quantity(1).price(new BigDecimal(100)).build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        Product discountedProduct = productService.applyDiscount(productId, BigDecimal.valueOf(10));

        assertEquals(BigDecimal.valueOf(90.0), discountedProduct.getPrice());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void applyDiscountInvalidPercentageTest() {
        Long productId = 1L;

        assertThrows(InvalidDiscountPercentageException.class, () -> productService.applyDiscount(productId, BigDecimal.valueOf(-10)));

}



}
