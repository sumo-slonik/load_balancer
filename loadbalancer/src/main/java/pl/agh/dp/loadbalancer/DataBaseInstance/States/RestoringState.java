package pl.agh.dp.loadbalancer.DataBaseInstance.States;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.service.spi.ServiceException;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.QueryType;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestoringState extends DataBaseState {

    private final DataBaseInstance dataBaseInstance;

    public RestoringState(DataBaseInstance dataBaseInstance){
        this.dataBaseInstance = dataBaseInstance;
        System.out.println("tutaj byłem");
    }

    @Override
    public void loseConnection(DataBaseInstance databaseInstance) {
        databaseInstance.setState(new DisconnectedState(dataBaseInstance));
    }

    @Override
    public void establishConnection(DataBaseInstance databaseInstance) {
//        databaseInstance.setState(new ConnectedState(dataBaseInstance));
    }

    @Override
    public DataBaseStates getState() {
        return DataBaseStates.RESTORING;
    }

    @Override
    public boolean isConnected() {
        boolean result = true;
        try {
            System.out.print("tworzenie sesji: ");
            dataBaseInstance.createSession();
            System.out.println(dataBaseInstance.getSession());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }

    @Override
    public void queryProcessorHandle() {

        System.out.println("restoringState queryProcessorHandle");

        synchronized (this.dataBaseInstance.getQueryProcesor()) {

            if (dataBaseInstance.hasEmptyQueue()) {
                dataBaseInstance.createSession();
                dataBaseInstance.setState(new ConnectedState(dataBaseInstance));
            } else {

                Command command = this.dataBaseInstance.getQueryProcesor().getCommand();
                if(command == null){ // interruptException happened or get time took too long
                    return;
                }
                if (!command.getQueryType().equals(QueryType.SELECT)) {

                    Session databaseSession = this.dataBaseInstance.getSession();

                    Query resultQuery = null;

                    try {
                        resultQuery = databaseSession.createSQLQuery(command.getCommand());
                        resultQuery.setCacheable(false);
                        int transactionResult = command.handleQueryParameters(resultQuery, databaseSession);
                    } catch (HibernateException exception) {
                        System.out.println(exception.toString());
                    }


                } else {
                    command.setResult("select failed");
                    command.notify();
                }

            }

        }

    }
}


