package store.repositories.jooq;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import store.repositories.domain.User;
import store.repositories.UserRepository;
import store.repositories.converters.UserMapper;
import store.repositories.converters.UserUnmapper;

import java.util.Optional;

import static store.jooq.tables.Users.USERS;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dsl;
    private final UserUnmapper unmapper;
    private final UserMapper mapper;

    public UserRepositoryImpl(DSLContext dsl, UserUnmapper unmapper, UserMapper mapper) {
        this.dsl = dsl;
        this.unmapper = unmapper;
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
