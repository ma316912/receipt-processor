package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Item;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemPointsRuleTest {
    private ItemPointsRule itemPointsRule;
    @BeforeEach
    void setUp() {
        itemPointsRule = new ItemPointsRule();
    }

    @Test
    void shouldCalculatePointsForTwoItemsTest() {
        List<Item> fourItems = List.of(
                new Item("Item1", 2.0),
                new Item("Item2", 3.0),
                new Item("Item3", 4.0),
                new Item("Item4", 5.0)
        );
        Receipt receipt = new Receipt(
                "StoreC",
                LocalDate.of(2022, 1, 1),
                LocalTime.of(13, 1),
                fourItems,
                19.00
        );
        int points = itemPointsRule.calculatePoints(receipt);
        assertEquals(10, points);
    }
}