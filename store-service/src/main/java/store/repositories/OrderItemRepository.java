package store.repositories;

import store.api.domain.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {
    List<OrderItem> findUnpaidByUserId(int id);

    void create(OrderItem item);

    Optional<OrderItem> findOne(int id);

    void delete(int id);

    void deleteUnpaidByProductId(int productId);

    void updateOrderId(List<Integer> ids, int orderId);
}
