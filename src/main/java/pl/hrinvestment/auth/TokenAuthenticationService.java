package pl.hrinvestment.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.hrinvestment.user.User;
import pl.hrinvestment.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static java.util.Optional.empty;

@Service
public class TokenAuthenticationService {

    public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private UserService userService;

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
