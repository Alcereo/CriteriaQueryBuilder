package tests.unitTests;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * Created by maxim_000 on 10.05.2017.
 */
@Configuration
public class TestConfig extends tests.TestConfig {
    @Bean
    @Override
    public SessionFactory sessionFactory(){
        return mock(SessionFactory.class);
    }
}
