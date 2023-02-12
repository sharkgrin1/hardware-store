package store.repositories.jooq;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import store.api.domain.OrderItem;
import store.jooq.tables.records.OrderItemsRecord;
import store.repositories.OrderItemRepository;

import java.util.List;
import java.util.Optional;

import static store.jooq.tables.OrderItems.*;

@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final DSLContext dsl;

    public OrderItemRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<OrderItem> findUnpaidByUserId(int id) {
        return dsl.selectFrom(ORDER_ITEMS)
                .where(ORDER_ITEMS.USER_ID.eq(id).and(ORDER_ITEMS.ORDER_ID.isNull()))
                .fetch()
                .map(x -> x.into(OrderItem.class));
    }

    @Override
    public void create(OrderItem item) {
        final var record = new OrderItemsRecord();
        record.from(item);
        dsl.insertInto(ORDER_ITEMS)
                .set(record)
                .execute();
    }

    @Override
    public Optional<OrderItem> findOne(int id) {
        return Optional.ofNullable(dsl.selectFrom(ORDER_ITEMS)
                .where(ORDER_ITEMS.ID.eq(id))
                .fetchOne(x -> x.into(OrderItem.class)));
    }

    @Override
    public void delete(int id) {
        dsl.deleteFrom(ORDER_ITEMS)
                .where(ORDER_ITEMS.ID.eq(id))
                .execute();
    }

    @Override
    public void deleteUnpaidByProductId(int productId) {
        dsl.deleteFrom(ORDER_ITEMS)
                .where(ORDER_ITEMS.PRODUCT_ID.eq(productId).and(ORDER_ITEMS.ORDER_ID.isNull()))
                .execute();
    }

    @Override
    public void updateOrderId(List<Integer> ids, int orderId) {
        dsl.update(ORDER_ITEMS)
                .set(ORDER_ITEMS.ORDER_ID, orderId)
                .where(ORDER_ITEMS.ID.in(ids))
                .execute();
    }

    @Override
    public List<OrderItem> findPaidByProductIds(List<Integer> productIds) {
        return  dsl.selectFrom(ORDER_ITEMS)
                .where(ORDER_ITEMS.PRODUCT_ID.in(productIds).and(ORDER_ITEMS.ORDER_ID.isNotNull()))
                .fetch()
                .map(x -> x.into(OrderItem.class));
    }
}
