package pl.agh.dp.loadbalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.agh.dp.loadbalancer.socetServer.SocetServer;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("pl.agh.dp.loadbalancer.data.acces.domain")
public class LoadbalancerApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(LoadbalancerApplication.class, args);

    }
}
