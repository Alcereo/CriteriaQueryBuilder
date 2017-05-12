package unitTests.criteria.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * Created by maxim_000 on 10.05.2017.
 */
@Configuration
@ComponentScan("ru.alcereo")
public class TestConfig {
    @Bean
    public SessionFactory mockedSessionFactory(){
        return mock(SessionFactory.class);
    }
}
