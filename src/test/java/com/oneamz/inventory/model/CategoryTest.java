package com.oneamz.inventory.model;

import com.oneamz.inventory.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String name = "Electronics";
        String description = "All electronic devices";
        Category category = new Category(id, name, description);

        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
    }

    @Test
    void testRequiredArgsConstructor() {
        String name = "Books";
        String description = "Books desc";
        Long id = 1L;
        Category category = new Category(id,name,description);

        assertNotNull(category.getId());
        assertEquals(name, category.getName());
        assertNotNull(category.getDescription());
    }

    @Test
    void testGettersAndSetters() {
        Category category = new Category();
        category.setId(2L);
        category.setName("Clothing");
        category.setDescription("Fashion wear");

        assertEquals(2L, category.getId());
        assertEquals("Clothing", category.getName());
        assertEquals("Fashion wear", category.getDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        Category category1 = new Category(1L, "Food", "Groceries");
        Category category2 = new Category(1L, "Food", "Groceries");
        Category category3 = new Category(2L, "Food", "Groceries");

        assertEquals(category1, category2);
        assertNotEquals(category1, category3);
        assertEquals(category1.hashCode(), category2.hashCode());
        assertNotEquals(category1.hashCode(), category3.hashCode());
    }

    @Test
    void testBuilder() {
        Category category = Category.builder()
                .id(3L)
                .name("Sports")
                .description("Sports equipment")
                .build();

        // Getter'lar ile değerlerin doğru atandığını kontrol edin
        assertEquals(3L, category.getId());
        assertEquals("Sports", category.getName());
        assertEquals("Sports equipment", category.getDescription());
    }


}
