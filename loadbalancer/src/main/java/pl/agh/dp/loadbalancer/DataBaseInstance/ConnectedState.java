package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.command.Command;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class ConnectedState implements DataBaseState{


    private final DataBaseInstance dataBaseInstance;

    @PostConstruct
    @Override
    public void notifyQueryProcessor(){
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
        System.out.println("before get connected state");
        Command command = this.dataBaseInstance.getQueryProcesor().getCommand();
        System.out.println("after get " + command.getCommand());

        Session databaseSession =  this.dataBaseInstance.getSession();

        Query resultQuery = null;

        try{
            resultQuery = databaseSession.createQuery(command.getCommand());
        } catch (HibernateException exception){
            System.out.println(exception.toString());
        }

        if(resultQuery == null){
            command.setResult("null");
        } else {
            command.setResult(resultQuery.getQueryString());
        }

        command.notify();

    }

}
