package store.repositories.jooq;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import store.api.domain.Product;
import store.jooq.tables.records.ProductsRecord;
import store.repositories.ProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static store.jooq.tables.Products.PRODUCTS;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final DSLContext dsl;

    public ProductRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }


    @Override
    public List<Product> findAll() {
        return dsl.selectFrom(PRODUCTS)
                .fetch()
                .map(x -> x.into(Product.class));
    }

    @Override
    public Product create(Product product) {
        final var record = new ProductsRecord();
        record.from(product);
        return Objects.requireNonNull(dsl.insertInto(PRODUCTS)
                        .set(record)
                        .returning().fetchOne())
                .into(Product.class);
    }

    @Override
    public Optional<Product> findOne(int id) {
        return Optional.ofNullable(dsl.selectFrom(PRODUCTS)
                .where(PRODUCTS.ID.eq(id))
                .fetchOne(x -> x.into(Product.class)));
    }

    @Override
    public void update(Product product) {
        final var record = new ProductsRecord();
        record.from(product);
        dsl.update(PRODUCTS)
                .set(record)
                .where(PRODUCTS.ID.eq(product.getId()))
                .execute();
    }

    @Override
    public void delete(int id) {
        dsl.deleteFrom(PRODUCTS)
                .where(PRODUCTS.ID.eq(id))
                .execute();
    }
}
