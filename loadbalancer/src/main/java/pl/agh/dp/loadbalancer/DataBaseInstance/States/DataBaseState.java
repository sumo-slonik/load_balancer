package pl.agh.dp.loadbalancer.DataBaseInstance.States;


import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

public abstract class DataBaseState {
    // method that adds commands to the queue
    public abstract void addCommandToQueue();
    // method that processes commands on the queue
    public abstract void processCommandFromQueue();
    public  abstract void loseConnection(DataBaseInstance databaseInstance);
    public abstract void establishConnection(DataBaseInstance databaseInstance);
    public abstract DataBaseStates getState();
    public abstract boolean isConnected();
    public abstract void queryProcessorHandle();
    public abstract void notifyQueryProcessor();


}
