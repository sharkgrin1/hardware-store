package store.api.domain;

import java.util.Arrays;

public enum Type {
    ROLE_ADMIN("A"), ROLE_CUSTOMER("C");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Type parseType(String val) {
        final var found = Arrays.stream(Type.values()).filter(x -> x.type.equals(val)).findFirst();
        return found.orElse(null);
    }
}
