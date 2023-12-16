package com.merhawifissehaye.service;

import com.merhawifissehaye.model.BidAuction;
import com.merhawifissehaye.model.BidProposal;
import com.merhawifissehaye.model.User;

public interface AuctionService {
    BidAuction getBidAuction(long id);
    BidProposal getBidProposal(long id);

    BidAuction createBidAuction(BidAuction auction);

    BidProposal createBidProposal(BidProposal proposal);

    Iterable<BidAuction> getMyAuctions(User user);

    BidAuction getBidAuctionByProposalAndEmail(long proposalId, String email);
}
