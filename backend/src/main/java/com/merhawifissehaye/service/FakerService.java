package com.merhawifissehaye.service;

import com.github.javafaker.Faker;
import com.merhawifissehaye.model.Category;
import com.merhawifissehaye.model.Product;
import com.merhawifissehaye.model.User;
import com.merhawifissehaye.repository.CategoryRepository;
import com.merhawifissehaye.repository.ProductRepository;
import com.merhawifissehaye.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FakerService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public static final Faker faker = new Faker(Locale.US);

    public Iterable<Product> createProducts() {
        if (productRepository.count() > 0) return List.of();

        List<Product> products = Arrays.stream(new long[100]).mapToObj(id -> Product.builder().id(id).
                name(faker.commerce().productName())
                .description(faker.lorem().sentence(10))
                .owner(userRepository.findById(1L).get())
                .build()).collect(Collectors.toList());
        return productRepository.saveAll(products);
    }

    public List<Category> createCategories() {
        if (categoryRepository.count() > 0) return List.of();
        List<Category> categories = Arrays.stream(new long[20]).mapToObj(id ->
                        Category.builder().id(id).name(faker.commerce().department())
                                .description(faker.lorem().sentence(10)).build())
                .collect(Collectors.toList());
        return categoryRepository.saveAll(categories);
    }

    public User createUser() {
        if (userRepository.count() > 0) return null;
        User user = User.builder().id(1L)
                .email("test@example.com")
                .password(passwordEncoder.encode("Password1234"))
                .licenseNumber("ABCD123456").name("Test User").build();
        return userRepository.save(user);
    }
}
