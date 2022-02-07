package pl.agh.dp.loadbalancer.Connection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
public class DataBaseConfiguration {
    @Bean
    @ConditionalOnExpression("${database.configuration.first.enabled:true}")
    @ConfigurationProperties("database.configuration.first")
    DataBaseConnectionConfig firstConfig() {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConditionalOnExpression("${database.configuration.second.enabled:true}")
    @ConfigurationProperties("database.configuration.second")
    DataBaseConnectionConfig secondConfig() {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConditionalOnExpression("${database.configuration.third.enabled:true}")
    @ConfigurationProperties("database.configuration.third")
    DataBaseConnectionConfig thirdConfig() {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConditionalOnExpression("${database.configuration.fourth.enabled:true}")
    @ConfigurationProperties("database.configuration.fourth")
    DataBaseConnectionConfig fourthConfig() {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConditionalOnExpression("${database.configuration.fifth.enabled:true}")
    @ConfigurationProperties("database.configuration.fifth")
    DataBaseConnectionConfig fifthConfig() {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConditionalOnExpression("${database.configuration.sixth.enabled:true}")
    @ConfigurationProperties("database.configuration.sixth")
    DataBaseConnectionConfig sixthConfig() {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConditionalOnExpression("${database.configuration.seventh.enabled:true}")
    @ConfigurationProperties("database.configuration.seventh")
    DataBaseConnectionConfig seventhConfig() {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConditionalOnExpression("${database.configuration.eighth.enabled:true}")
    @ConfigurationProperties("database.configuration.eighth")
    DataBaseConnectionConfig eighthConfig() {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConditionalOnExpression("${database.configuration.ninth.enabled:true}")
    @ConfigurationProperties("database.configuration.ninth")
    DataBaseConnectionConfig ninthConfig() {
        return new DataBaseConnectionConfig();
    }

    @Bean
    @ConditionalOnExpression("${database.configuration.tenth.enabled:true}")
    @ConfigurationProperties("database.configuration.tenth")
    DataBaseConnectionConfig tenthConfig() {
        return new DataBaseConnectionConfig();
    }

}
