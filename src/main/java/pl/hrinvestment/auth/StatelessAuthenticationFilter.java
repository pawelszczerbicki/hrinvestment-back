package pl.hrinvestment.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import pl.hrinvestment.user.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
public class StatelessAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private TokenAuthenticationService tokenAuthService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = getContext().getAuthentication();
        if (!(authentication instanceof UserAuthentication)) {
            Optional<User> user = tokenAuthService.getUser((HttpServletRequest) request);
            if (user.isPresent()) getContext().setAuthentication(new UserAuthentication(user.get()));
        }
        chain.doFilter(request, response);
    }
}