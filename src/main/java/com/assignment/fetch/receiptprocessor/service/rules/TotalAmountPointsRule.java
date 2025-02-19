package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Component;

/*
 *   50 points if the total is a round dollar amount with no cents.
 *   25 points if the total is a multiple of 0.25.
 * */
@Component
public class TotalAmountPointsRule implements PointsRule {
    @Override
    public int calculatePoints(Receipt receipt) {
        int totalAmountPoints = 0;
        double totalAmount = receipt.getTotal();
        if (totalAmount == Math.floor(totalAmount)) {
            totalAmountPoints += 50;
        }
        if (totalAmount % 0.25 == 0) {
            totalAmountPoints += 25;
        }
        return totalAmountPoints;
    }
}
