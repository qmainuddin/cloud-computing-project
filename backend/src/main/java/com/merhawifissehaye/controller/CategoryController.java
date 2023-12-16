package com.merhawifissehaye.controller;

import com.merhawifissehaye.model.Category;
import com.merhawifissehaye.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@CrossOrigin
public class CategoryController {
    CategoryRepository categoryRepository;

    @GetMapping("")
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
