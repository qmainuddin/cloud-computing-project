package com.merhawifissehaye;

import com.merhawifissehaye.model.Product;
import com.merhawifissehaye.model.User;
import com.merhawifissehaye.repository.UserRepository;
import com.merhawifissehaye.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest extends BaseTest {
    @MockBean
    private ProductService productService;

    @Test
    public void nonAuthenticatedUserCannotGetProduct() throws Exception {
//        mockMvc.perform(get("/api/products/1"))
//                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetProduct() throws Exception {
//        signIn();
//
//        var product = Product.builder().id(1L).name("Product 1").build();
//        when(productService.getProductById(1L)).thenReturn(product);
//        mockMvc.perform(get("/api/products/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @Test
    public void nonAuthenticatedUserCannotGetProductList() throws Exception {
//        mockMvc.perform(get("/api/products"))
//                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetProductList() throws Exception {
//        signIn();
//
//        var products = Arrays.stream(new long[10]).mapToObj(id -> Product.builder().id(id).build()).collect(Collectors.toList());
//        when(productService.getProducts(null, 1, 10, "test@example.com")).thenReturn(new PageImpl<>(products));
//        mockMvc.perform(get("/api/products"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }

    // test authenticated users can search by product name
    @Test
    public void testGetProductListByName() throws Exception {
//        signIn();
//
//        var product1 = Product.builder().id(1L).name("Product 1").build();
//        var product2 = Product.builder().id(2L).name("Product 2").build();
//
//        var products = List.of(product1, product2);
//        when(productService.getProducts("Product", 1, 10, "test@example.com")).thenReturn(new PageImpl<Product>(List.of(product1, product2)));
//        mockMvc.perform(get("/api/products?q=Product"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }

    @Test
    public void testAuthenticatedUserCanCreateProduct() throws Exception {
//        User user = signIn();
//
//        var product = Product.builder().id(1L).name("Product 1").description("Product Description").build();
//        when(productService.createProduct(product)).thenReturn(product);
//        var request = mockMvc.perform(post("/api/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(product))
//        );
//        var body = request.andReturn();
//        var response = body.getResponse();
//        request.andExpect(status().isCreated());
//                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }
}
