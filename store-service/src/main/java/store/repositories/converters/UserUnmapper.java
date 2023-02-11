package store.repositories.converters;

import org.jetbrains.annotations.NotNull;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.stereotype.Component;
import store.repositories.domain.User;
import store.jooq.tables.records.UsersRecord;

@Component
public class UserUnmapper implements RecordUnmapper<User, UsersRecord> {
    @Override
    public @NotNull UsersRecord unmap(User user) throws MappingException {
        return null;
    }
}
