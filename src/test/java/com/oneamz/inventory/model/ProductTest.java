package com.oneamz.inventory.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String name = "Laptop";
        BigDecimal price = new BigDecimal("1200");
        String description = "Powerful laptop with 16GB RAM and 512GB SSD";
        Integer quantity = 10;
        Category category = Category.builder().id(2L).name("Electronics").build();

        Product product = new Product(id, name, price, description, quantity, category);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(description, product.getDescription());
        assertEquals(quantity, product.getQuantity());
        assertEquals(category, product.getCategory());
    }

    @Test
    void testRequiredArgsConstructor() {
        String name = "Smartphone";
        BigDecimal price = new BigDecimal("800");
        Integer quantity = 5;

        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);

        assertNull(product.getId());
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertNull(product.getDescription());
        assertEquals(quantity, product.getQuantity());
        assertNull(product.getCategory());
    }

    @Test
    void testGettersAndSetters() {
        Product product = new Product();
        product.setId(3L);
        product.setName("Tablet");
        product.setPrice(new BigDecimal("500"));
        product.setDescription("Android tablet with 10 inch display");
        product.setQuantity(2);
        Category category = Category.builder().id(3L).name("Computers").build();
        product.setCategory(category);

        assertEquals(3L, product.getId());
        assertEquals("Tablet", product.getName());
        assertEquals(new BigDecimal("500"), product.getPrice());
        assertEquals("Android tablet with 10 inch display", product.getDescription());
        assertEquals(2, product.getQuantity());
        assertEquals(category, product.getCategory());
    }

    @Test
    void testEqualsAndHashCode() {
        Product product1 = new Product(1L, "Headphones", new BigDecimal("100"), "Wireless headphones with Bluetooth", 20, null);
        Product product2 = new Product(1L, "Headphones", new BigDecimal("100"), "Wireless headphones with Bluetooth", 20, null);
        Product product3 = new Product(2L, "Speakers", new BigDecimal("200"), "Powerful speakers with subwoofer", 10, null);

        assertEquals(product1, product2);
        assertNotEquals(product1, product3);
        assertEquals(product1.hashCode(), product2.hashCode());
        assertNotEquals(product1.hashCode(), product3.hashCode());
    }

    @Test
    void testBuilder() {
        Product product = Product.builder()
            .id(4L)
            .name("Smartwatch")
            .price(new BigDecimal("300"))
            .description("Smartwatch with fitness tracking and GPS")
            .quantity(15)
            .build();

        assertEquals(4L, product.getId());
        assertEquals("Smartwatch", product.getName());
        assertEquals(new BigDecimal("300"), product.getPrice());
        assertEquals("Smartwatch with fitness tracking and GPS", product.getDescription());
        assertEquals(15, product.getQuantity());
        assertNull(product.getCategory());
    }

    @Test
    void testCategoryRelationship() {
        Category category = Category.builder().id(1L).name("Electronics").build();
        Product product = Product.builder()
            .name("Laptop")
            .price(new BigDecimal("1200"))
            .category(category)
            .build();

        assertEquals(category, product.getCategory());
    }

    // ... diğer testler ...
}
