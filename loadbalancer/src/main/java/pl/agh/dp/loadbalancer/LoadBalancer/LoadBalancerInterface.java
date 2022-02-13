package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;

public interface LoadBalancerInterface {

    DataBaseInstance chooseDatabase();

    void newQuery(String query);

    void setBalanceStrategy(boolean useMinLoadBalance);

    void setDbInterface(DatabasesInterface dbInterface);

}
