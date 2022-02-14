package pl.agh.dp.loadbalancer.client;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {

    static String[] requests = {"FROM CLUB", "UPDATE CLUB SET",
            "INSERT INTO CLUB (clubName,city,foundation_date,club_id,province) VALUES club_name,city, foundation_date, club_id,province FROM INSERT_CLUB where club_name = 'nowyklub' ", "DELETE FROM CLUB WHERE"
            , "MinLoad", "RoundRobin","Description"};

    static String[] columnNames = {"clubName", "city", "foundationDate", "club_id", "province"};

    public static void main(String[] args) {



        String hostname = "localhost";
        int port = 9090;

        System.out.println("wybierz liczbe by wykonac nastepujaca operacje");
        System.out.println("0 - " + requests[0]);
        System.out.println("1 - " + requests[1]);
        System.out.println("2 - " + requests[2]);
        System.out.println("3 - " + requests[3]);
        System.out.println("4 - " + requests[4]);
        System.out.println("5 - " + requests[5]);
        System.out.println("6- " +  requests[6]);
        System.out.println("lub wpisz 'disconnect' by zakonczyc");

        try (Socket socket = new Socket(hostname, port)) {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String text;

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a string: ");
            text = sc.nextLine();
            while (!text.equals("disconnect")) {

                switch (text) {
                    case "0":
                        int position = Integer.parseInt(text);

                        writer.println(requests[position]);
                        InputStream selectAllInput = socket.getInputStream();
                        BufferedReader selectAllReader = new BufferedReader(new InputStreamReader(selectAllInput));

                        getOutput(selectAllReader);

                        break;
                    case "1":
                        for (int i = 0; i < columnNames.length; i++) {
                            System.out.println(i + "-" + columnNames[i]);
                        }

                        Scanner updateSetColumnNameScanner = new Scanner(System.in);
                        System.out.println("SET Column");
                        System.out.print("Enter a number: ");
                        String updateColumnNameInput = updateSetColumnNameScanner.nextLine();

                        int updateSetColumnNameInputInt = Integer.parseInt(updateColumnNameInput);

                        Scanner updateSetScanner = new Scanner(System.in);
                        System.out.print("SET " + columnNames[updateSetColumnNameInputInt] + "=:");
                        String updateSetInput = updateSetScanner.nextLine();

                        for (int i = 0; i < columnNames.length; i++) {
                            System.out.println(i + "-" + columnNames[i]);
                        }

                        Scanner updateColumnNameScanner = new Scanner(System.in);
                        System.out.println("Where column");
                        System.out.print("Enter a number: ");
                        String updateWhereColumnNameInput = updateColumnNameScanner.nextLine();

                        int updateWhereColumnNameInputInt = Integer.parseInt(updateWhereColumnNameInput);

                        System.out.println("where:");
                        Scanner updateWhereScanner = new Scanner(System.in);
                        System.out.print("where " + columnNames[updateWhereColumnNameInputInt] + "=:");
                        String updateWhereInput = updateWhereScanner.nextLine();

                        if (updateSetColumnNameInputInt >= 0 && updateSetColumnNameInputInt < columnNames.length
                                && updateWhereColumnNameInputInt >= 0 && updateWhereColumnNameInputInt < columnNames.length) {

                            String updateRequest = requests[1] + " " + columnNames[updateSetColumnNameInputInt] + "=" + updateSetInput + " WHERE " +
                                    columnNames[updateWhereColumnNameInputInt] + "=" + updateWhereInput;

                            System.out.println(updateRequest);

                            writer.println(updateRequest);
                            InputStream updateInput = socket.getInputStream();
                            BufferedReader updateReader = new BufferedReader(new InputStreamReader(updateInput));

                            getOutput(updateReader);
                        }

                        break;
                    case "2":

                        System.out.println(new Date());
//                        Scanner insertClubName = new Scanner(System.in);
//                        System.out.print("Club name: ");
//                        String clubName = insertClubName.nextLine();
//
//                        Scanner insertCity = new Scanner(System.in);
//                        System.out.print("City: ");
//                        String city = insertCity.nextLine();
//
//                        Scanner insertFoundationDate = new Scanner(System.in);
//                        System.out.print("Foundation date (format YYYY-MM-DD): ");
//                        String foundationDate = insertFoundationDate.nextLine();
//
//                        Scanner insertClubId = new Scanner(System.in);
//                        System.out.print("club id: ");
//                        String clubId = insertClubId.nextLine();
//
//                        Scanner insertProvince = new Scanner(System.in);
//                        System.out.print("Province: ");
//                        String province = insertProvince.nextLine();
//
//                        String insertString = requests[2] + clubName + "','" + city +"',STR_TO_DATE('" + foundationDate+"', '%Y-%M-%d'),'" + clubId +"','" + province +"')";
//                        System.out.println(insertString);

                        writer.println(requests[2]);
                        InputStream insertInput = socket.getInputStream();
                        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertInput));

                        getOutput(insertReader);

                        break;
                    case "3":
                        for (int i = 0; i < columnNames.length; i++) {
                            System.out.println(i + "-" + columnNames[i]);
                        }

                        Scanner deleteColumnNameScanner = new Scanner(System.in);
                        System.out.print("Enter a number: ");
                        String deleteColumnNameInput = deleteColumnNameScanner.nextLine();

                        int deleteColumnNameInputInt = Integer.parseInt(deleteColumnNameInput);

                        System.out.println("where:");
                        Scanner deleteWhereScanner = new Scanner(System.in);
                        System.out.print("where " + columnNames[deleteColumnNameInputInt] + "==");
                        String deleteWhereInput = deleteWhereScanner.nextLine();

                        if (deleteColumnNameInputInt >= 0 && deleteColumnNameInputInt < columnNames.length) {

                            String deleteRequest = requests[3] + " " + columnNames[deleteColumnNameInputInt] + "=:" + deleteWhereInput;
                            System.out.println(deleteRequest);

                            writer.println(deleteRequest);
                            InputStream deleteInput = socket.getInputStream();
                            BufferedReader deleteReader = new BufferedReader(new InputStreamReader(deleteInput));

                            getOutput(deleteReader);
                        }
                        break;
                    case "4":
                        String deleteRequest = requests[4];
                        System.out.println(deleteRequest);
                        writer.println(deleteRequest);
                        InputStream changeStrategy = socket.getInputStream();
                        BufferedReader changeStrategyRender = new BufferedReader(new InputStreamReader(changeStrategy));

                        getOutput(changeStrategyRender);

                        break;
                    case "5":
                        String RQ = requests[5];
                        System.out.println(RQ);
                        writer.println(RQ);
                        InputStream changeStrategy1 = socket.getInputStream();
                        BufferedReader changeStrategyRender1 = new BufferedReader(new InputStreamReader(changeStrategy1));

                        getOutput(changeStrategyRender1);
//                        String outputLine1;
//                        outputLine1 = changeStrategyRender1.readLine();
//                        while (!outputLine1.equals("streamEndedSeq")) {
//                            System.out.println(outputLine1);
//                            outputLine1 = changeStrategyRender1.readLine();
//                        }
                        break;
                    case "6":
                        String RQ1 = requests[6];
                        System.out.println(RQ1);
                        writer.println(RQ1);
                        InputStream changeStrategy2 = socket.getInputStream();
                        BufferedReader changeStrategyRender2 = new BufferedReader(new InputStreamReader(changeStrategy2));

                        getOutput(changeStrategyRender2);
                        break;

                }

//                if(text.equals("0") || text.equals("1") ||text.equals("2") ||text.equals("3") ){
//                    int position = Integer.parseInt(text);
//
//                    writer.println(requests[position]);
//                    InputStream input = socket.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//
//
//                    String server_answer = reader.readLine();
//                    System.out.println(server_answer);
//                }

                System.out.print("Enter a string: ");
                text = sc.nextLine();
            }

            socket.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private static void getOutput(BufferedReader reader) throws IOException {

        String selectLine;
        selectLine = reader.readLine();
        while (!selectLine.equals("streamEndedSeq")) {
            System.out.println(selectLine);
            selectLine = reader.readLine();
        }

    }

}