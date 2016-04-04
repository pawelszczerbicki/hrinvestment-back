package pl.hrinvestment.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.hrinvestment.user.User;

import java.util.Optional;

@Service
public class SecurityService {

    public Optional<User> currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UserAuthentication)
            return Optional.of((User) auth.getDetails());
        return Optional.empty();
    }
}