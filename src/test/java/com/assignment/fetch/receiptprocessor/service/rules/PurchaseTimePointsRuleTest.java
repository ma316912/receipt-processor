package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Item;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTimePointsRuleTest {

    private PurchaseTimePointsRule purchaseTimePointsRule;
    private Receipt validReceipt;

    @BeforeEach
    void setUp() {
        purchaseTimePointsRule = new PurchaseTimePointsRule();

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
    void shouldCalculate10PointsForTimeBetween2and4pmTest() {
        Receipt receipt = new Receipt(
                "StoreG",
                LocalDate.of(2022, 1, 1),
                LocalTime.of(15, 30),
                validReceipt.getItems(),
                35.35
        );
        int points = purchaseTimePointsRule.calculatePoints(receipt);
        assertEquals(10, points);
    }
}