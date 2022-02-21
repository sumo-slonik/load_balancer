package pl.agh.dp.loadbalancer.client;

import org.hibernate.Session;
import javax.persistence.Table;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CustomSession {

    Session session;
    Socket socket;
    PrintWriter writer;

    public CustomSession(Session session, Socket socket) throws IOException {
        this.session = session;
        this.socket = socket;
        OutputStream output = socket.getOutputStream();
        this.writer = new PrintWriter(output, true);
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void save(Object o) {
        session.save(o);
    }

    public void delete(Object o) {
        session.delete(o);
    }

    public <T> List<T> findAll(Class<T> entityClass) throws IOException {

        String query = "SELECT * FROM " + entityClass.getAnnotation(Table.class).name();

        System.out.println(query);
        writer.println(query);

        String response = getSocketOutput();
        System.out.println(response);

        List<T> result = new ArrayList<>();

        // THIS IS WHERE WE MAP
        // the response onto entities and populate the list

        return result;
    }

    // Read raw socket output
    private String getSocketOutput() throws IOException {

        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String completeOutput = "";
        String selectLine;
        selectLine = reader.readLine();
        while (!selectLine.equals("streamEndedSeq")) {
            //System.out.println(selectLine);
            completeOutput += selectLine + '\n';
            selectLine = reader.readLine();
        }

        return completeOutput;
    }

}
