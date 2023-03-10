package store.commons;

public final class HttpConstants {

    public static final String PATH_LOGIN = "/login";
    public static final String PATH_PRODUCTS = "/products";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_TOKEN = "token";
    public static final String PARAM_ID = "id";
    public static final String PATH_PARAM_ID = "/{id}";
    public static final String PATH_ITEMS = "/items";
    public static final String PARAM_USER_ID = "userId";
    public static final String PATH_ORDERS = "/orders";
    public static final String PARAM_ITEMS = "items";
    public static final String PARAM_PRODUCT_IDS = "productIds";
    public static final String PATH_PAID = "/paid";

    private HttpConstants() {
        throw new UnsupportedOperationException();
    }
}
