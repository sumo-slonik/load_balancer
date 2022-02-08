package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.Connection.DataBaseConfiguration;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.DataBasesInterface.ConnectionChecker;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.SelectCommand;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBaseNumber;
import org.hibernate.service.spi.ServiceException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DataBaseInstanceImpl implements DataBaseInstance {

    @Getter
    private final DataBaseNumber dataBaseNumber;
    @Getter
    private final DataBaseConnectionConfig dataBaseConnectionConfig;

    @Getter
    @Setter
    private DatabasesInterface databasesInterface;


    private QueryProcessor<Command> queryProcessor;
    private Thread queryProcessorThread;

    public QueryProcessor<Command> getQueryProcessor() {
        return queryProcessor;
    }

    @Setter
    private DataBaseState state = new DisconnectedState(this);

    @Getter
    private Session session;

    public DataBaseInstanceImpl(DataBaseNumber dataBaseNumber, DataBaseConnectionConfig dataBaseConfiguration){
        this.dataBaseNumber = dataBaseNumber;
        this.dataBaseConnectionConfig = dataBaseConfiguration;

        System.out.println("init query processor in databaseInstanceImpl");
        this.queryProcessor = new QueryProcessor<>(this);
        this.queryProcessorThread = new Thread(this.queryProcessor);
        this.queryProcessorThread.start();
    }

    @PreDestroy
    public void finishQueryProcessor(){
        this.queryProcessor.setEnd(true);
        try {
            this.queryProcessorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendQuery(String query) {
        return false;
    }

    @Override
    public DataBaseStates getState() {
        return this.state.getState();
    }

    @Override
    public Double getLoad() {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return dataBaseConnectionConfig.getConfiguration();
    }

    @Override
    public void createSession() {
            this.session = dataBaseConnectionConfig.getConfiguration().buildSessionFactory().openSession();
    }

    @Override
    public void loseConnection() {
        this.state.loseConnection(this);
    }

    @Override
    public void establishConnection() {
        this.state.establishConnection(this);

    }

    @Override
    public void setState(DataBaseState dataBaseState) {
        this.state = dataBaseState;
    }

    private SessionFactory getSessionFactory() {
        return getConfiguration().buildSessionFactory();
    }


    @Override
    public void processQuery(String select) {

//        NativeQuery result = this.dataBaseSession.createSQLQuery(select);
//        List clubs = result.list();
//        for(Object c : clubs)
//        {
//            Club club = new Club((Object[]) c);
//            System.out.println(club.toString());
//        }
    }

    public void checkConnection() {

        if (this.state.isConnected()) {
            establishConnection();
        } else {
            loseConnection();
        }
    }

    @Override
    public QueryProcessor<Command> getQueryProcesor() {
        return this.queryProcessor;
    }

    public void notifyQueryProcessor(){
        this.queryProcessor.notify();
    }

    @Override
    public DataBaseState getStateObject() {
        return this.state;
    }

    @Override
    public Boolean hasEmptyQueue()
    {
        return queryProcessor.hasEmptyQueue();
    }

    @Override
    public void addCommandToQueue(Command command) {
        queryProcessor.addCommandToQueue(command);
    }

    @Override
    public String getDescription()
    {
        return this.dataBaseConnectionConfig.getConnectionUrl()+" "+this.state+" "+this.dataBaseNumber;
    }

    @Override
    public void throwbackSelectCommand(SelectCommand command){

        this.databasesInterface.executeSelect(command);
    }
}


