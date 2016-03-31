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

    public Integer asInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean asBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
