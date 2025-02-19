package com.assignment.fetch.receiptprocessor.controller;

import com.assignment.fetch.receiptprocessor.response.PointsResponse;
import com.assignment.fetch.receiptprocessor.model.Receipt;
import com.assignment.fetch.receiptprocessor.response.ReceiptResponse;
import com.assignment.fetch.receiptprocessor.service.processor.ReceiptProcessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/receipts")
public class ReceiptProcessorController {

    private final ReceiptProcessorService receiptProcessorService;

    @Autowired
    public ReceiptProcessorController(ReceiptProcessorService receiptProcessorService) {
        this.receiptProcessorService = receiptProcessorService;
    }

    @PostMapping(path = "/process", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processReceipt(@Valid @RequestBody Receipt receipt) {
        ReceiptResponse response = receiptProcessorService.processReceipt(receipt);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}/points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PointsResponse> getPoints(@PathVariable String id) {
        System.out.println("Get point for Id: " + id);
        Optional<PointsResponse> pointsResponse = receiptProcessorService.calculatePoints(id);

        if (pointsResponse.isPresent()) {
            return ResponseEntity.ok(pointsResponse.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PointsResponse(0));
        }
    }
}

