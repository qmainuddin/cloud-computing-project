package com.merhawifissehaye.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class BidProposal {
    @Id
    @GeneratedValue
    private long id;

    private long startingPrice;
    private double deposit;
    private Date paymentDueDate;
    @Column(columnDefinition = "boolean default false")
    private boolean published;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Product product;

//    @JsonIgnore
//    @OneToMany(mappedBy = "bidProposal")
//    private List<BidAuction> auctions;
}
