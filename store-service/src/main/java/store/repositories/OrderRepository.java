package store.repositories;

import store.api.domain.Order;

public interface OrderRepository {
    int create(Order order);
}
