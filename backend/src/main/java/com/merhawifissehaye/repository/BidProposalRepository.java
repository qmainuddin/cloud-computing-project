package com.merhawifissehaye.repository;

import com.merhawifissehaye.model.BidProposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BidProposalRepository extends PagingAndSortingRepository<BidProposal, Long>, JpaRepository<BidProposal, Long> {
    @Query("SELECT b FROM BidProposal b WHERE b.product.owner.email <> ?1 AND b.published = true AND b.paymentDueDate > CURRENT_DATE")
    Page<BidProposal> getAllBidProposals(PageRequest pageRequest, String email);

    @Query("SELECT b FROM BidProposal b WHERE b.product.owner.email = ?1")
    Page<BidProposal> getAllMyBidProposals(PageRequest pageRequest, String email);

    @Query("SELECT b FROM BidProposal b WHERE b.product.owner.email <> ?1 AND b.published = true AND b.product.name LIKE %?2% AND b.paymentDueDate > CURRENT_DATE")
    Page<BidProposal> getFilteredBidProposals(String name, String email, PageRequest pageRequest);

    @Query("SELECT b FROM BidProposal b WHERE b.product.owner.email = ?1 AND b.product.name LIKE %?2%")
    Page<BidProposal> getMyFilteredBidProposals(String name, String email, PageRequest pageRequest);

    @Modifying
    @Query("DELETE FROM BidProposal b WHERE b.product.id = ?1")
    void deleteAllByProductId(long id);

    long countByProductId(long id);

    @Query("SELECT b FROM BidProposal b WHERE b.product.owner.email = ?1 AND b.published = true AND b.paymentDueDate < CURRENT_DATE")
    List<BidProposal> getClosedBids(String email);
}
