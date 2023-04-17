package lukaszzielinski;

import lukaszzielinski.productcatalog.HashMapProductStorage;
import lukaszzielinski.productcatalog.ProductCatalog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }

    @Bean
    ProductCatalog CreateMyProductCatalog(ProductCatalog storage){
        return new ProductCatalog(new HashMapProductStorage());
    }
}
