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

                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);

                    String request;

                    request = reader.readLine();
                    while (request != null && !request.equals("disconnect")) {
                        System.out.println(request);

                        // Tu obsluga zapytania
                        String[] splitedRequest = request.split(" ");
                        if (splitedRequest.length > 0) {
                            switch (splitedRequest[0]) {
                                case "FROM":
                                    System.out.println("obsluga selecta");
                                    String res = dbExecutor.performSelect(request);
                                    writer.println(res);
                                    break;
                                case "DELETE":
                                    System.out.println("obsluga DELETE");
                                    writer.println(dbExecutor.performDelete(request));
                                    break;
                                case "INSERT":
                                    System.out.println("obsluga INSERTA");
                                    writer.println(dbExecutor.performInsert(request));
                                    break;
                                case "UPDATE":
                                    System.out.println("obsluga UPDATE");
                                    writer.println(dbExecutor.performUpdate(request));
                                    break;
                                case "RoundRobin":
                                    System.out.println("zmiana na RoundRobin");
                                    writer.println("zmiana na RoundRobin");
                                    databasesInterface.changeBalanceStrategyAsRoundRobin();
                                    break;
                                case "MinLoad":
                                    System.out.println("changed to na MinLoad");
                                    writer.println("changed to na MinLoad");
                                    databasesInterface.changeBalanceStrategyAsMinLoad();
                                    break;
                                case "Description":
                                    writer.println(databasesInterface.getConnectedDataBaseDescription());
                                    break;
                            }


                        }
                        writer.println("streamEndedSeq");

                        request = reader.readLine();
                    }
                    socket.close();
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