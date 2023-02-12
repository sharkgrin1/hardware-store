package store.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.repositories.UserRepository;
import store.repositories.domain.User;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(User user) {
        final var found = userRepository.findByUsername(user.getUsername());
        if (found.isEmpty()) {
            throw new RuntimeException("User does not exist");
        }
        if (!passwordEncoder.matches(user.getPassword(), found.get().getPassword())) {
            throw new RuntimeException("Password is wrogn");
        }
        return found.get();
    }
}
