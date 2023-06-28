package lukaszzielinski.sales.payment;

public interface PaymentGateway {
    PaymentData register(RegisterPaymentRequest request);
}