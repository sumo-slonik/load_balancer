package pl.agh.dp.loadbalancer.client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pl.agh.dp.loadbalancer.Club.ClubEntity;
import pl.agh.dp.loadbalancer.Employee.EmployeeEntity;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Client {

    static String[] requests = {"SELECT *", "INSERT", "DELETE", "MinLoad", "RoundRobin", "Description"};
    Socket socket;

    @PostConstruct
    private void clientLoop() throws IOException {

        System.out.println("Wybierz liczbe by wykonac nastepujaca operacje:");
        System.out.println("0 - " + requests[0]);
        System.out.println("1 - " + requests[1]);
        System.out.println("2 - " + requests[2]);
        System.out.println("3 - " + requests[3]);
        System.out.println("4 - " + requests[4]);
        System.out.println("5 - " + requests[5]);
        System.out.println("Lub wpisz 'disconnect' by zakonczyc");

        // Creating session and the associated socket
        CustomSession session = HibernateUtil.getSession();
        socket = session.getSocket();

        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        // Menu handling
        String text;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        text = sc.nextLine();
        while (!text.equals("disconnect")) {

            switch (text) {
                case "0":
                    // SELECT

                    List<ClubEntity> results = session.findAll(ClubEntity.class);
                    System.out.println(results);

                    break;
                case "1":
                    // INSERT

                    ClubEntity club1 = new ClubEntity("klub3", "Sosnowiec3", Date.valueOf("1997-03-10"), 1324L, "asd");
                    session.save(club1);
//                  EmployeeEntity empl1 = new EmployeeEntity(1L, "Jan", "Kowalski");
//                  session.save(empl1);

                    break;
                case "2":
                    // DELETE

                    ClubEntity club2 = new ClubEntity("Polska", "Sosnowiec", Date.valueOf("1997-03-10"), 1324L, "Slaskie");
                    session.delete(club2);

                    break;

                case "3":
                case "4":
                case "5":
                    // DB modes

                    String request = requests[Integer.parseInt(text)];
                    System.out.println(request);
                    writer.println(request);
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    getOutput(reader);
                    break;

            }

            System.out.print("Enter a string: ");
            text = sc.nextLine();
        }

        socket.close();

    }

    private static void getOutput(BufferedReader reader) throws IOException {

        String selectLine;
        selectLine = reader.readLine();
        while (!selectLine.equals("streamEndedSeq")) {
            System.out.println(selectLine);
            selectLine = reader.readLine();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

}