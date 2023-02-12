package store.repositories.jooq;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import store.repositories.UserRepository;
import store.repositories.converters.UserMapper;
import store.repositories.domain.User;

import java.util.Optional;

import static store.jooq.tables.Users.USERS;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dsl;
    private final UserMapper mapper;

    public UserRepositoryImpl(DSLContext dsl, UserMapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(dsl.selectFrom(USERS)
                        .where(USERS.USERNAME.eq(username))
                        .fetchOne())
                .map(mapper::map);
    }
}
