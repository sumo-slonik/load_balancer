package pl.agh.dp.loadbalancer.client;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {

    static String[] requests = {"FROM CLUB","UPDATE CLUB SET",
            "INSERT INTO CLUB xxddddddd","DELETE FROM CLUB WHERE"};

    static String[] columnNames = {"club_name", "city", "foundation_date (format YYYY-MM-DD)", "club_id", "province"};

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 9090;

        System.out.println("wybierz liczbe by wykonac nastepujaca operacje");
        System.out.println("0 - "+requests[0]);
        System.out.println("1 - "+requests[1]);
        System.out.println("2 - "+requests[2]);
        System.out.println("3 - "+requests[3]);
        System.out.println("lub wpisz 'disconnect' by zakonczyc");

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String text;

            Scanner sc= new Scanner(System.in);
            System.out.print("Enter a string: ");
            text = sc.nextLine();
            while(!text.equals("disconnect")) {

                switch (text) {
                    case "0":
                        int position = Integer.parseInt(text);

                        writer.println(requests[position]);
                        InputStream selectAllInput = socket.getInputStream();
                        BufferedReader selectAllReader = new BufferedReader(new InputStreamReader(selectAllInput));


//                        String selectAllServerAnswer = selectAllReader.readLine();
//                        String selectAllServerAnswer =  selectAllReader.lines().collect(Collectors.joining());
//                        System.out.println(selectAllServerAnswer);

                        String selectLine;
                        selectLine = selectAllReader.readLine();
                        while (!selectLine.equals("streamEndedSeq")) {
                            System.out.println(selectLine);
                            selectLine = selectAllReader.readLine();
                        }
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

                            String updateRequest = requests[1] + " " + columnNames[updateSetColumnNameInputInt] + "=:" + updateSetInput+" WHERE "+
                                    columnNames[updateWhereColumnNameInputInt] + "=:" + updateWhereInput;

                            System.out.println(updateRequest);

                            writer.println(updateRequest);
                            InputStream updateInput = socket.getInputStream();
                            BufferedReader updateReader = new BufferedReader(new InputStreamReader(updateInput));


                            String updateLine;
                            updateLine = updateReader.readLine();
                            while (!updateLine.equals("streamEndedSeq")) {
                                System.out.println(updateLine);
                                updateLine = updateReader.readLine();
                            }
                        }

                        break;
                    case "2":

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


                            String deleteLine;
                            deleteLine = deleteReader.readLine();
                            while (!deleteLine.equals("streamEndedSeq")) {
                                System.out.println(deleteLine);
                                deleteLine = deleteReader.readLine();
                            }
                        }
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

}