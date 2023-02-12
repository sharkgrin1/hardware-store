package store.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.api.OrderItemService;
import store.api.ProductService;
import store.api.domain.Product;
import store.commons.HttpConstants;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = HttpConstants.PATH_PRODUCTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    private final ProductService productService;
    private final OrderItemService orderItemService;

    public ProductController(ProductService productService, OrderItemService orderItemService) {
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Product create(@RequestBody @Valid Product product) {
        return productService.create(product);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Product update(@RequestBody @Valid Product product) {
        final var updated = productService.update(product);
        if (updated.isHidden()) {
            orderItemService.deleteUnpaidByProductId(updated.getId());
        }
        return updated;
    }

    @DeleteMapping(HttpConstants.PATH_PARAM_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable(HttpConstants.PARAM_ID) int id) {
        productService.delete(id);
        orderItemService.deleteUnpaidByProductId(id);
    }
}
