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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Client {

    static String[] requests = {"SELECT *", "INSERT", "INSERT reczny", "DELETE", "DELETE reczny", "MinLoad", "RoundRobin", "Description"};
    Socket socket;

    @PostConstruct
    private void initDb() throws IOException {

        String hostname = "localhost";
        int port = 9090;

        System.out.println("wybierz liczbe by wykonac nastepujaca operacje");
        System.out.println("0 - " + requests[0]);
        System.out.println("1 - " + requests[1]);
        System.out.println("2 - " + requests[2]);
        System.out.println("lub wpisz 'disconnect' by zakonczyc");

        socket = new Socket(hostname, port);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

        QueryInterceptor<EmployeeEntity> interceptor = new QueryInterceptor<EmployeeEntity>(socket, writer);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.withOptions().interceptor(interceptor).openSession();


            String text;

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a string: ");
            text = sc.nextLine();
            while (!text.equals("disconnect")) {

                switch (text) {
                    case "0":

//                        ClubEntity club0 = session.get(ClubEntity.class, 32L);

                        List< ClubEntity > employees = session.createQuery("from ClubEntity", ClubEntity.class).list();
                        employees.forEach(e -> System.out.println(e.getClub_name()));

                        InputStream selectAllInput = socket.getInputStream();
                        BufferedReader selectAllReader = new BufferedReader(new InputStreamReader(selectAllInput));

                        getOutput(selectAllReader);

                        break;
                    case "1":

                        ClubEntity club1 = new ClubEntity("Polska", "Sosnowiec", Date.valueOf("1997-03-10"), 1324L, "Slaskie");
                        session.save(club1);
//                        EmployeeEntity empl1 = new EmployeeEntity(1L, "Jan", "Kowalski");
//                        session.save(empl1);

                        break;
                    case "2":

                        ClubEntity club2 = new ClubEntity("Polska", "Sosnowiec", Date.valueOf("1997-03-10"), 1324L, "Slaskie");
                        session.delete(club2);

                        break;

                    case "3":

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