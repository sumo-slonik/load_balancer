package pl.agh.dp.loadbalancer.Connection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseConfiguration {
    @Bean
    @ConfigurationProperties("database.configuration.first")
    DataBaseConnectionConfig firstConfig()
    {
        return new DataBaseConnectionConfig();
    }

}
