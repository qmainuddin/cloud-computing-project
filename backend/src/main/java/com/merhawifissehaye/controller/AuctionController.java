package com.merhawifissehaye.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merhawifissehaye.dto.CreateBidProposalDto;
import com.merhawifissehaye.dto.DoBidDto;
import com.merhawifissehaye.model.BidAuction;
import com.merhawifissehaye.model.BidProposal;
import com.merhawifissehaye.model.User;
import com.merhawifissehaye.repository.BidAuctionRepository;
import com.merhawifissehaye.repository.UserRepository;
import com.merhawifissehaye.service.AuctionService;
import com.merhawifissehaye.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/auctions")
@AllArgsConstructor
@CrossOrigin
@RestController
public class AuctionController {
    private AuctionService auctionService;
    private ProductService productService;
    private UserRepository userRepository;
    private BidAuctionRepository auctionRepository;
    private ObjectMapper objectMapper;

    // get my auctions
    @GetMapping("/mine")
    public ResponseEntity<Iterable<BidAuction>> getMyAuctions(@AuthenticationPrincipal String email, @RequestParam(name = "year", required = false) Integer year) {
        User user = userRepository.findByEmail(email).orElseThrow();
//        if (!user.isVerified()) return ResponseEntity.status(403).build();
        if (year != null) {
            return ResponseEntity.ok(auctionRepository.getAuctionsByYear(year));
        }
        Iterable<BidAuction> bidAuctions = auctionService.getMyAuctions(user);
        return ResponseEntity.ok(bidAuctions);
    }

    // View auction
    @GetMapping("/{id}")
    public ResponseEntity<BidAuction> getAuction(@PathVariable Long id, @AuthenticationPrincipal User user) {
        if (!user.isVerified()) return ResponseEntity.status(403).build();
        BidAuction bidAuction = auctionService.getBidAuction(id);
        return ResponseEntity.ok(bidAuction);
    }

    @GetMapping("/proposal/{proposalId}")
    public ResponseEntity<BidAuction> getMyAuctionByProposal(@PathVariable long proposalId, @AuthenticationPrincipal String email) {
//        if (!user.isVerified()) return ResponseEntity.status(403).build();
        BidAuction bidAuctions = auctionService.getBidAuctionByProposalAndEmail(proposalId, email);
        return ResponseEntity.ok(bidAuctions);
    }

    @PostMapping("/{productId}/createBid")
    public ResponseEntity<BidProposal> createBid(@PathVariable long productId, @Valid @RequestBody CreateBidProposalDto bidProposalDto, @AuthenticationPrincipal User user) {
        var product = productService.getProductById(productId);
        if (!product.getOwner().equals(user)) return ResponseEntity.status(403).build();

        try {
            var bidProposal = bidProposalDto.toBidProposal();
            bidProposal = auctionService.createBidProposal(bidProposal);
            return new ResponseEntity<>(bidProposal, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/increase")
    public ResponseEntity<String> increaseBid(@PathVariable long id, @RequestBody BidAuction auctionBody, @AuthenticationPrincipal String email) {
        var auction = auctionRepository.findById(id).orElseThrow();
        try {
            if (auctionBody.getBidPrice() < auction.getBidPrice()) {
                return ResponseEntity.status(400).body("You can only increase bid price");
            }
            auction.setBidPrice(auctionBody.getBidPrice());
            auction = auctionRepository.save(auction);
            return ResponseEntity.ok(objectMapper.writeValueAsString(auction));
        } catch (IllegalArgumentException | JsonProcessingException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/{auctionProposalId}/bid")
    public ResponseEntity<String> doBid(@PathVariable long auctionProposalId, @Valid @RequestBody DoBidDto doBidDto, @AuthenticationPrincipal String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            var proposal = auctionService.getBidProposal(auctionProposalId);
            var auction = doBidDto.toBidAuction();
            auction.setBidder(user);
            auction.setBidProposal(proposal);

            var deposit = proposal.getDeposit();
            if (deposit == 0) deposit = 0.1 * proposal.getStartingPrice();
            if (user.getBalance() < deposit)
                return ResponseEntity.badRequest().body("You don't have enough balance to bid");
            if (auction.getBidPrice() < proposal.getStartingPrice()) return ResponseEntity.badRequest().build();

            user.setBalance(user.getBalance() - proposal.getDeposit());

            auction = auctionService.createBidAuction(auction);
            return new ResponseEntity<>(objectMapper.writeValueAsString(auction), HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
