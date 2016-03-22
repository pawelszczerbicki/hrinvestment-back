package pl.hrinvestment.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hrinvestment.user.User;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AuthController {

    @RequestMapping(value = "/login", method = POST)
    public User login() {
        return new User("test user");
    }
}
