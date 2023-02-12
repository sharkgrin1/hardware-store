package store.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import store.api.OrderService;
import store.api.domain.Order;
import store.repositories.OrderRepository;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Order create(Order order, List<Integer> itemIds) {
        final var id = orderRepository.create(order);
        order.setId(id);
        return order;
    }
}
