package pl.hrinvestment.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.hrinvestment.config.Config;
import pl.hrinvestment.user.User;
import pl.hrinvestment.user.UserService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parser;
import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneOffset.UTC;
import static java.util.Optional.empty;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static pl.hrinvestment.config.Keys.HMAC_SECRET;

@Service
public final class TokenHandler {

    private final Logger logger = Logger.getLogger(getClass());

    private final byte[] secret;

    @Autowired
    private UserService userService;

    @Autowired
    public TokenHandler(Config config) {
        this.secret = parseBase64Binary(config.get(HMAC_SECRET));
    }

    public Optional<User> parseUserFromToken(String token) {
        try {
            Claims body = parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            LocalDateTime expires = ofInstant(ofEpochMilli(body.getExpiration().getTime()), UTC);
            return now().isAfter(expires) ? empty() : userService.byUsername(body.getSubject());
        } catch (Exception e) {
            logger.error("Can not parse token!", e);
            return empty();
        }
    }

    public String createTokenForUser(User user) {
        return builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(now().plusDays(10).toInstant(UTC)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
