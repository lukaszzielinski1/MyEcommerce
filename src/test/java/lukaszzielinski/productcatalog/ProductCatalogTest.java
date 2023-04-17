package lukaszzielinski.productcatalog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;

public class ProductCatalogTest {

    @Test
    void itExposeEmptyProductsList() {
        ProductCatalog catalog = thereIsProductCatalog();
        List<Product> products = catalog.allProducts();
        assertEmptyList(products);
    }

    private static void assertEmptyList(List<Product> products) {
        assert 0 == products.size();
    }

    private ProductCatalog thereIsProductCatalog() {
        return new ProductCatalog(new DbProductStorage());
    }
    @Test
    void itAllowsToAddProduct() {
        //Arrange
        ProductCatalog catalog = thereIsProductCatalog();
        //Act
        String productID = catalog.addProduct("Lego", "cool");
        //Assert
        List<Product> products = catalog.allProducts();
        assert 1 == products.size();
    }

    @Test
    void itAllowsToChangePrice() {
        ProductCatalog catalog = thereIsProductCatalog();
        String productId = catalog.addProduct("lego", "nice");
        catalog.changePrice(productId, BigDecimal.valueOf(20.20));
        Product loaded = catalog.loadById(productId);
        Assertions.assertEquals(BigDecimal.valueOf(20.20), loaded.getPrice());
    }

    @Test
    void itAllowsToAssignImage() {
        ProductCatalog catalog = thereIsProductCatalog();
        String productId = catalog.addProduct("lego", "nice");
        catalog.assignImage(productId, "nice/picture.jpeg");
    }

    @Test
    void itAllowsToLoadProductDetails() {
        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("lego set 8083", "nice one");

        Product loadedProduct = catalog.loadById(productId);
        assert loadedProduct.getId().equals(productId);
        assert loadedProduct.getName().equals("lego set 8083");
    }
    @Test
    void itAllowsToPublishProduct() {
        ProductCatalog catalog = thereIsProductCatalog();
        String productId = catalog.addProduct("lego set 8083", "nice one");
        catalog.changePrice(productId, BigDecimal.valueOf(10));
        catalog.assignImage(productId, "nice.jpeg");

        catalog.publishProduct(productId);

        List<Product> publishedProducts = catalog.allPublishedProducts();
        Assertions.assertDoesNotThrow(() -> catalog.publishProduct(productId));
        Assertions.assertEquals(1, publishedProducts.size());
    }

    @Test
    void ProductCantBePublishedWithoutPriceAndImage(){
        ProductCatalog catalog = thereIsProductCatalog();
        String productId = catalog.addProduct("lego set 8083", "nice one");

        Assertions.assertThrows(
                ProductCantBePublishedException.class,
                () -> catalog.publishProduct(productId)
        );
    }


}