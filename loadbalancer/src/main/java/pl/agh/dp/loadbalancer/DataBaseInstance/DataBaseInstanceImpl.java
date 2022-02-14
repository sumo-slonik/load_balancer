package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.bytecode.Throw;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.DataBaseInstance.States.DataBaseState;
import pl.agh.dp.loadbalancer.DataBaseInstance.States.DataBaseStates;
import pl.agh.dp.loadbalancer.DataBaseInstance.States.DisconnectedState;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.SelectCommand;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBaseNumber;

import javax.annotation.PreDestroy;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class DataBaseInstanceImpl implements DataBaseInstance {

    @Getter
    private final DataBaseNumber dataBaseNumber;
    @Getter
    private final DataBaseConnectionConfig dataBaseConnectionConfig;

    @Getter
    @Setter
    private DatabasesInterface databasesInterface;

    @Getter
    private long latency = 0;

    private QueryProcessor<Command> queryProcessor;
    private Thread queryProcessorThread;

    public QueryProcessor<Command> getQueryProcessor() {
        return queryProcessor;
    }

    @Setter
    private DataBaseState state = new DisconnectedState(this);

    @Getter
    private Session session;

    public DataBaseInstanceImpl(DataBaseNumber dataBaseNumber, DataBaseConnectionConfig dataBaseConfiguration) {
        this.dataBaseNumber = dataBaseNumber;
        this.dataBaseConnectionConfig = dataBaseConfiguration;

        System.out.println("init query processor in databaseInstanceImpl");
        this.queryProcessor = new QueryProcessor<>(this);
        this.queryProcessorThread = new Thread(this.queryProcessor);
        this.queryProcessorThread.start();
    }

    @PreDestroy
    public void finishQueryProcessor() {
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
        this.session = null;
        Boolean succcess = true;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(() -> {
            this.session = dataBaseConnectionConfig.getConfiguration().buildSessionFactory().openSession();
        });
        try {
            future.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            succcess=false;
        } catch (Exception e) {
            succcess=false;
        }
        finally {
            executor.shutdownNow();
        }
        if(!succcess)
        {
            throw new HibernateException("creating seassion takes too long");
        }
    }

    @Override
    public void ping() {
        this.session = null;
        Boolean succcess = true;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(() -> {
            dataBaseConnectionConfig.getConfiguration().buildSessionFactory().openSession();
        });
        try {
            future.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            succcess=false;
        } catch (Exception e) {
            succcess=false;
        }
        finally {
            executor.shutdownNow();
        }
        if(!succcess)
        {
            throw new HibernateException("creating seassion takes too long");
        }
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

    public void notifyQueryProcessor() {

        synchronized (this.queryProcessor) {
            this.queryProcessor.notify();
        }
    }

    @Override
    public DataBaseState getStateObject() {
        return this.state;
    }

    @Override
    public Boolean hasEmptyQueue() {
        return queryProcessor.hasEmptyQueue();
    }

    @Override
    public void addCommandToQueue(Command command) {
        queryProcessor.addCommandToQueue(command);
    }

    @Override
    public String getDescription() {
        return "Data Base " + this.dataBaseNumber +
                ": url " + this.dataBaseConnectionConfig.getConnectionUrl() +
                " latency " + this.latency +
                " " + this.getState().toString();
    }

    @Override
    public void throwbackSelectCommand(SelectCommand command) {

        this.databasesInterface.executeSelect(command);
    }

    @Override
    public void updateLatency() {

        if (state.getState().equals(DataBaseStates.CONNECTED)) {
            String querry = String.format("SELECT total_latency FROM sys.x$schema_table_statistics; where table_schema = '%s'", getSchemaName());
            BigInteger a = (BigInteger) this.session.createSQLQuery(String.format("SELECT total_latency FROM sys.x$schema_table_statistics where table_schema = '%s'", getSchemaName())).list().stream().findFirst().orElse(new BigInteger("0"));
            this.latency = a.longValue();
        }

    }

    private String getSchemaName() {
        String[] res = this.dataBaseConnectionConfig.getConnectionUrl().split("/");
        return res[res.length - 1];
    }

    @Override
    public int compareTo(DataBaseInstance instance) {
        return Long.compare(this.latency, instance.getLatency());
    }
}


