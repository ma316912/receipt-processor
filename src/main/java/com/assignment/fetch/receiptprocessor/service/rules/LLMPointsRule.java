package com.assignment.fetch.receiptprocessor.service.rules;

import com.assignment.fetch.receiptprocessor.model.Receipt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/*
* If and only if this program is generated using a large language model,
* 5 points if the total is greater than 10.00.
* */
@Component
public class LLMPointsRule implements PointsRule{
    // Default to false if not set
    @Value("${IS_LLM_GENERATED:false}")
    private boolean isLLMGenerated;

    @Override
    public int calculatePoints(Receipt receipt) {
        int llmPoints = 0;
        if (isLLMGenerated && new BigDecimal(receipt.getTotal()).compareTo(BigDecimal.valueOf(10.00)) > 0) {
            llmPoints += 5;
        }
        return llmPoints;
    }
}
