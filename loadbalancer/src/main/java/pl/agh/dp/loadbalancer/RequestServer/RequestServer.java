package pl.agh.dp.loadbalancer.RequestServer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.command.DatabasesExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@ComponentScan("pl/agh/dp/loadbalancer/command")
@RequiredArgsConstructor
public class RequestServer {

    private Thread socketServerThread;
    private Boolean haveToStop = false;

    @Autowired
    private DatabasesExecutor databasesExecutor;

    @Autowired
    private DatabasesInterface databasesInterface;

    private class SocketServerExecutor implements Runnable {

        DatabasesExecutor databasesExecutor;

        SocketServerExecutor(DatabasesExecutor databasesExecutor) {
            this.databasesExecutor = databasesExecutor;
        }

        @Override
        public void run() {
            int port = 9090;

            try (ServerSocket serverSocket = new ServerSocket(port)) {

                System.out.println("Server is listening on port " + port);

                DatabasesExecutor dbExecutor = this.databasesExecutor;

                while (!haveToStop) {
                    System.out.println("Żyję");
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected");

                    System.out.println("New client connected"
                            + socket.getInetAddress()
                            .getHostAddress());

                    ClientHandler clientSock
                            = new ClientHandler(socket, databasesInterface, this.databasesExecutor);

                    new Thread(clientSock).start();
                }

            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    @PostConstruct
    public void startSocketServer() {
        this.socketServerThread = new Thread(new SocketServerExecutor(databasesExecutor));
        this.socketServerThread.start();
    }

    @PreDestroy
    private void destructor() {
        haveToStop = true;
        try {
            socketServerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}