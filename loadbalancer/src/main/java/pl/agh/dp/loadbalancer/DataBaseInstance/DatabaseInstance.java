package pl.agh.dp.loadbalancer.DataBaseInstance;

import org.hibernate.Session;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import org.hibernate.cfg.Configuration;

public interface DatabaseInstance {
    boolean sendQuery(String query);
    DataBaseStates getState();
    Double getLoad();
    Configuration getConfiguration();
    Session getSession();
    void loseConnection();
    void getConnection();
}
