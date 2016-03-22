package pl.hrinvestment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class Config {
    @Autowired
    private Environment env;

    public String get(String key) {
        return env.getProperty(key);
    }
}