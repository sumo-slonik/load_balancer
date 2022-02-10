package pl.agh.dp.loadbalancer.DataBaseInstance;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.Session;


import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.UpdateCommand;

public abstract class DataBaseState {
    // method that adds commands to the queue
    abstract void addCommandToQueue();
    // method that processes commands on the queue
    abstract void processCommandFromQueue();
    abstract void loseConnection(DataBaseInstance databaseInstance);
    abstract void establishConnection(DataBaseInstance databaseInstance);
    abstract DataBaseStates getState();
    abstract boolean isConnected();

    public abstract void queryProcessorHandle();

    abstract void notifyQueryProcessor();


}
