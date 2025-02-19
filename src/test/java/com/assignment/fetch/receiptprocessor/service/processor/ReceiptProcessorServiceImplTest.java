package com.assignment.fetch.receiptprocessor.service.processor;

import com.assignment.fetch.receiptprocessor.model.Item;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import com.assignment.fetch.receiptprocessor.response.PointsResponse;
import com.assignment.fetch.receiptprocessor.service.rules.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ReceiptProcessorServiceImplTest {

    private ReceiptProcessorServiceImpl receiptProcessorService;

    private Receipt validReceipt;
    private Map<String, Receipt> receiptStore;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {

        List<PointsRule> rules = Arrays.asList(
                new RetailerNamePointsRule(),
                new TotalAmountPointsRule(),
                new PurchaseDateOddPointsRule(),
                new PurchaseTimePointsRule(),
                new ItemPointsRule(),
                new ItemDescriptionPointsRule(),
                new LLMPointsRule()
        );

        receiptProcessorService = new ReceiptProcessorServiceImpl(rules);

        Receipt validReceipt = new Receipt(
                "Target",
                LocalDate.of(2022, 1, 1),
                LocalTime.of(15, 30),
                List.of(
                        new Item("Knor Cream Chicken", 1.26),
                        new Item("Mountain Dew 12PK", 6.49)
                ),
                15.00
        );


        receiptStore = new ConcurrentHashMap<>();
        receiptStore.putIfAbsent("946b5052-018b-4c71-b3c9-440582392241", validReceipt);

        Field field = ReceiptProcessorServiceImpl.class.getDeclaredField("receiptStore");
        field.setAccessible(true);  // Make it accessible (even though it's private)
        field.set(receiptProcessorService, receiptStore);
    }

    @Test
    void shouldCalculateTotalPointsCorrectlyTest() {
        // Assuming:
        // 6 points for alphanumeric retailer
        // 50 points for round dollar amount
        // 25 points for multiple of 0.25
        // 5 points for two items
        // 1 point for trimmed description multiple of 3
        // 6 points for odd day (1st)
        // 10 points for time between 2-4pm
        Optional<PointsResponse> points = receiptProcessorService.calculatePoints("946b5052-018b-4c71-b3c9-440582392241");
        assertEquals(103, points.get().getPoints());
    }
}
