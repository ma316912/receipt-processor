package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Item;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LLMPointsRuleTest {

    private  LLMPointsRule llmPointsRule;
    private Receipt validReceipt;


    @BeforeEach
    void setUp() {
        llmPointsRule = new LLMPointsRule();

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
    void shouldCalculate5PointsIfTotalIsGreaterThan10Test() throws IllegalAccessException, NoSuchFieldException {
        Receipt receipt = new Receipt(
                "StoreE",
                LocalDate.of(2022, 1, 1),
                LocalTime.of(13, 1),
                validReceipt.getItems(),
                15.00
        );

        Field field = llmPointsRule.getClass().getDeclaredField("isLLMGenerated");
        field.setAccessible(true);
        field.set(llmPointsRule, true);

        int points = llmPointsRule.calculatePoints(receipt);
        assertEquals(5, points);
    }
}