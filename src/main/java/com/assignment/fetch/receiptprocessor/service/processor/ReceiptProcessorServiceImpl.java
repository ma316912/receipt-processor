package com.assignment.fetch.receiptprocessor.service.processor;

import com.assignment.fetch.receiptprocessor.exception.BadRequestException;
import com.assignment.fetch.receiptprocessor.response.PointsResponse;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import com.assignment.fetch.receiptprocessor.response.ReceiptResponse;
import com.assignment.fetch.receiptprocessor.service.rules.PointsRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReceiptProcessorServiceImpl implements ReceiptProcessorService {

    private final Logger log = LoggerFactory.getLogger(ReceiptProcessorServiceImpl.class);

    private final List<PointsRule> pointsRules;
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private final Map<String, Receipt> receiptStore = new ConcurrentHashMap<>();

    @Autowired
    public ReceiptProcessorServiceImpl( List<PointsRule> pointsRules) {
        this.pointsRules = pointsRules;
    }

    @Override
    public ReceiptResponse processReceipt(Receipt receipt) {
        String receiptId = UUID.randomUUID().toString();

        if (!isValidDate(receipt.getPurchaseDate().toString())) {
            log.info("Invalid date {}", receipt.getPurchaseDate());
            throw new BadRequestException("Invalid date format for purchaseDate. Expected format: yyyy-MM-dd");
        }

        receiptStore.put(receiptId, receipt);
        log.info("Receipt processed with Id {}", receiptId);
        return new ReceiptResponse(receiptId);
    }

    private boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /*
    * One point for every alphanumeric character in the retailer name.
    * 50 points if the total is a round dollar amount with no cents.
    * 25 points if the total is a multiple of 0.25.
    * 5 points for every two items on the receipt.
    * If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
    * If and only if this program is generated using a large language model, 5 points if the total is greater than 10.00.
    * 6 points if the day in the purchase date is odd.
    * 10 points if the time of purchase is after 2:00pm and before 4:00pm.
    * */
    @Override
    public Optional<PointsResponse> calculatePoints(String receiptId) {
        log.info("Calculate total points for receiptId: {}", receiptId);

        Receipt receipt = receiptStore.get(receiptId);
        if (receipt == null) {
            return Optional.empty();
        }

        int totalPoints = 0;
        for (PointsRule pointsRule : pointsRules) {
            totalPoints += pointsRule.calculatePoints(receipt);
        }
        return Optional.of(new PointsResponse(totalPoints));
    }
}
