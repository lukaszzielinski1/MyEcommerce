package lukaszzielinski.productcatalog;

import java.util.List;

public interface ProductStorage {
    void add(Product newOne);

    List<Product> allProducts();

    List<Product> allPublishedProducts();

    Product loadById(String productId);
}