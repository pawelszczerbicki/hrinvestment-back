package pl.hrinvestment.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@ComponentScan("pl.hrinvestment")
@PropertySource("classpath:app.properties")
public class InvestmentContext extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //TODO reconfigure CORS after test phase
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "HEAD", "DELETE");
    }
}
