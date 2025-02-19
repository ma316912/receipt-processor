package com.assignment.fetch.receiptprocessor.controller;

import com.assignment.fetch.receiptprocessor.model.Receipt;
import com.assignment.fetch.receiptprocessor.model.Item;
import com.assignment.fetch.receiptprocessor.response.ReceiptResponse;
import com.assignment.fetch.receiptprocessor.service.processor.ReceiptProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptProcessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReceiptProcessorService receiptProcessorService;

    private Receipt validReceipt;

    @BeforeEach
    public void setUp() {
        validReceipt = new Receipt();
        validReceipt.setRetailer("Target");
        validReceipt.setPurchaseDate(LocalDate.of(2022, 1, 1));
        validReceipt.setPurchaseTime(LocalTime.of(13, 1));
        validReceipt.setTotal(35.35);

        Item item1 = new Item("Mountain Dew 12PK", 6.49);
        Item item2 = new Item("Emils Cheese Pizza", 12.25);
        Item item3 = new Item("Knorr Creamy Chicken", 1.26);
        Item item4 = new Item("Doritos Nacho Cheese", 3.35);
        Item item5 = new Item("Klarbrunn 12-PK 12 FL OZ", 12.00);

        validReceipt.setItems(List.of(item1, item2, item3, item4, item5));
    }

    @Test
    public void shouldReturnBadRequestWhenMissingFieldsTest() throws Exception {
        String invalidReceiptJson = "{ \"retailer\": \"\", \"purchaseDate\": \"\", \"purchaseTime\": \"\", \"total\": \"\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidReceiptJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnValidReceiptIdTest() throws Exception {
        String validReceiptJson = "{ \"retailer\": \"Target\", \"purchaseDate\": \"2022-01-01\", \"purchaseTime\": \"13:01\", \"total\": \"35.35\", \"items\": [ { \"shortDescription\": \"Mountain Dew 12PK\", \"price\": \"6.49\" }, { \"shortDescription\": \"Emils Cheese Pizza\", \"price\": \"12.25\" }, { \"shortDescription\": \"Knorr Creamy Chicken\", \"price\": \"1.26\" }, { \"shortDescription\": \"Doritos Nacho Cheese\", \"price\": \"3.35\" }, { \"shortDescription\": \"Klarbrunn 12-PK 12 FL OZ\", \"price\": \"12.00\" } ] }";

        mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validReceiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void shouldReturnBadRequestWhenItemPriceIsInvalidTest() throws Exception {
        String invalidItemPriceJson = "{ \"retailer\": \"Target\", \"purchaseDate\": \"2022-01-01\", \"purchaseTime\": \"13:01\", \"total\": \"35.35\", \"items\": [ { \"shortDescription\": \"Mountain Dew 12PK\", \"price\": \"-5.00\" } ] }";

        mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidItemPriceJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestForInvalidIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/receipts/invalid-id/points"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnPointsForValidReceiptUsingIDTest() throws Exception {
        ReceiptResponse savedReceipt = receiptProcessorService.processReceipt(validReceipt);

        mockMvc.perform(MockMvcRequestBuilders.get("/receipts/" + savedReceipt.getId() + "/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(28));  // Adjust the points as expected
    }

    @Test
    public void shouldReturnBadRequestWhenMissingPurchaseDateTest() throws Exception {
        String missingPurchaseDateJson = "{ \"retailer\": \"Target\", \"purchaseTime\": \"13:01\", \"total\": \"35.35\", \"items\": [ { \"shortDescription\": \"Mountain Dew 12PK\", \"price\": \"6.49\" } ] }";

        mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(missingPurchaseDateJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestForMissingReceiptIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/receipts/invalid-id/points"))
                .andExpect(status().isNotFound());
    }
}