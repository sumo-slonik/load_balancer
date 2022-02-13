package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MinLoadStrategy implements BalanceStrategy{
    public DataBaseInstance chooseDatabase(List<DataBaseInstance> databases) {
        double minLoad = Double.POSITIVE_INFINITY;
        databases.sort(Comparator.comparingLong(DataBaseInstance::getLatency));
        System.out.println(databases.get(0).getDescription());
        return databases.get(0);
    }
}
