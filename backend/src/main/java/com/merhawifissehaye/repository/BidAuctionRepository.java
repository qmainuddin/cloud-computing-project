package com.merhawifissehaye.repository;

import com.merhawifissehaye.model.BidAuction;
import com.merhawifissehaye.model.BidProposal;
import com.merhawifissehaye.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidAuctionRepository extends JpaRepository<BidAuction, Long> {
    @Modifying
    @Query("DELETE FROM BidAuction b WHERE b.bidProposal.product.id = ?1")
    void deleteByBidProposalProductId(long id);

    Iterable<BidAuction> findAllByBidder(User user);

    BidAuction findTopByBidProposalAndBidderEmail(BidProposal proposal, String email);

    long countByBidProposalId(long id);

    BidAuction findFirstByBidProposalIdOrderByBidPriceDesc(long id);

    @Query("SELECT b FROM BidAuction b WHERE YEAR(b.createdAt) = ?1")
    List<BidAuction> getAuctionsByYear(int year);
}
