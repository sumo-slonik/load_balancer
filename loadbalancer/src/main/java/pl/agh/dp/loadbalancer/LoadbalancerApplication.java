package pl.agh.dp.loadbalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("pl.agh.dp.loadbalancer.Connection")
//@EnableJpaRepositories(basePackages = { "pl.agh.dp.loadbalancer" })
public class LoadbalancerApplication {
    private static ApplicationContext applicationContext;


    public static void main(String[] args) throws IOException {
        SpringApplication.run(LoadbalancerApplication.class, args);
        System.out.println("kuba");
    }


}
