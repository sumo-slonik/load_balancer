package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;

@RequiredArgsConstructor
public class DisconnectedState implements DataBaseState{

    private final DataBaseInstance dataBaseInstance;

    @Override
    public void addCommandToQueue() {

    }

    @Override
    public void processCommandFromQueue() {

    }

    @Override
    public void loseConnection(DataBaseInstance databaseInstance) {
// nothing
    }

    @Override
    public void establishConnection(DataBaseInstance databaseInstance) {
        databaseInstance.setState(new RestoringState());
    }

    @Override
    public DataBaseStates getState() {
        return DataBaseStates.DISCONNECTED;
    }

    @Override
    public boolean isConnected() {
        boolean result = true;
        try
        {
            dataBaseInstance.createSession();
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }
}
