package pl.hrinvestment.user;

import org.springframework.stereotype.Repository;
import pl.hrinvestment.generic.GenericDao;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class UserDao extends GenericDao<User>{

    public UserDao() {
        super(User.class);
    }

    public Optional<User> byUsername(String email) {
        return findOne(where("email").is(email));
    }
}
