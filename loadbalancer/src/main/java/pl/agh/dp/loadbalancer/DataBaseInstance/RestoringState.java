package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestoringState implements DataBaseState{

    private final DataBaseInstance dataBaseInstance;


    @Override
    public void addCommandToQueue() {

    }

    @Override
    public void processCommandFromQueue() {

    }

    @Override
    public void loseConnection(DataBaseInstance databaseInstance) {
        databaseInstance.setState(new DisconnectedState(dataBaseInstance));
    }

    @Override
    public void establishConnection(DataBaseInstance databaseInstance) {
//        nothing
    }

    @Override
    public DataBaseStates getState() {
        return DataBaseStates.RESTORING;
    }

    @Override
    public boolean isConnected() {
        return this.dataBaseInstance.getSession().isConnected();
    }
}
