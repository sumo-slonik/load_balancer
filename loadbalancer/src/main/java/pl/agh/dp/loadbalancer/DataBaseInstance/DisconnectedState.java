package pl.agh.dp.loadbalancer.DataBaseInstance;

public class DisconnectedState implements DataBaseState{
    @Override
    public void addCommandToQueue() {

    }

    @Override
    public void processCommandFromQueue() {

    }

    @Override
    public void loseConnection(DatabaseInstance databaseInstance) {
// nothing
    }

    @Override
    public void getConnection(DatabaseInstance databaseInstance) {
        databaseInstance.setState(new RestoringState());
    }

    @Override
    public DataBaseStates getState() {
        return DataBaseStates.DISCONNECTED;
    }
}
