package pl.agh.dp.loadbalancer.DataBaseInstance.States;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.QueryType;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

public class ConnectedState extends DataBaseState {


    private final DataBaseInstance dataBaseInstance;

    public ConnectedState(DataBaseInstance dataBaseInstance){
        this.dataBaseInstance = dataBaseInstance;
        this.dataBaseInstance.notifyQueryProcessor();
    }

//    @PostConstruct
//    @Override
//    public void notifyQueryProcessor() {
//        this.dataBaseInstance.notifyQueryProcessor();
//    }

    @Override
    public boolean isConnected() {
        return this.dataBaseInstance.getSession().isConnected();
    }

    @Override
    public void addCommandToQueue() {

    }

    @Override
    public void processCommandFromQueue() {

    }

    @Override
    public void loseConnection(DataBaseInstance databaseInstance) {
        databaseInstance.setState(new DisconnectedState(this.dataBaseInstance));
    }

    @Override
    public void establishConnection(DataBaseInstance databaseInstance) {
// nothing
    }

    @Override
    public DataBaseStates getState() {
        return DataBaseStates.CONNECTED;
    }

    @Override
    public void queryProcessorHandle() {
        Command command;
        synchronized (this.dataBaseInstance.getQueryProcesor()) {

            System.out.println("before get connected state");
            command = this.dataBaseInstance.getQueryProcesor().getCommand();
        }
        synchronized (command) {
            System.out.println("after get " + command.getCommand());

            Session databaseSession = this.dataBaseInstance.getSession();

            Query resultQuery = null;
            int transactionResult;


//            if(command.queryType.equals(QueryType.))

            try{
                resultQuery = databaseSession.createQuery(command.getCommand());


                transactionResult = command.handleQueryParameters(resultQuery, databaseSession);

            } catch (HibernateException exception){
                System.out.println(exception.toString());
            }


//            if (resultQuery == null) {
//                command.setResult("null");
//            } else {
//                String res = (String) resultQuery.list().stream().map(club -> club.toString()).collect(Collectors.joining("\n"));
//
//                command.setResult(res);
//            }

            if(command.queryType.equals(QueryType.SELECT)){
                String res = (String) resultQuery.list().stream().map(club -> club.toString()).collect(Collectors.joining("\n"));

                command.setResult(res);
            }
            else{
                command.setResult("");
            }

            command.notify();

        }

    }

}
