package com.merhawifissehaye.dto;

import com.merhawifissehaye.model.BidAuction;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoBidDto {
    @NotNull(message = "Bid price is required")
    @Min(value = 1, message = "Bid price must be greater than 0")
    private long bidPrice;

    public BidAuction toBidAuction() {
        var bidAuction = new BidAuction();
        bidAuction.setBidPrice(bidPrice);
        return bidAuction;
    }
}
