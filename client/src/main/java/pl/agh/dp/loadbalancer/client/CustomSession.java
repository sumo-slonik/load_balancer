package pl.agh.dp.loadbalancer.client;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public void update(Object o) throws IOException {

        String idColumnName = "";
        Long idValue = -1L;
        String tableName = o.getClass().getAnnotation(Table.class).name();

        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();

        Field[] fields = o.getClass().getDeclaredFields();

        for ( Field field : fields  ) {
            field.setAccessible(true);
            try {
                if(field.isAnnotationPresent(Id.class)){
                    idColumnName = field.getAnnotation(Column.class).name();
                    idValue = Long.parseLong(field.get(o).toString());
                }else{
                    columns.add( field.getAnnotation(Column.class).name() );
                    values.add( field.get(o).toString() );
                }
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
        }

        String request = "UPDATE " + tableName + " SET ";
        for(int i = 0; i < columns.size()-1; i++){
            request += columns.get(i) + " = '" + values.get(i) + "', ";
        }
        request += columns.get(columns.size()-1) + " = '" + values.get(columns.size()-1) + "'";

        request += " WHERE " + idColumnName + " = " + idValue;

        System.out.println(request);
        writer.println(request);

        // Clear
        getSocketOutput();
    }

    public <T> List<T> findAll(Class<T> entityClass) throws IOException {

        String query = "SELECT * FROM " + entityClass.getAnnotation(Table.class).name();

        List<String> idColumns = new ArrayList<String>();
        List<String> columns = new ArrayList<String>();
        for(Field el : entityClass.getDeclaredFields()) {
                   if(!el.isAnnotationPresent(Id.class)) columns.add(el.toString().split(entityClass.getName()+".")[1]);
                   else idColumns.add(el.toString().split(entityClass.getName()+".")[1]);
                }


        columns = columns.stream().sorted().collect(Collectors.toList());

        writer.println(query);

        String response = getSocketOutput();

        List<T> result = new ArrayList<>();

        ArrayList<String> responseArray = new ArrayList<String>();

        Arrays.stream(response.split("\n")).forEach(el -> responseArray.add(el.substring(1,el.length() -1 )));


        for(String s : responseArray){
            String[] parsedObject = s.split(",");
            StringBuilder entityObjAsJson = new StringBuilder("{");

            int parsedObjectIndex = 0;

            for(int k=0; k<idColumns.size(); k++ ,parsedObjectIndex++){

                entityObjAsJson.append("\"").append(idColumns.get(k)).append("\": \"").append(parsedObject[parsedObjectIndex]);

                if(parsedObjectIndex != parsedObject.length - 1)
                    entityObjAsJson.append("\",");
                else
                    entityObjAsJson.append("\"");
            }

            for(int i=0; i<columns.size() ;i++ ,parsedObjectIndex++){

                entityObjAsJson.append("\"").append(columns.get(i)).append("\": \"").append(parsedObject[parsedObjectIndex]);

                if(parsedObjectIndex != parsedObject.length - 1)
                    entityObjAsJson.append("\",");
                else
                    entityObjAsJson.append("\"");
            }

            entityObjAsJson.append("}");

            Gson gson = new Gson();
            T added = gson.fromJson(entityObjAsJson.toString().replace("\"\"", "\""), entityClass);
            result.add(added);

        }

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
