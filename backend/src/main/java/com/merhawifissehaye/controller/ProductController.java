package com.merhawifissehaye.controller;

import com.merhawifissehaye.dto.CreateProductDto;
import com.merhawifissehaye.model.BidProposal;
import com.merhawifissehaye.model.Product;
import com.merhawifissehaye.model.User;
import com.merhawifissehaye.repository.BidProposalRepository;
import com.merhawifissehaye.repository.CategoryRepository;
import com.merhawifissehaye.repository.UserRepository;
import com.merhawifissehaye.service.ProductService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/products")
public class ProductController {
    ProductService productService;
    CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private BidProposalRepository bidProposalRepository;

    @GetMapping("/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("")
    @ResponseBody
    public Page<Product> getProducts(@RequestParam(name = "q", required = false) String query, @RequestParam(name = "page", required = false) int page, @AuthenticationPrincipal String email) {
        return productService.getProducts(query, page, 10, email);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductDto productDto, @AuthenticationPrincipal String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        var product = productDto.toProduct();
        product.setOwner(user);
        product.setCategories(
                categoryRepository.findAllById(productDto.getCategoryIds())
        );
        product = productService.createProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> saveProduct(@PathVariable Long id, @Valid @RequestBody CreateProductDto productDto, @AuthenticationPrincipal String email) {
        Product product = productService.getProductById(id);
        var productData = productDto.toProduct();
        if (!product.getOwner().getEmail().equals(email)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        product.setName(productData.getName());
        product.setDescription(productData.getDescription());
        product.setCategories(
                categoryRepository.findAllById(productDto.getCategoryIds())
        );
        product = productService.updateProduct(product);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/publish/{id}")
    @ResponseBody
    public ResponseEntity<BidProposal> publishProduct(@PathVariable Long id, @RequestBody BidProposal bidProposal, @AuthenticationPrincipal String email) {
        Product product = productService.getProductById(id);
        if (!product.getOwner().getEmail().equals(email)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(bidProposalRepository.countByProductId(id) > 0) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
//        if (!product.getBidProposal().isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }

        bidProposal.setProduct(product);
        bidProposal = productService.createBidProposal(bidProposal);
        return new ResponseEntity<>(bidProposal, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id, @AuthenticationPrincipal String email) {
        Product product = productService.getProductById(id);
        if (!product.getOwner().getEmail().equals(email)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
