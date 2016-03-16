package pl.hrinvestment.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan("pl.hrinvestment")
@PropertySource("classpath:app.properties")
public class InvestmentContext {
}
