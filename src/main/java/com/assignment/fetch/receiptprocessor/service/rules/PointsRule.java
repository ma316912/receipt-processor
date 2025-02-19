package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Receipt;

public interface PointsRule {
    int calculatePoints(Receipt receipt);
}
