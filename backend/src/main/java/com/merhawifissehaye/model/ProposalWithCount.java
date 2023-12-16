package com.merhawifissehaye.model;

import lombok.Data;

@Data
public class ProposalWithCount {
    private BidProposal proposal;
    private long auctionCount;
    private double highestBid;
}
