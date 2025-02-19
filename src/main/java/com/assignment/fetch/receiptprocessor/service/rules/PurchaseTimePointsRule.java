package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/*
* Points for Purchase Time (between 2:00 PM and 4:00 PM)
* */
@Component
public class PurchaseTimePointsRule implements PointsRule{
    @Override
    public int calculatePoints(Receipt receipt) {
        int purchaseTimePoints = 0;
        LocalTime purchaseTime = receipt.getPurchaseTime();
        LocalTime startTime = LocalTime.of(14, 0);
        LocalTime endTime = LocalTime.of(16, 0);

        if (!purchaseTime.isBefore(startTime) && !purchaseTime.isAfter(endTime)) {
            purchaseTimePoints += 10;
        }
        return purchaseTimePoints;
    }
}
