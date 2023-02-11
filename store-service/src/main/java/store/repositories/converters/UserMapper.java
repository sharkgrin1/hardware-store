package store.repositories.converters;

import org.jetbrains.annotations.Nullable;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import store.api.domain.Type;
import store.repositories.domain.User;
import store.jooq.tables.records.UsersRecord;
@Component
public class UserMapper implements RecordMapper<UsersRecord, User> {
    @Override
    public @Nullable User map(UsersRecord record) {
        final var user = new User();
        user.setId(record.getId());
        user.setUsername(record.getUsername());
        user.setPassword(record.getPassword());
        user.setType(Type.parseType(record.getType()));
        return user;
    }
}
