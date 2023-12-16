package com.merhawifissehaye.service;

import com.merhawifissehaye.exceptions.ProductPermissionException;
import com.merhawifissehaye.model.BidAuction;
import com.merhawifissehaye.model.BidProposal;
import com.merhawifissehaye.model.Product;
import com.merhawifissehaye.model.User;
import com.merhawifissehaye.repository.BidAuctionRepository;
import com.merhawifissehaye.repository.BidProposalRepository;
import com.merhawifissehaye.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private BidAuctionRepository bidAuctionRepository;
    private BidProposalRepository bidProposalRepository;

    @Override
    public BidAuction getBidAuction(long id) {
        return bidAuctionRepository.findById(id).orElseThrow();
    }

    @Override
    public BidProposal getBidProposal(long id) {
        return bidProposalRepository.findById(id).orElseThrow();
    }

    @Override
    public BidAuction createBidAuction(BidAuction auction) {
        return bidAuctionRepository.save(auction);
    }

    @Override
    public BidProposal createBidProposal(BidProposal proposal) {
        return bidProposalRepository.save(proposal);
    }

    @Override
    public Iterable<BidAuction> getMyAuctions(User user) {
        return bidAuctionRepository.findAllByBidder(user);
    }

    @Override
    public BidAuction getBidAuctionByProposalAndEmail(long proposalId, String email) {
        return bidAuctionRepository.findTopByBidProposalAndBidderEmail(getBidProposal(proposalId), email);
    }
}
