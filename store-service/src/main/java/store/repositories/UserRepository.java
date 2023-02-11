package store.repositories;

import store.repositories.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
