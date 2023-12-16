package com.merhawifissehaye.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<Product> products;
}
