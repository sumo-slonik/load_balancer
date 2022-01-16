package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.List;

public class RoundRobinStrategy implements BalanceStrategy{

    int lastDatabaseIndex = 0;

    public DataBaseInstance chooseDatabase(List<DataBaseInstance> databases) {
        if(lastDatabaseIndex >= databases.size())
            lastDatabaseIndex = -1;

        return databases.get(++lastDatabaseIndex);
    }
}
