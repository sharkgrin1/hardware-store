package store.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import store.commons.HttpConstants;
import store.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final UserRepository userRepository;

    public AuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        var username = request.getParameter(HttpConstants.PARAM_USERNAME);
        if (StringUtils.isBlank(username)) {
            return null;
        }
        final var user = userRepository.findByUsername(username);
        return user.orElse(null);
    }


    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        var token = request.getParameter(HttpConstants.PARAM_TOKEN);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }
}
