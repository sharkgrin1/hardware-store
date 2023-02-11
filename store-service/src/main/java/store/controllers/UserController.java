package store.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import store.services.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Value("${secret}")
    private String secret;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(HttpConstants.PATH_LOGIN)
    public Map<String, Object> login(@RequestBody @Valid User user) {
        final var found = userService.login(user);
        final var time = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        final var token = DigestUtils.md5Hex(found.getUsername() + secret + time.toInstant(ZoneOffset.UTC));
        return Map.of("token", token, "id", found.getId());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Void> userNotFoundException(Throwable ex) {
        LOGGER.warn("Thrown", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
