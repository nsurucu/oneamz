package com.oneamz.inventory.repository;

import com.oneamz.inventory.model.Category;
import com.oneamz.inventory.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setName("Test Category");
        category.setDescription("This is a test category");
    }

    @Test
    public void testCreateCategory() {
        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isGreaterThan(0);
        assertThat(savedCategory.getName()).isEqualTo("Test Category");
        assertThat(savedCategory.getDescription()).isEqualTo("This is a test category");
    }

    @Test
    public void testFindAllCategories() {
        Category savedCategory = categoryRepository.save(category);

        List<Category> categories = categoryRepository.findAll();

        assertThat(categories).isNotEmpty();
        assertThat(categories).contains(savedCategory);
    }

    @Test
    public void testFindCategoryById() {
        Category savedCategory = categoryRepository.save(category);

        Category foundCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getId()).isEqualTo(savedCategory.getId());
        assertThat(foundCategory.getName()).isEqualTo("Test Category");
        assertThat(foundCategory.getDescription()).isEqualTo("This is a test category");
    }

    @Test
    public void testUpdateCategory() {
        Category savedCategory = categoryRepository.save(category);

        savedCategory.setName("Updated Test Category");
        savedCategory.setDescription("This is an updated test category");

        Category updatedCategory = categoryRepository.save(savedCategory);

        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getId()).isEqualTo(savedCategory.getId());
        assertThat(updatedCategory.getName()).isEqualTo("Updated Test Category");
        assertThat(updatedCategory.getDescription()).isEqualTo("This is an updated test category");
    }

    @Test
    public void testDeleteCategory() {
        Category savedCategory = categoryRepository.save(category);

        categoryRepository.deleteById(savedCategory.getId());

        Category deletedCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);

        assertThat(deletedCategory).isNull();
    }
}
