package store.api;

import store.api.domain.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order, List<Integer> itemIds);
}
