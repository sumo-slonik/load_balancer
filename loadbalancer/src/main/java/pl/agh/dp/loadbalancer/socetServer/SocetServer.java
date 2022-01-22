package pl.agh.dp.loadbalancer.socetServer;

import pl.agh.dp.loadbalancer.command.DatabasesExecutor;

import java.net.*;
import java.io.*;

public class SocetServer{

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
                                dbExecutor.performSelect(request);
                                writer.println("Server przetworzyl selecta");
                                break;
                            case "DELETE":
                                System.out.println("obsluga DELETE");
                                dbExecutor.performDelete(request);
                                writer.println("Server przetworzyl DELETE");
                                break;
                            case "INSERT":
                                System.out.println("obsluga INSERTA");
                                dbExecutor.performInsert(request);
                                writer.println("Server przetworzyl Inserta");
                                break;
                            case "UPDATE":
                                System.out.println("obsluga UPDATE");
                                dbExecutor.performUpdate(request);
                                writer.println("Server przetworzyl update");
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