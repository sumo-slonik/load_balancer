package pl.agh.loadbalancer.loadbalancer.socketServer;

import java.net.*;
import java.io.*;

public class SocketServer{

    public void startSocketServer() {
        int port = 9090;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

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
                        if(splitedRequest[0].equals("SELECT")){
                            System.out.println("obsluga selecta");
                            //tu obsluga selecta
                            writer.println("Server przetworzyl selecta");
                        }
                        else if(splitedRequest[0].equals("DELETE") || splitedRequest[0].equals("INSERT") || splitedRequest[0].equals("UPDATE") ){
                            System.out.println("obsluga DELETE, INSERT, UPDATE");
                            //tu obsluga delete insert i update
                            writer.println("Server przetworzyl DELETE INSERT lub UPDATE");
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
