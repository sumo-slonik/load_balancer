package pl.agh.dp.loadbalancer.DataBaseInstance.States;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.QueryType;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class ConnectedState extends DataBaseState {


    private final DataBaseInstance dataBaseInstance;

    public ConnectedState(DataBaseInstance dataBaseInstance){
        this.dataBaseInstance = dataBaseInstance;
        this.dataBaseInstance.notifyQueryProcessor();
    }

    @Override
    public boolean isConnected() {
        boolean result = true;
        try
        {
            dataBaseInstance.ping();
        }catch (Exception ex)
        {
            result = false;
        }
        return result;
    }

    @Override
    public void loseConnection(DataBaseInstance databaseInstance) {
        databaseInstance.setState(new DisconnectedState(this.dataBaseInstance));
    }

    @Override
    public void establishConnection(DataBaseInstance databaseInstance) {
// nothing
    }

    @Override
    public DataBaseStates getState() {
        return DataBaseStates.CONNECTED;
    }

    @Override
    public void queryProcessorHandle() {
        Command command;
        synchronized (this.dataBaseInstance.getQueryProcesor()) {
            command = this.dataBaseInstance.getQueryProcesor().getCommand();
            if(command == null){ // interruptException happened or get time took too long
                return;
            }
        }
        synchronized (command) {
            System.out.println("after get " + command.getCommand());

            Session databaseSession = this.dataBaseInstance.getSession();

            Query resultQuery = null;
            int transactionResult;

            try{
                {
                        resultQuery = databaseSession.createSQLQuery(command.getCommand());
                        resultQuery.setCacheable(false);
                        transactionResult = command.handleQueryParameters(resultQuery, databaseSession);
                }


            } catch (HibernateException exception){
                System.out.println(exception.toString());
            }


            if(command.queryType.equals(QueryType.SELECT)){
                databaseSession.clear();
                ObjectMapper mapper = new ObjectMapper();
                    String res = (String) resultQuery.list().stream().map(obj -> {
                        try {
                            return mapper.writeValueAsString(obj);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        return obj.toString();
                    }).collect(Collectors.joining("\n"));

                command.setResult(res);


            }
            else{
                command.setResult("");
            }

            command.notify();

        }

    }

}
