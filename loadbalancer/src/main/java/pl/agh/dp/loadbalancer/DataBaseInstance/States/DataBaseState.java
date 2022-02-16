package pl.agh.dp.loadbalancer.DataBaseInstance.States;


import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

public abstract class DataBaseState {

    public abstract void loseConnection(DataBaseInstance databaseInstance);
    public abstract void establishConnection(DataBaseInstance databaseInstance);
    public abstract DataBaseStates getState();
    public abstract boolean isConnected();
    public abstract void queryProcessorHandle();
//    public abstract void notifyQueryProcessor();


}
