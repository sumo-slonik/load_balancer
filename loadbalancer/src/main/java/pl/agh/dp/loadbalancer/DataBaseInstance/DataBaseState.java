package pl.agh.dp.loadbalancer.DataBaseInstance;

public interface DataBaseState {
    // method that adds commands to the queue
    void addCommandToQueue();
    // method that processes commands on the queue
    void processCommandFromQueue();
    void loseConnection(DataBaseInstance databaseInstance);
    void establishConnection(DataBaseInstance databaseInstance);
    DataBaseStates getState();
    boolean isConnected();
}
