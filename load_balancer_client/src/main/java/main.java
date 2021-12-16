import java.net.*;
import java.io.*;
import java.util.Scanner;

public class main {

    static String[] requests = {"SELECT cos tam FROM tabela","INSERT INTO tabela cos tam cos tam",
            "UPDATE TABLE SET ... WHERE cos tam","DELETE FROM tabela WHERE cos tam"};


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


                if(text.equals("0") || text.equals("1") ||text.equals("2") ||text.equals("3") ){
                    int position = Integer.parseInt(text);

                    writer.println(requests[position]);
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));


                    String server_answer = reader.readLine();
                    System.out.println(server_answer);
                }

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
