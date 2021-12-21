package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DatabaseInstance;

import java.util.List;

public interface BalanceStrategy {

    DatabaseInstance chooseDatabase(List<DatabaseInstance> databases);
}
