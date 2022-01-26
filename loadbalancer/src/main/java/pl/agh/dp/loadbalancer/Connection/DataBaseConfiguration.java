package pl.agh.dp.loadbalancer.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstanceImpl;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBaseNumber;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

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

    @Bean
    @ConfigurationProperties("database.configuration.third")
    DataBaseConnectionConfig thirdConfig()
    {
        return new DataBaseConnectionConfig();
    }

}
