package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Item;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TotalAmountPointsRuleTest {

    private TotalAmountPointsRule totalAmountPointsRule;
    private Receipt validReceipt;

    @BeforeEach
    void setUp() {
        totalAmountPointsRule = new TotalAmountPointsRule();

        List<Item> items = List.of(
                new Item("Mountain Dew 12PK", 6.49),
                new Item("Emils Cheese Pizza", 12.25),
                new Item("Knorr Creamy Chicken", 1.26),
                new Item("Doritos Nacho Cheese", 3.35),
                new Item("Klarbrunn 12-PK 12 FL OZ", 12.00)
        );

        validReceipt = new Receipt(
                "Target",
                LocalDate.of(2022, 1, 1),
                LocalTime.of(13, 1),
                items,
                35.35
        );
    }

    @Test
    void shouldCalculate50PointsForRoundDollarAmountTest() {
        Receipt receipt = new Receipt(
                "StoreA",
                LocalDate.of(2022, 1, 1),
                LocalTime.of(13, 1),
                validReceipt.getItems(),
                25.00
        );
        int points = totalAmountPointsRule.calculatePoints(receipt);
        assertEquals(75, points);
    }
}