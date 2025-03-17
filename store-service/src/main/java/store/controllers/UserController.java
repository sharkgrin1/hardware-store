package store.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.commons.HttpConstants;
import store.repositories.domain.User;
import store.security.JwtTokenManager;
import store.services.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;

    public UserController(UserService userService, JwtTokenManager jwtTokenManager) {
        this.userService = userService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @PostMapping(HttpConstants.PATH_LOGIN)
    public Map<String, Object> login(@RequestBody @Valid User user) {
        final var found = userService.login(user);
        return Map.of("token", jwtTokenManager.generateToken(found.getUsername()), "id", found.getId(), "role", found.getType().name());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Void> userNotFoundException(Throwable ex) {
        LOGGER.warn("Thrown", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
