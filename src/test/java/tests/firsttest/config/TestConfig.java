package tests.firsttest.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by maxim_000 on 10.05.2017.
 */
@Configuration
@ComponentScan("ru.alcereo")
public class TestConfig extends tests.TestConfig {
    @Bean
    public SessionFactory sessionFactory(){
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }
}
