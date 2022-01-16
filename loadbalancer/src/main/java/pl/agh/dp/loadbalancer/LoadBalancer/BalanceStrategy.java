package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.List;

public interface BalanceStrategy {

    DataBaseInstance chooseDatabase(List<DataBaseInstance> databases);
}
