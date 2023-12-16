package com.merhawifissehaye.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
public class BidAuction {
    @Id
    @GeneratedValue
    private Long id;

    private double bidPrice;

    @ManyToOne
    private BidProposal bidProposal;

    @ManyToOne
    private User bidder;

    @CreationTimestamp
    private Date createdAt;
}
