package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/*
* 6 points if the day in the purchase date is odd.
* */
@Component
public class PurchaseDateOddPointsRule implements PointsRule{
    @Override
    public int calculatePoints(Receipt receipt) {
        int totalPurchaseDateOddPoints = 0;
        if (isOddDay(receipt.getPurchaseDate())) {
            totalPurchaseDateOddPoints += 6;
        }
        return totalPurchaseDateOddPoints;
    }

    private boolean isOddDay(LocalDate purchaseDate) {
        return purchaseDate.getDayOfMonth() % 2 != 0;
    }
}
