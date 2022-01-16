package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.List;

public class MinLoadStrategy implements BalanceStrategy{

    public DataBaseInstance chooseDatabase(List<DataBaseInstance> databases) {

        DataBaseInstance minLoadDatabase = null;
        double minLoad = Double.POSITIVE_INFINITY;

        for(DataBaseInstance database : databases){
            if(database.getLoad() < minLoad){
                minLoad = database.getLoad();
                minLoadDatabase = database;
            }
        }

        return minLoadDatabase;
    }
}
