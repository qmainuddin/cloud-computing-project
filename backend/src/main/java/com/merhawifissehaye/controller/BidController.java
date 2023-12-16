package com.merhawifissehaye.controller;

import com.merhawifissehaye.model.BidAuction;
import com.merhawifissehaye.model.BidProposal;
import com.merhawifissehaye.model.ProposalWithCount;
import com.merhawifissehaye.repository.BidAuctionRepository;
import com.merhawifissehaye.repository.BidProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/bids")
@CrossOrigin
@AllArgsConstructor
@RestController
public class BidController {
    private BidProposalRepository bidProposalRepository;
    private BidAuctionRepository bidAuctionRepository;
//    private ObjectMapper objectMapper;

    @GetMapping("")
    @ResponseBody
    public Page<ProposalWithCount> getBidProposals(@RequestParam(name = "q", required = false) String query,
                                                   @RequestParam(name = "page", required = false) int page,
//                                                   @RequestParam(name = "year", required = false) Integer year,
                                                   @AuthenticationPrincipal String email) {
        Sort sort = Sort.by("id").descending();
        PageRequest paging = PageRequest.of(page, 20, sort);
        Page<BidProposal> proposals;

        if (query != null && !query.isEmpty())
            proposals = bidProposalRepository.getFilteredBidProposals(query, email, paging);
        else proposals = bidProposalRepository.getAllBidProposals(paging, email);

        return proposals.map(proposal -> {
            ProposalWithCount proposalWithCount = new ProposalWithCount();
            proposalWithCount.setProposal(proposal);
            proposalWithCount.setAuctionCount(bidAuctionRepository.countByBidProposalId(proposal.getId()));
            BidAuction topAuction = bidAuctionRepository.findFirstByBidProposalIdOrderByBidPriceDesc(proposal.getId());
            if (topAuction != null) {
                proposalWithCount.setHighestBid(topAuction.getBidPrice());
            }
            return proposalWithCount;
        });
    }

    @GetMapping("/my")
    @ResponseBody
    public Page<BidProposal> getMyBids(@RequestParam(name = "q", required = false) String query, @RequestParam(name = "page", required = false) int page, @AuthenticationPrincipal String email) {
        Sort sort = Sort.by("id").descending();
        PageRequest paging = PageRequest.of(page, 20, sort);
        if (query != null && !query.isEmpty())
            return bidProposalRepository.getMyFilteredBidProposals(query, email, paging);
        return bidProposalRepository.getAllMyBidProposals(paging, email);
    }

    public static class SingleBidProposalResponse extends BidProposal {
        List<BidAuction> auctions;
//        public static SingleBidProposalResponse fromBidProposal(BidProposal auction) {
//            this.setDeposit(auction.getDeposit());
//            this.setProduct(auction.getProduct());
//            this.setPublished(auction.isPublished());
//            this.setPaymentDueDate(auction.getPaymentDueDate());
//            this.setId(auction.getId());
//            this.setStartingPrice(auction.getStartingPrice());
//        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<BidProposal> getBidProposal(@PathVariable Long id, @AuthenticationPrincipal String email) {
        try {
            // TODO: Check if the user is the owner of the product
            BidProposal bidProposal = bidProposalRepository.findById(id).orElseThrow();
//            SingleBidProposalResponse proposal = (SingleBidProposalResponse) bidProposal;
//            proposal.auctions = bidAuctionRepository.findAllByBidProposalAndBidderEmail(bidProposal, email);
//            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//            ObjectWriter writer = objectMapper.writerWithView(IncludeIgnoredView.class);
//            String result = writer.writeValueAsString(bidProposal);
            return ResponseEntity.ok(bidProposal);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<BidProposal> updateBidProposal(@PathVariable Long id, @RequestBody BidProposal bidProposal, @AuthenticationPrincipal String email) {
        try {
            BidProposal proposal = bidProposalRepository.findById(id).orElseThrow();
            proposal.setDeposit(bidProposal.getDeposit());
            proposal.setStartingPrice(bidProposal.getStartingPrice());
            proposal.setPaymentDueDate(bidProposal.getPaymentDueDate());
            return ResponseEntity.ok(bidProposalRepository.save(proposal));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/publish/{id}")
    @ResponseBody
    public ResponseEntity<BidProposal> publishBidProposal(@PathVariable Long id, @AuthenticationPrincipal String email) {
        try {
            BidProposal proposal = bidProposalRepository.findById(id).orElseThrow();
            if (!proposal.getProduct().getOwner().getEmail().equals(email)) {
                return ResponseEntity.badRequest().build();
            }
            proposal.setPublished(true);
            return ResponseEntity.ok(bidProposalRepository.save(proposal));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/closed")
    public ResponseEntity<List<BidProposal>> getClosedBids(@AuthenticationPrincipal String email) {
        try {
            List<BidProposal> closedBids = bidProposalRepository.getClosedBids(email);
            return ResponseEntity.ok(closedBids);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
