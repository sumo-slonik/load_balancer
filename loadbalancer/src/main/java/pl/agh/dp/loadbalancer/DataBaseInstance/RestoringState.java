package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.service.spi.ServiceException;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.QueryType;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class RestoringState implements DataBaseState{

    private final DataBaseInstance dataBaseInstance;

    @PostConstruct
    @Override
    public void notifyQueryProcessor(){
        this.dataBaseInstance.notifyQueryProcessor();
    }

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
        boolean result = true;
        try
        {
            dataBaseInstance.getConfiguration().buildSessionFactory().openSession();
        }catch (ServiceException | IllegalStateException ex)
        {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }

    @Override
    public void queryProcessorHandle() {

        Command command = this.dataBaseInstance.getQueryProcesor().getCommand();

        if(! command.getQueryType().equals(QueryType.SELECT)) {

            Session databaseSession = this.dataBaseInstance.getSession();

            Query resultQuery;

            try {
                resultQuery = databaseSession.createQuery(command.getCommand());
            } catch (HibernateException exception) {
                System.out.println(exception.toString());
            }

        }

    }
}
