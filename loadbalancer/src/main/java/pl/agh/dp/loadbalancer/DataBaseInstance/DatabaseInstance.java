package pl.agh.dp.loadbalancer.DataBaseInstance;

import pl.agh.dp.loadbalancer.DataBasesInterface.DatabaseState;

public interface DatabaseInstance {
    public void sendQuery();
    public DatabaseState getState();
    public void actualiseState();
}
