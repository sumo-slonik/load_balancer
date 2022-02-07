package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.List;

public class RoundRobinStrategy implements BalanceStrategy{

    int lastDatabaseIndex = 0;

    public DataBaseInstance chooseDatabase(List<DataBaseInstance> databases) {
        if(lastDatabaseIndex >= databases.size())
            lastDatabaseIndex = -1;
        System.out.println(databases.get(1+lastDatabaseIndex).getState());
        return databases.get(++lastDatabaseIndex);
    }
}
