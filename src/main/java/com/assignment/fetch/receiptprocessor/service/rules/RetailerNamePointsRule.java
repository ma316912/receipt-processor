package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Component;

/*
* One point for every alphanumeric character in the retailer name.
* */
@Component
public class RetailerNamePointsRule implements PointsRule{
    @Override
    public int calculatePoints(Receipt receipt) {
        return (int) receipt.getRetailer().chars().filter(Character::isLetterOrDigit).count();
    }
}
