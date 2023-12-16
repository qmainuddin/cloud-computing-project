package com.merhawifissehaye.service;


import com.merhawifissehaye.model.BidProposal;
import com.merhawifissehaye.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    public Product getProductById(Long id);

    Page<Product> getProducts(String name, int pageNumber, int pageSize, String ownerEmail);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    BidProposal createBidProposal(BidProposal bidProposal);

    void deleteProduct(long id);
}
