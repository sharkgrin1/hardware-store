package store.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.api.OrderItemService;
import store.api.OrderService;
import store.api.domain.Order;
import store.commons.HttpConstants;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = HttpConstants.PATH_ORDERS, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public void create(@RequestBody @Valid Order order, @RequestParam(HttpConstants.PARAM_ITEMS) List<Integer> itemIds) {
        final var created = orderService.create(order, itemIds);
        orderItemService.updateOrderId(itemIds, created.getId());
    }
}
