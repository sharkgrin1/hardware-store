package store.api;

import store.api.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product findOne(int id);
    List<Product> findAll();

    Product create(Product product);

    Product update(Product product);

    void delete(int id);
}
