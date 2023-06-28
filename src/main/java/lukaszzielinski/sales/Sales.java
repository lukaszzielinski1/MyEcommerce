package lukaszzielinski.sales;

import lukaszzielinski.sales.cart.Cart;
import lukaszzielinski.sales.cart.CartStorage;
import lukaszzielinski.sales.offering.EveryNItemLineDiscountPolicy;
import lukaszzielinski.sales.offering.Offer;
import lukaszzielinski.sales.offering.OfferCalculator;
import lukaszzielinski.sales.offering.TotalDiscountPolicy;
import lukaszzielinski.sales.payment.PaymentData;
import lukaszzielinski.sales.payment.PaymentGateway;
import lukaszzielinski.sales.payment.RegisterPaymentRequest;
import lukaszzielinski.sales.productdetails.NoSuchProductException;
import lukaszzielinski.sales.productdetails.ProductDetails;
import lukaszzielinski.sales.productdetails.ProductDetailsProvider;
import lukaszzielinski.sales.reservation.OfferAcceptanceRequest;
import lukaszzielinski.sales.reservation.Reservation;
import lukaszzielinski.sales.reservation.ReservationDetails;
import lukaszzielinski.sales.reservation.InMemoryReservationStorage;

import javax.swing.plaf.synth.SynthLookAndFeel;
import java.util.Optional;

import java.math.BigDecimal;
import java.util.Optional;

public class Sales {
    private CartStorage cartStorage;
    private ProductDetailsProvider productDetailsProvider;
    private final OfferCalculator offerCalculator;
    private PaymentGateway paymentGateway;
    private InMemoryReservationStorage reservationStorage;

    public Sales(
            CartStorage cartStorage,
            ProductDetailsProvider productDetails,
            OfferCalculator offerCalculator,
            PaymentGateway paymentGateway,
            InMemoryReservationStorage reservationStorage
        ) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetails;
        this.offerCalculator = offerCalculator;
        this.paymentGateway = paymentGateway;
        this.reservationStorage = reservationStorage;
    }

    public void addToCart(String customerId, String productId) {
        Cart customerCart = loadCartForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails product = loadProductDetails(productId)
                .orElseThrow(() -> new NoSuchProductException());

        customerCart.add(product.getId());

        cartStorage.addForCustomer(customerId, customerCart);
    }

    private Optional<ProductDetails> loadProductDetails(String productId) {
        return productDetailsProvider.load(productId);
    }

    private Optional<Cart> loadCartForCustomer(String customerId) {
        return cartStorage.load(customerId);
    }

    public Offer getCurrentOffer(String customerId) {
        Cart customerCart = loadCartForCustomer(customerId)
                .orElse(Cart.empty());

        Offer offer = this.offerCalculator.calculateOffer(
                customerCart.getCartItems(),
                new TotalDiscountPolicy(BigDecimal.valueOf(500), BigDecimal.valueOf(50)),
                new EveryNItemLineDiscountPolicy(5)
        );

        return offer;
    }

    public ReservationDetails acceptOffer(String customerId, OfferAcceptanceRequest request) {
        Offer offer = this.getCurrentOffer(customerId);

        PaymentData payment = paymentGateway.register(RegisterPaymentRequest.of(request, offer));

        Reservation reservation = Reservation.of(request, offer, payment);

        reservationStorage.save(reservation);

        return new ReservationDetails(reservation.getId(), reservation.getPaymentUrl());
    }
}