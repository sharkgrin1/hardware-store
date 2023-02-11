package store.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import store.api.OrderItemService;
import store.api.ProductService;
import store.api.domain.Product;
import store.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final OrderItemService orderItemService;

    public ProductServiceImpl(ProductRepository productRepository, OrderItemService orderItemService) {
        this.productRepository = productRepository;
        this.orderItemService = orderItemService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Product findOne(int id) {
        return productRepository.findOne(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Product update(Product product) {
        checkExistence(product.getId());
        productRepository.update(product);
        return product;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        checkExistence(id);
        orderItemService.deleteUnpaidByProductId(id);
        productRepository.delete(id);
    }

    private void checkExistence(int id) {
        final var found = productRepository.findOne(id);
        if (found.isEmpty()) {
            throw new RuntimeException("Product does not exist");
        }
    }
}
