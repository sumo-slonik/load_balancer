package pl.agh.dp.loadbalancer.Connection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agh.dp.loadbalancer.RequestServer.RequestServer;
import pl.agh.dp.loadbalancer.command.DatabasesExecutor;

@Configuration
public class RequestServerConfig {

    @Bean
    DatabasesExecutor databasesExecutor()
    {
        return new DatabasesExecutor();
    }
    @Bean
    RequestServer requestServer(){
        return new RequestServer();
    }
}
