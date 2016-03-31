package pl.hrinvestment.spring;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.hrinvestment.config.Config;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.UnknownHostException;

import static com.mongodb.MongoCredential.createCredential;
import static java.util.Collections.singletonList;
import static pl.hrinvestment.auth.TokenAuthenticationService.AUTH_HEADER_NAME;
import static pl.hrinvestment.config.Keys.*;

@EnableWebMvc
@Configuration
@ComponentScan("pl.hrinvestment")
@PropertySource("classpath:app.properties")
@EnableSwagger2
public class InvestmentContext extends WebMvcConfigurerAdapter {

    @Autowired
    private Config c;

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        MongoCredential credential = createCredential(c.get(DB_LOGIN), c.get(DB_SCHEMA), c.get(DB_PASSWORD).toCharArray());
        MongoClient client = new MongoClient(new ServerAddress(c.get(DB_URL), c.asInt(DB_PORT)), singletonList(credential));
        return new MongoTemplate(new SimpleMongoDbFactory(client, c.get(DB_SCHEMA)));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //TODO reconfigure CORS after test phase
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "HEAD", "DELETE").exposedHeaders(AUTH_HEADER_NAME);
    }
}
