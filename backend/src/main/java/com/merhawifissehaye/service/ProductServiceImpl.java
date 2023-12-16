package com.merhawifissehaye.service;

import com.merhawifissehaye.model.BidProposal;
import com.merhawifissehaye.model.Product;
import com.merhawifissehaye.repository.BidAuctionRepository;
import com.merhawifissehaye.repository.BidProposalRepository;
import com.merhawifissehaye.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    BidProposalRepository bidProposalRepository;
    BidAuctionRepository bidAuctionRepository;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<Product> getProducts(String name, int pageNumber, int pageSize, String ownerEmail) {
        Sort sort = Sort.by("id").descending();
        PageRequest paging = PageRequest.of(pageNumber, pageSize, sort);
        if (name != null && !name.isEmpty()) return productRepository.findByNameContaining(name, paging);
        return productRepository.findByOwnerEmail(ownerEmail, paging);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public BidProposal createBidProposal(BidProposal bidProposal) {
        return bidProposalRepository.save(bidProposal);
    }

    @Override
    @Transactional
    public void deleteProduct(long id) {
        bidAuctionRepository.deleteByBidProposalProductId(id);
        bidProposalRepository.deleteAllByProductId(id);
        productRepository.deleteById(id);
    }
}
