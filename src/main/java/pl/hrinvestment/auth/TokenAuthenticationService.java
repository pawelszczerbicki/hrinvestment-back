package pl.hrinvestment.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.hrinvestment.config.Config;
import pl.hrinvestment.config.Keys;
import pl.hrinvestment.user.User;
import pl.hrinvestment.user.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.empty;

@Service
public class TokenAuthenticationService {

    public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    private String redirectTo;

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private Config config;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        redirectTo = config.get(Keys.FRONTEND_URL);
    }

    public String calculateTargetUrl(Authentication authentication) {
        return format("%s/auth?%s=%s", redirectTo, AUTH_HEADER_NAME, getToken(authentication));
    }

    public void addHeaderAuthentication(HttpServletResponse response, Authentication authentication) {
        response.addHeader(AUTH_HEADER_NAME, getToken(authentication));
    }

    public String getToken(Authentication auth) {
        return tokenHandler.createTokenForUser(userService.loadUserByUsername(auth.getName()));
    }

    public Optional<User> getUser(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_NAME);
        return token != null ? tokenHandler.parseUserFromToken(token) : empty();
    }
}
