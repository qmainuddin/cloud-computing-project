package com.merhawifissehaye.dto;

import com.merhawifissehaye.model.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreateProductDto {
    @NotNull(message = "name is required")
    @NotEmpty(message = "name is required")
    private String name;
    @NotNull(message = "description is required")
    @NotEmpty(message = "description is required")
    private String description;
    @NotEmpty(message = "categories is required")
    private List<String> categories;

    public Product toProduct() {
        return Product.builder()
                .name(name)
                .description(description)
                .build();
    }

    public List<Long> getCategoryIds() {
        return categories.stream().mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
    }
}
