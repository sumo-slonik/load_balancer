package pl.agh.dp.loadbalancer.RequestServer;

import lombok.SneakyThrows;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.command.DatabasesExecutor;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final DatabasesInterface databasesInterface;
    private final DatabasesExecutor databasesExecutor;

    public ClientHandler(Socket socket, DatabasesInterface databasesInterface, DatabasesExecutor databasesExecutor)
    {
        this.socket = socket;
        this.databasesInterface = databasesInterface;
        this.databasesExecutor = databasesExecutor;
    }

    @Override
    public void run() {
        try {
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
                            String res = databasesExecutor.performSelect(request);
                            writer.println(res);
                            break;
                        case "DELETE":
                            System.out.println("obsluga DELETE");
                            writer.println(databasesExecutor.performDelete(request));
                            break;
                        case "INSERT":
                            System.out.println("obsluga INSERTA");
                            Arrays.stream(splitedRequest).forEach(System.out::println);
//                            writer.println(databasesExecutor.
//                                    performInsert(
//                                            Arrays.stream(
//                                                    Arrays.copyOfRange(splitedRequest, 1, splitedRequest.length)
//                                            ).collect(Collectors.joining())
//                                    ));
                            writer.println(databasesExecutor.performInsert(request));
                            break;
                        case "UPDATE":
                            System.out.println("obsluga UPDATE");
                            writer.println(databasesExecutor.performUpdate(request));
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
                            writer.println(databasesInterface.getAllDataBasesDescription());
                            break;
                    }


                }
                writer.println("streamEndedSeq");

                request = reader.readLine();
            }
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
