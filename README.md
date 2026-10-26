
# PaymentService — Spring Boot + Razorpay + Stripe

A Spring Boot payment microservice built using Java 17, Spring Boot 3.4.5, Razorpay, Stripe, and the Strategy Design Pattern.

The application supports initiating payments through multiple payment gateways using a common interface.

## Features

- Razorpay order creation
- Stripe Checkout session creation
- Strategy Design Pattern for gateway selection
- Spring Boot REST API
- Environment-based API credentials
- Request validation and exception handling
- Separate controller, service, DTO, configuration, and payment gateway packages

## Tech Stack

- Java 17
- Spring Boot 3.4.5
- Maven
- Razorpay Java SDK
- Stripe Java SDK
- REST APIs

## Project Structure

```text
src/main/java/com/example/PaymentService/
├── config/
│   └── RazorpayConfig.java
├── controllers/
│   ├── PaymentController.java
│   └── GlobalExceptionHandler.java
├── dtos/
│   ├── InitiatePaymentDto.java
│   └── InitiatePaymentResponseDto.java
├── paymentgateway/
│   ├── PaymentGateway.java
│   ├── PaymentGatewayStrategyChooser.java
│   ├── RazorpayPaymentGateway.java
│   └── StripePaymentGateway.java
├── services/
│   └── PaymentService.java
└── PaymentServiceApplication.java
```

## Prerequisites

- Java 17 or later
- Maven
- Razorpay test account
- Stripe test account

## Configuration

Configure the following environment variables before initiating payments.

```bash
export RAZORPAY_KEY_ID=rzp_test_your_key_id
export RAZORPAY_KEY_SECRET=your_razorpay_test_secret
export STRIPE_SECRET_KEY=sk_test_your_stripe_secret
```

Replace the example values with your actual test credentials.

Never commit API secrets to GitHub.

## Run the Application

Clone or download the project and navigate to its root directory.

Build the project:

```bash
mvn clean package
```

Run the application:

```bash
mvn spring-boot:run
```

Default server port: `8082`

## API Endpoints

### 1. Initiate a Razorpay Payment

**Endpoint**

```http
POST /api/payments/initiate
```

**Request**

```json
{
  "amount": 499.00,
  "currency": "INR",
  "gateway": "RAZORPAY",
  "receipt": "order_1001"
}
```

**cURL**

```bash
curl -X POST http://localhost:8082/api/payments/initiate \
  -H "Content-Type: application/json" \
  -d '{"amount":499.00,"currency":"INR","gateway":"RAZORPAY","receipt":"order_1001"}'
```

The API creates a Razorpay order and returns its order ID.

The frontend can use the order ID with Razorpay Checkout to let the customer complete payment.

Creating an order does not mean the payment has been completed.

### 2. Initiate a Stripe Checkout Session

**Endpoint**

```http
POST /api/payments/initiate
```

**Request**

```json
{
  "amount": 19.99,
  "currency": "USD",
  "gateway": "STRIPE",
  "receipt": "Order 1002",
  "successUrl": "https://example.com/payment/success?session_id={CHECKOUT_SESSION_ID}",
  "cancelUrl": "https://example.com/payment/cancel"
}
```

**cURL**

```bash
curl -X POST http://localhost:8082/api/payments/initiate \
  -H "Content-Type: application/json" \
  -d '{"amount":19.99,"currency":"USD","gateway":"STRIPE","receipt":"Order 1002","successUrl":"https://example.com/payment/success?session_id={CHECKOUT_SESSION_ID}","cancelUrl":"https://example.com/payment/cancel"}'
```

The API creates a Stripe Checkout session and returns a checkout URL.

Open the returned URL in a browser to continue the checkout process.

## Strategy Design Pattern

The application uses the Strategy Design Pattern to keep payment gateway implementations separate.

### PaymentGateway

Defines the common interface implemented by supported payment gateways.

### RazorpayPaymentGateway

Implements Razorpay-specific order creation logic.

### StripePaymentGateway

Implements Stripe-specific Checkout session creation logic.

### PaymentGatewayStrategyChooser

Selects the appropriate payment gateway based on the requested gateway type.

### PaymentService

Coordinates the payment initiation workflow.

### PaymentController

Exposes the REST API to clients.

**Benefits**

- Loose coupling between business logic and payment providers
- Easier addition of new payment gateways
- Better separation of concerns
- Easier unit testing and maintenance

## Payment Flow

1. The client sends a payment initiation request.
2. PaymentController receives and validates the request.
3. PaymentService invokes PaymentGatewayStrategyChooser.
4. The chooser selects Razorpay or Stripe.
5. The selected gateway creates an order or Checkout session.
6. The API returns the result to the client.

## Security Considerations

Before using this service in production:

- Verify Razorpay payment signatures on the backend.
- Verify webhook signatures before trusting payment status.
- Verify Stripe webhook signatures using the raw request body and endpoint signing secret.
- Do not trust a success redirect alone as proof of payment.
- Store credentials in environment variables or a secrets manager.
- Add authentication and authorization.
- Persist payment and order details in a database.
- Implement idempotency to prevent duplicate payment operations.
- Validate amounts and ensure users can only pay for their own orders.
- Use HTTPS in production.

## Important Notes

- Use test credentials during development.
- Amounts are provided in major currency units, such as rupees or dollars, and converted into minor units such as paise or cents by the implementation.
- Currency conversion must account for currencies with zero or three decimal places if those currencies are supported.
- Payment initiation is different from payment confirmation. Use verified webhooks or server-side verification to determine the final payment status.

## Future Improvements

- Payment status API
- Database persistence using MySQL
- Razorpay and Stripe webhook handlers
- Idempotency support
- Unit and integration tests
- Docker containerization
- CI/CD using Jenkins
- Authentication and authorization

