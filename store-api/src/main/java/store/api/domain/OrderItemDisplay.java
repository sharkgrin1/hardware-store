package store.api.domain;

import java.math.BigDecimal;

public class OrderItemDisplay {
    private final int id;
    private final int userId;
    private final String productName;
    private final BigDecimal productPrice;
    private final BigDecimal total;
    private final int quantity;

    public OrderItemDisplay(int id, int userId, String productName, BigDecimal productPrice, BigDecimal total, int quantity) {
        this.id = id;
        this.userId = userId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.total = total;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }
}
