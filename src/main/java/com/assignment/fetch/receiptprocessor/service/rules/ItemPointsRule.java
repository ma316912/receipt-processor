package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Component;

/*
* 5 points for every 2 items on the receipt
* */
@Component
public class ItemPointsRule implements PointsRule{
    @Override
    public int calculatePoints(Receipt receipt) {
        int itemsSize = receipt.getItems().size();
        return (int) Math.floor(itemsSize / 2) * 5;
    }
}
