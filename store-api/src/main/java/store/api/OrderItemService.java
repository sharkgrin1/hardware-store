package store.api;

import store.api.domain.OrderItem;
import store.api.domain.OrderItemDisplay;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDisplay> findUnpaidByUserId(int userId);

    void create(OrderItem item);

    void delete(int id);

    void deleteUnpaidByProductId(int productId);

    void updateOrderId(List<Integer> ids, int orderId);
}
