package store.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import store.api.OrderItemService;
import store.api.ProductService;
import store.api.domain.OrderItem;
import store.api.domain.OrderItemDisplay;
import store.repositories.OrderItemRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ProductService productService) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<OrderItemDisplay> findUnpaidByUserId(int userId) {
        final var items = orderItemRepository.findUnpaidByUserId(userId);
        return items.stream().map(x -> {
            final var product = productService.findOne(x.getProductId());
            return new OrderItemDisplay(
                    x.getId(),
                    x.getUserId(),
                    product.getName(),
                    product.getPrice(),
                    product.getPrice().multiply(new BigDecimal(x.getQuantity())),
                    x.getQuantity()
            );
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void create(OrderItem item) {
        final var product = productService.findOne(item.getProductId());
        product.setQuantity(product.getQuantity() - item.getQuantity());
        productService.update(product);
        orderItemRepository.create(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        final var itemOptional = orderItemRepository.findOne(id);
        if (itemOptional.isEmpty()) {
            throw new RuntimeException("Order item does not exist");
        }
        final var item = itemOptional.get();
        final var product = productService.findOne(item.getProductId());
        product.setQuantity(product.getQuantity() + item.getQuantity());
        productService.update(product);
        orderItemRepository.delete(item.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deleteUnpaidByProductId(int productId) {
        orderItemRepository.deleteUnpaidByProductId(productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void updateOrderId(List<Integer> ids, int orderId) {
        orderItemRepository.updateOrderId(ids, orderId);
    }
}
