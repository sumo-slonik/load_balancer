package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

public interface LoadBalancerInterface {

    DataBaseInstance chooseDatabase();
    void newQuery(String query);
    void setBalanceStrategy(boolean useMinLoadBalance);
}
