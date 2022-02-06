package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.service.spi.ServiceException;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.command.Command;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class DisconnectedState implements DataBaseState{

    private final DataBaseInstance dataBaseInstance;

    @PostConstruct
    @Override
    public void notifyQueryProcessor(){
    }

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
        databaseInstance.setState(new RestoringState(databaseInstance));
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
        }catch (ServiceException | IllegalStateException ex)
        {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }

    @Override
    public synchronized void queryProcessorHandle() {
        try {
            System.out.println("zaczyna wait w disconnected state");
            this.dataBaseInstance.getQueryProcesor().wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
