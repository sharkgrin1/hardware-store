package store.commons;

public final class HttpConstants {

    public static final String PATH_LOGIN = "/login";
    public static final String PATH_PRODUCTS = "/products";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_TOKEN = "token";
    public static final String PARAM_ID = "id";
    public static final String PATH_PARAM_ID = "/{id}";
    public static final String PATH_ITEMS = "/items";

    private HttpConstants() {
        throw new UnsupportedOperationException();
    }
}
