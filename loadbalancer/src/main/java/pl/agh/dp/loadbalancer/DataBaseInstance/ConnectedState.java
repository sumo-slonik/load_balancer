package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.jdbc.object.SqlQuery;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.command.Command;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConnectedState implements DataBaseState {


    private final DataBaseInstance dataBaseInstance;

    @PostConstruct
    @Override
    public void notifyQueryProcessor() {
        this.dataBaseInstance.notifyQueryProcessor();
    }

    @Override
    public boolean isConnected() {
        return this.dataBaseInstance.getSession().isConnected();
    }

    @Override
    public void addCommandToQueue() {

    }

    @Override
    public void processCommandFromQueue() {

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

            System.out.println("before get connected state");
            command = this.dataBaseInstance.getQueryProcesor().getCommand();
        }
        synchronized (command) {
            System.out.println("after get " + command.getCommand());

            Session databaseSession = this.dataBaseInstance.getSession();

            Query resultQuery = null;

            try{
                resultQuery = databaseSession.createQuery(command.getCommand());
            } catch (HibernateException exception){
                System.out.println(exception.toString());
            }


            if (resultQuery == null) {
                command.setResult("null");
            } else {
                String res = (String) resultQuery.list().stream().map(club -> club.toString()).collect(Collectors.joining("\n"));




                command.setResult(res);
            }

            command.notify();

        }

    }

}
