package pl.agh.dp.loadbalancer.DataBaseInstance;

public interface DatabaseInstance {
    boolean sendQuery(String query);
    DataBaseStates getState();
    Double getLoad();
}
