package pl.hrinvestment.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User();
    }

    public Optional<User> byUsername(String username) {
        return Optional.of(new User());
    }
}
