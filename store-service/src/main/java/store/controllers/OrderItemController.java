package store.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.api.OrderItemService;
import store.api.domain.OrderItem;
import store.api.domain.OrderItemDisplay;
import store.commons.HttpConstants;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = HttpConstants.PATH_ITEMS, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<OrderItemDisplay> findUnpaidByUserId(@RequestParam(HttpConstants.PARAM_USER_ID) int userId) {
        return orderItemService.findUnpaidByUserId(userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public void create(@RequestBody @Valid OrderItem item) {
        orderItemService.create(item);
    }

    @DeleteMapping(HttpConstants.PATH_PARAM_ID)
    @PreAuthorize("hasRole('CUSTOMER')")
    public void delete(@PathVariable(HttpConstants.PARAM_ID) int id) {
        orderItemService.delete(id);
    }
}
