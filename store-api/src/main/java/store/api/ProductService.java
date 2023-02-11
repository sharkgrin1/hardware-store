package store.api;

import store.api.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product create(Product product);

    Product update(Product product);

    void delete(int id);
}
