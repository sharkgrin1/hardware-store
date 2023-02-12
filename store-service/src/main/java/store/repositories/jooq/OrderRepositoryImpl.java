package store.repositories.jooq;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import store.api.domain.Order;
import store.jooq.tables.records.OrdersRecord;
import store.repositories.OrderRepository;

import java.util.Objects;

import static store.jooq.tables.Orders.*;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final DSLContext dsl;

    public OrderRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public int create(Order order) {
        final var record = new OrdersRecord();
        record.from(order);
        return Objects.requireNonNull(dsl.insertInto(ORDERS)
                        .set(record)
                        .returning(ORDERS.ID)
                        .fetchOne())
                .getId();
    }
}
