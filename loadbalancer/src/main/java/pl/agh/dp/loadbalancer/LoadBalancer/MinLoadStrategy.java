package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DatabaseInstance;

import java.util.List;

public class MinLoadStrategy implements BalanceStrategy{

    public DatabaseInstance chooseDatabase(List<DatabaseInstance> databases) {

        DatabaseInstance minLoadDatabase = null;
        double minLoad = Double.POSITIVE_INFINITY;

        for(DatabaseInstance database : databases){
            if(database.getLoad() < minLoad){
                minLoad = database.getLoad();
                minLoadDatabase = database;
            }
        }

        return minLoadDatabase;
    }
}
