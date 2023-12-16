package com.merhawifissehaye.repository;

import com.merhawifissehaye.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, CrudRepository<Product, Long> {
    Page<Product> findByNameContaining(String name, PageRequest pageRequest);
    Page<Product> findByOwnerEmail(String email, PageRequest pageRequest);
}
