package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Item;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Component;

/*
* If the trimmed length of the item description is a multiple of 3,
* multiply the price by 0.2 and round up to the nearest integer.The result is the number of points earned.
* */
@Component
public class ItemDescriptionPointsRule implements PointsRule{
    @Override
    public int calculatePoints(Receipt receipt) {
        int itemDescriptionPoints = 0;
        for (Item item : receipt.getItems()) {
            String trimmedDescription = item.getShortDescription().trim();
            if (trimmedDescription.length() % 3 == 0) {
                double price = item.getPrice();
                itemDescriptionPoints += (int) Math.ceil(price * 0.2);
            }
        }
        return itemDescriptionPoints;
    }
}
