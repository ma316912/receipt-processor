package com.assignment.fetch.receiptprocessor.service.processor;


import com.assignment.fetch.receiptprocessor.response.PointsResponse;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import com.assignment.fetch.receiptprocessor.response.ReceiptResponse;

import java.util.Optional;

public interface ReceiptProcessorService {
    ReceiptResponse processReceipt(Receipt receipt);

    Optional<PointsResponse> calculatePoints(String receiptId);
}
