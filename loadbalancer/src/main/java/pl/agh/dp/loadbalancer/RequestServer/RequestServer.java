package pl.agh.dp.loadbalancer.RequestServer;

import pl.agh.dp.loadbalancer.command.DatabasesExecutor;

import javax.annotation.PostConstruct;
import java.net.*;
import java.io.*;

public class RequestServer {

    @PostConstruct
    public void startSocketServer() {
        int port = 9090;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            DatabasesExecutor dbExecutor = new DatabasesExecutor();

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String request;

                request = reader.readLine();
                while(request != null && !request.equals("disconnect")) {
                    System.out.println(request);

                    // Tu obsluga zapytania
                    String[] splitedRequest = request.split(" ");
                    if(splitedRequest.length > 0){
                        switch (splitedRequest[0]) {
                            case "SELECT":
                                System.out.println("obsluga selecta");
                                writer.println(dbExecutor.performSelect(request));
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
                        }


                    }

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