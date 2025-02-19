# Fetch Assignment - Receipt Processor

## Overview

The receipt processor is a web service application built using Java Spring Boot Framework, leveraging RESTful services. It processes receipt data and calculates the total reward points according to a predefined set of business rules.

---
## Requirements
- Java 17
- Spring Boot v3.4.2
- Docker

---
## Installation

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/ma316912/receipt-processor.git
   ```

2. Navigate to the project directory:

   ```bash
   cd receipt-processor
   ```

3. Build the Docker Image.:

   ```bash
    docker build -t receiptprocessor . 
   ```

4. Navigate to the project directory:

   ```bash
     docker run -p 8000:8080 receiptprocessor
   ```
    - Optional to mimic LLM Generated requests.
    ```bash
     docker run -e IS_LLM_GENERATED=true -p 8000:8080 receiptprocessor
   ```

5. Access the application in your web browser at `http://localhost:8000`.

---
# API Specification Summary

## Endpoint: Process Receipts

- **Path:** `/receipts/process`
- **Method:** `POST`
- **Payload:** Receipt JSON

### Description:
This endpoint accepts a JSON representation of a receipt and returns a JSON object containing a unique ID generated by your application.

### Example Request:
```json
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}
```

### Example Response:
```json
{
    "id": "de16dab5-4da1-4cab-8cf3-278fac19fb03"
}
```

---

## Endpoint: Get Points

- **Path:** `/receipts/{id}/points`
- **Method:** `GET`

### Description:
This endpoint fetches the number of points awarded for a given receipt ID. It looks up the receipt store and calculates the relevant points to return based on the pre-defined rules.

### Example Request:
```
http://localhost:8080/receipts/de16dab5-4da1-4cab-8cf3-278fac19fb03/points
```

### Example Response:
```json
{
  "points": 32
}
```
---
## How to Test Endpoints
#### Endpoint: Process Receipts

```bash
curl --location 'http://localhost:8000/receipts/process' \
--header 'Content-Type: application/json' \
--data '{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}'
```

#### Endpoint: Get Points

```bash
curl --location 'http://localhost:8000/receipts/{id}/points'
```

