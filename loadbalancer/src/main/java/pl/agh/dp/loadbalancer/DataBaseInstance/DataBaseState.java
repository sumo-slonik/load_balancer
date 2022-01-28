package pl.agh.dp.loadbalancer.DataBaseInstance;

import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.command.Command;

public interface DataBaseState {
    // method that adds commands to the queue
    void addCommandToQueue();
    // method that processes commands on the queue
    void processCommandFromQueue();
    void loseConnection(DataBaseInstance databaseInstance);
    void establishConnection(DataBaseInstance databaseInstance);
    DataBaseStates getState();
    boolean isConnected();

    void queryProcessorHandle();

    void notifyQueryProcessor();

}
