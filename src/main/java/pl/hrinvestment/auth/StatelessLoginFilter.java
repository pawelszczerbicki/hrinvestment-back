package pl.hrinvestment.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import pl.hrinvestment.user.User;
import pl.hrinvestment.user.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
public class StatelessLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenAuthenticationService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        tokenService.addHeaderAuthentication(response, authentication);
        getContext().setAuthentication(new UserAuthentication(userService.loadUserByUsername(authentication.getName())));
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User u = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword()));
        } catch (IOException e) {
            redirect(response);
            return null;
        }
    }

    private void redirect(HttpServletResponse response) {
        try {
            response.sendRedirect("/auth/unauthorized");
        } catch (IOException e1) {
            response.setStatus(SC_UNAUTHORIZED);
        }
    }
}