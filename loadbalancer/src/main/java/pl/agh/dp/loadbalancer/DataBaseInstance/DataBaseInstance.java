package pl.agh.dp.loadbalancer.DataBaseInstance;

import org.hibernate.Session;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import org.hibernate.cfg.Configuration;

public interface DataBaseInstance {
    boolean sendQuery(String query);

    DataBaseStates getState();

    Double getLoad();

    Configuration getConfiguration();

    Session getSession();

    void loseConnection();

    void establishConnection();

    void setState(DataBaseState dataBaseState);

    void createSession();
    void checkConnection();

    DataBaseConnectionConfig getDataBaseConnectionConfig();

    //  tutaj walniesz zamiast selecta swojego comanda
    void processQuery(String query);
}
