package lukaszzielinski.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import lukaszzielinski.sales.cart.Cart;
import lukaszzielinski.sales.cart.CartStorage;
import lukaszzielinski.sales.offering.Offer;
import lukaszzielinski.sales.offering.OfferCalculator;
import lukaszzielinski.sales.offering.OfferLine;
import lukaszzielinski.sales.payment.SpyPaymentGateway;
import lukaszzielinski.sales.productdetails.InMemoryProductDetailsProvider;
import lukaszzielinski.sales.productdetails.ProductDetails;
import lukaszzielinski.sales.reservation.InMemoryReservationStorage;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectingProductsTest {

    private CartStorage cartStorage;
    private InMemoryProductDetailsProvider productDetails;

    @BeforeEach
    void setUp() {
        this.cartStorage = new CartStorage();
        this.productDetails = new InMemoryProductDetailsProvider();
    }

    @Test
    void itAllowsToAddProduct() {
        //ARRANGE
        Sales sales = thereIsSalesModule();
        String product1 = thereIsProduct("Lego set", BigDecimal.valueOf(10.10));
        String customerId = thereIsCustomer("Kuba");

        //Act
        sales.addToCart(customerId, product1);

        //Assert
        assertThereIsXProductsInCustomerCart(1, customerId);
    }

    @Test
    public void itAllowAddProductToCartByMultipleCustomers() {
        //Arrange
        Sales sales = thereIsSalesModule();
        String productId1 = thereIsProduct("lego set 1", BigDecimal.valueOf(10.10));
        String productId2 = thereIsProduct("lego set 2", BigDecimal.TEN);

        String customerId1 = thereIsCustomer("Kuba");
        String customerId2 = thereIsCustomer("Michal");
        //Act
        sales.addToCart(customerId1, productId1);
        sales.addToCart(customerId1, productId2);

        sales.addToCart(customerId2, productId1);

        //Assert
        assertThereIsXProductsInCustomerCart(2, customerId1);
        assertThereIsXProductsInCustomerCart(1, customerId2);
    }

    @Test
    public void itGenerateOfferBasedOnCurrentCart() {
        //Arrange
        Sales sales = thereIsSalesModule();
        String productId1 = thereIsProduct("lego set 1", BigDecimal.valueOf(10.10));
        String productId2 = thereIsProduct("lego set 2", BigDecimal.valueOf(20.10));

        String customerId = thereIsCustomer("Kuba");

        //Act
        sales.addToCart(customerId, productId1);
        sales.addToCart(customerId, productId1);
        sales.addToCart(customerId, productId2);

        Offer offer = sales.getCurrentOffer(customerId);

        assertThat(offer.getTotal()).isEqualByComparingTo(BigDecimal.valueOf(40.30));
        assertThat(offer.getOrderLines())
                .hasSize(2);

        assertThat(offer.getOrderLines())
                .filteredOn(orderLine -> orderLine.getProductId().equals(productId1))
                .extracting(OfferLine::getQuantity)
                .first()
                .isEqualTo(2);

    }

    private void assertThereIsXProductsInCustomerCart(int totalProductsQuantity, String customerId) {
        Cart cart = cartStorage.load(customerId).get();

        assert cart.getItemsCount() == totalProductsQuantity;
    }

    private String thereIsCustomer(String id) {
        return id;
    }

    private String thereIsProduct(String name, BigDecimal price) {
        String id = UUID.randomUUID().toString();
        productDetails.add(new ProductDetails(id, name, price));

        return id;
    }

    private Sales thereIsSalesModule() {
        return new Sales(
                cartStorage,
                productDetails,
                new OfferCalculator(productDetails),
                new SpyPaymentGateway(),
                new InMemoryReservationStorage()
        );
    }

}