package pl.agh.loadbalancer.loadbalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.agh.loadbalancer.loadbalancer.socketServer.SocketServer;

import java.io.IOException;

@SpringBootApplication
public class LoadbalancerApplication {

    public static void main(String[] args) throws IOException {

        SocketServer server = new SocketServer();
        server.startSocketServer();
        SpringApplication.run(LoadbalancerApplication.class, args);

    }

}
