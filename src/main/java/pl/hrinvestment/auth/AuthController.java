package pl.hrinvestment.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hrinvestment.user.Permission;
import pl.hrinvestment.user.User;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AuthController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/login", method = POST)
    public User login() {
        return securityService.currentUser().get();
    }

    @RequestMapping(value = "/permissions", method = GET)
    public Permission[] permissions() {
        return Permission.values();
    }
}
