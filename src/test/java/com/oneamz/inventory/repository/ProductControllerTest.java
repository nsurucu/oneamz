package com.oneamz.inventory.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneamz.inventory.controller.ProductController;
import com.oneamz.inventory.model.Product;
import com.oneamz.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    public void createProduct_shouldReturnCreatedProduct() throws Exception {
        Product productToCreate = Product.builder().category(null).description("des").name("p1").quantity(1).price(new BigDecimal(12)).build();
        Product createdProduct  = Product.builder().id(1L).category(null).description("des").name("p1").quantity(1).price(new BigDecimal(12)).build();;
        when(productService.createProduct(any())).thenReturn(createdProduct);

        mockMvc.perform(post("/api/inventory/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productToCreate)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", equalTo("p1")))
                .andExpect(jsonPath("$.quantity", equalTo(1)));
    }


    @Test
    public void getAllProducts_shouldReturnAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                Product.builder().id(1L).category(null).description("des").name("p1").quantity(1).price(new BigDecimal(12)).build(),
                Product.builder().id(2L).category(null).description("des2").name("p2").quantity(2).price(new BigDecimal(22)).build()
        );
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/inventory/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("p1")))
                .andExpect(jsonPath("$[0].quantity", equalTo(1)))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].name", equalTo("p2")))
                .andExpect(jsonPath("$[1].quantity", equalTo(2)));
    }

    @Test
    public void deleteProduct_shouldDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/inventory/products/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(anyLong());
    }

}
