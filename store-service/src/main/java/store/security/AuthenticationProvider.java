package store.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import store.repositories.domain.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private final String secret;

    public AuthenticationProvider(String secret) {
        this.secret = secret;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var credentials = authentication.getCredentials();
        if (!(credentials instanceof String token)) {
            return null;
        }
        final var principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            return null;
        }
        if (!user.isEnabled()) {
            throw new DisabledException("User is disabled");
        }

        final var time = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        final var sign = DigestUtils.md5Hex(user.getUsername() + secret + time.toInstant(ZoneOffset.UTC));

        if (!sign.equalsIgnoreCase(token)) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
