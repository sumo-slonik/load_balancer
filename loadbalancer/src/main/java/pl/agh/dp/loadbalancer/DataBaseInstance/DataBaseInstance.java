package pl.agh.dp.loadbalancer.DataBaseInstance;

import org.hibernate.Session;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import org.hibernate.cfg.Configuration;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.SelectCommand;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBaseNumber;

public interface DataBaseInstance {
    boolean sendQuery(String query);

    DataBaseStates getState();

    DataBaseNumber getDataBaseNumber();

    Double getLoad();

    Configuration getConfiguration();

    Session getSession();

    void loseConnection();

    void establishConnection();

    void setState(DataBaseState dataBaseState);

    void createSession();

    void checkConnection();

    QueryProcessor<Command> getQueryProcesor();

    DataBaseConnectionConfig getDataBaseConnectionConfig();

    //  tutaj walniesz zamiast selecta swojego comanda
    void processQuery(String query);

    void notifyQueryProcessor();

    DataBaseState getStateObject();

    Boolean hasEmptyQueue();

    void addCommandToQueue(Command command);

    String getDescription();

    void throwbackSelectCommand(SelectCommand command);

    void setDatabasesInterface(DatabasesInterface databasesInterface);

    DatabasesInterface getDatabasesInterface();

    void updateLatency();

    long getLatency();

    int compareTo(DataBaseInstance instance);

}
