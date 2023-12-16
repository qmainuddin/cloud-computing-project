package com.merhawifissehaye.dto;

import com.merhawifissehaye.model.BidProposal;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class CreateBidProposalDto {
    @NotNull(message = "starting price is required")
    String startingPrice;
    @NotNull(message = "deposit is required")
    private String deposit;
    @NotNull(message = "payment due date is required")
    @Future(message = "payment due date must be in the future")
    private String paymentDueDate;

    public BidProposal toBidProposal() throws IllegalArgumentException {
        var bidProposal = new BidProposal();
        bidProposal.setStartingPrice(Long.parseLong(startingPrice));
        bidProposal.setDeposit(Double.parseDouble(deposit));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            bidProposal.setPaymentDueDate(simpleDateFormat.parse(paymentDueDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format");
        }


        return bidProposal;
    }
}
