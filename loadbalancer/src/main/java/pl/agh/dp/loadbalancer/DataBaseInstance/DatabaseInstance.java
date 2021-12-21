package pl.agh.dp.loadbalancer.DataBaseInstance;

import pl.agh.dp.loadbalancer.DataBasesInterface.DatabaseState;

public interface DatabaseInstance {
    boolean sendQuery(String query);
    DatabaseState getState();
    void actualiseState();
    double getLoad();
}
