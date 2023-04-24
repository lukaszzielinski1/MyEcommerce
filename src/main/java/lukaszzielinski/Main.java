package lukaszzielinski;

import lukaszzielinski.productcatalog.HashMapProductStorage;
import lukaszzielinski.productcatalog.ProductCatalog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    ProductCatalog createMyProductCatalog(){
        ProductCatalog productCatalog = new ProductCatalog(new HashMapProductStorage());
        String product1 = productCatalog.addProduct("My nice ebook","nice");
        productCatalog.changePrice(product1, BigDecimal.valueOf(10.10));
        productCatalog.assignImage(product1, "foo/nice/image.jpeg");
        productCatalog.publishProduct(product1);

        String product2 = productCatalog.addProduct("My nice ebook 2","nice2");
        productCatalog.changePrice(product2, BigDecimal.valueOf(10.10));
        productCatalog.assignImage(product2, "foo/nice2/image.jpeg");
        productCatalog.publishProduct(product2);
        return productCatalog;
    }
}