package pl.agh.dp.loadbalancer.DataBaseInstance;

public class ConnectedState implements DataBaseState{
    @Override
    public void addCommandToQueue() {

    }

    @Override
    public void processCommandFromQueue() {

    }

    @Override
    public void loseConnection(DatabaseInstance databaseInstance) {
        databaseInstance.setState(new DisconnectedState());
    }

    @Override
    public void getConnection(DatabaseInstance databaseInstance) {
// nothing
    }

    @Override
    public DataBaseStates getState() {
        return DataBaseStates.CONNECTED;
    }
}
