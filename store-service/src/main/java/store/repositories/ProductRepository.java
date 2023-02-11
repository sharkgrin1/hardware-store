package store.repositories;

import store.api.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Product create(Product product);

    Optional<Product> findOne(int id);
    void update(Product product);

    void delete(int id);
}
