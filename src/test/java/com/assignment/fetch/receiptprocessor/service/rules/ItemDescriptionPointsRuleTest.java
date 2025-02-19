package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Item;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ItemDescriptionPointsRuleTest {

    private ItemDescriptionPointsRule itemDescriptionPointsRule;
    @BeforeEach
    void setUp() {
        itemDescriptionPointsRule = new ItemDescriptionPointsRule();
    }

    @Test
    void shouldCalculatePointsForTrimmedDescriptionMultipleOf3Test() {
        Item item = new Item("Knor Cream Chicken", 1.26);
        Receipt receipt = new Receipt(
                "StoreD",
                LocalDate.of(2022, 1, 1),
                LocalTime.of(13, 1),
                List.of(item),
                1.26
        );
        int points = itemDescriptionPointsRule.calculatePoints(receipt);
        assertEquals(1, points);
    }
}