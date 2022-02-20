package pl.agh.dp.loadbalancer.client;

import lombok.SneakyThrows;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;

import javax.persistence.Table;
import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class QueryInterceptor<Entity> extends EmptyInterceptor {

    Socket socket;
    PrintWriter writer;

    QueryInterceptor(Socket socket, PrintWriter writer) {

        this.socket = socket;
        this.writer = writer;
    }

    @Override
    public String onPrepareStatement(String sql) {

        String[] splitedRequest = sql.split(" ");
        if(Objects.equals(splitedRequest[0], "select")){
            System.out.println(sql);
            writer.println(sql);
        }

        return null;
    }

    @SneakyThrows
    @Override
    public boolean onSave(Object entity, Serializable id,
                          Object[] state, String[] propertyNames, Type[] types) {

        String query = "INSERT INTO ";
        query += entity.getClass().getAnnotation(Table.class).name();
        query += " (";
        query += String.join(", ", propertyNames);
        query += ") VALUES (";
        for(int i = 0; i < state.length-1; i++){
            query += "'" + state[i].toString() + "', ";
        }
        query += "'" + state[state.length-1].toString() + "'";
        query += ")";
        System.out.println(query);

        writer.println(query);

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
                System.out.println("fuck that");
            }else{
                query += propertyNames[i] + "='" + state[i].toString() + "' AND ";
            }
        }
        query += propertyNames[state.length-1] + "='" + state[state.length-1].toString() + "'";
        System.out.println(query);

        writer.println(query);
    }

    @SneakyThrows
    @Override
    public boolean onLoad(Object entity, Serializable id,
                          Object[] state, String[] propertyNames, Type[] types) {

//        String query = "INSERT INTO ";
//        query += entity.getClass().getAnnotation(Table.class).name();
//        query += " (";
//        query += String.join(", ", propertyNames);
//        query += ") VALUES (";
//        for(int i = 0; i < state.length-1; i++){
//            query += "'" + state[i].toString() + "', ";
//        }
//        query += "'" + state[state.length-1].toString() + "'";
//        query += ")";
//        System.out.println(query);
//
//        writer.println(query);

        //System.out.println("ohuj");
        return false;
        //return super.onLoad(entity, id,state, propertyNames, types);
    }

}
