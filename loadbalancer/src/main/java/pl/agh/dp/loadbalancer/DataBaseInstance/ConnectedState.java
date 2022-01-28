package pl.agh.dp.loadbalancer.DataBaseInstance;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.command.Command;

import javax.annotation.PostConstruct;

public class ConnectedState implements DataBaseState{


    DataBaseInstance dataBaseInstance;

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
        Command command = this.dataBaseInstance.getQueryProcesor().getCommand();

        Session databaseSession =  this.dataBaseInstance.getSession();

        Query resultQuery;

        try{
            resultQuery = databaseSession.createQuery(command.getCommand());
        } catch (HibernateException exception){
            System.out.println(exception.toString());
        }

    }

}
