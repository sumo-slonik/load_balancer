package pl.agh.dp.loadbalancer.client;

import lombok.SneakyThrows;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class QueryInterceptor extends EmptyInterceptor {

    Socket socket;
    PrintWriter writer;

    QueryInterceptor(Socket socket) throws IOException {
        this.socket = socket;
        OutputStream output = socket.getOutputStream();
        this.writer = new PrintWriter(output, true);
    }


    @SneakyThrows
    @Override
    public boolean onSave(Object entity, Serializable id,
                          Object[] state, String[] propertyNames, Type[] types) {

        String query = "INSERT INTO ";
        query += entity.getClass().getAnnotation(Table.class).name();
        query += " (";

        Field[] fields = entity.getClass().getDeclaredFields();

        String[] columnNames = new String[propertyNames.length];
        for(int i = 0; i < propertyNames.length; i++){
            columnNames[i] = entity.getClass().getDeclaredField(propertyNames[i]).getAnnotation(Column.class).name();
        }

        query += String.join(", ", columnNames);
        query += ") VALUES (";
        for(int i = 0; i < state.length-1; i++){
            query += "'" + state[i].toString() + "', ";
        }
        query += "'" + state[state.length-1].toString() + "'";
        query += ")";

        System.out.println(query);
        writer.println(query);

        // Clear
        getSocketOutput();
        return false;
        //return super.onSave(entity, id,state, propertyNames, types);
    }

    @SneakyThrows
    @Override
    public void onDelete(Object entity, Serializable id,
                         Object[] state, String[] propertyNames, Type[] types) {

        String query = "DELETE FROM ";
        query += entity.getClass().getAnnotation(Table.class).name();
        query += " WHERE ";
        for(int i = 0; i < state.length-1; i++){
            if(state[i].getClass() == Date.class){
                //System.out.println("skip date");
            }else{
                query += entity.getClass().getDeclaredField(propertyNames[i]).getAnnotation(Column.class).name() + "='" + state[i].toString() + "' AND ";
            }
        }
        query += propertyNames[state.length-1] + "='" + state[state.length-1].toString() + "'";

        System.out.println(query);
        writer.println(query);

        // Clear
        getSocketOutput();
    }

    private void getSocketOutput() throws IOException {

        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String selectLine;
        selectLine = reader.readLine();
        while (!selectLine.equals("streamEndedSeq")) {
            System.out.println(selectLine);
            selectLine = reader.readLine();
        }

    }

}
