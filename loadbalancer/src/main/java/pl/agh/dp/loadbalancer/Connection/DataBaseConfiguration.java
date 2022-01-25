package pl.agh.dp.loadbalancer.Connection;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
public class DataBaseConfiguration {
    @Bean
    @ConfigurationProperties("database.configuration.first")
    DataBaseConnectionConfig firstConfig()
    {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConfigurationProperties("database.configuration.second")
    DataBaseConnectionConfig secondConfig()
    {
        return new DataBaseConnectionConfig();
    }



}
