package pl.agh.dp.loadbalancer.client;

import com.google.gson.Gson;
import org.hibernate.Session;
import javax.persistence.Table;
import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<Class> types = new ArrayList<>();

        Arrays.stream(entityClass.getDeclaredFields()).forEach(el -> columns.add(el.toString().split(entityClass.getName()+".")[1]));

//        Arrays.stream(entityClass.getDeclaredFields()).forEach(el -> types.add(el.toString().split(entityClass.getName()+".")[0]));
//        Arrays.stream(entityClass.getDeclaredFields()).(el -> types.add(el.toString().split(entityClass.getName()+".")[0].strip().split(".")[2]));
        Arrays.stream(entityClass.getDeclaredFields()).forEach(el -> types.add(el.getType()));

        System.out.println(types.get(0).getTypeName());

        System.out.println(query);
        writer.println(query);

        String response = getSocketOutput();
        System.out.println(response);

        List<T> result = new ArrayList<>();

        ArrayList<String> responseArray = new ArrayList<String>();

        Arrays.stream(response.split("\n")).forEach(el -> responseArray.add(el.substring(1,el.length() -1 )));

        System.out.println(responseArray);

        for(String s : responseArray){
            String[] parsedObject = s.split(",");
            StringBuilder entityObjAsJson = new StringBuilder("{");
            for(int i=0; i<parsedObject.length ;i++){
                System.out.println(parsedObject[i]+" "+columns.get(i));

                entityObjAsJson.append("\"").append(columns.get(i)).append("\": \"").append(parsedObject[i]);

                if(i != parsedObject.length - 1)
                    entityObjAsJson.append("\",");
                else
                    entityObjAsJson.append("\"");
            }

            entityObjAsJson.append("}");

            Gson gson = new Gson();
            T added = gson.fromJson(entityObjAsJson.toString().replace("\"\"", "\""), entityClass);
            result.add(added);

        }

        // THIS IS WHERE WE MAP
        // the response onto entities and populate the list
        System.out.println(result);
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
