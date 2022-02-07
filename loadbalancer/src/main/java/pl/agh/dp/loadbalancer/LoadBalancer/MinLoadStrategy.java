package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;

import java.util.List;

public class MinLoadStrategy implements BalanceStrategy{

    LoadMeasurer loadMeasurer;

    MinLoadStrategy(DatabasesInterface dbInterface){
        loadMeasurer = new LoadMeasurer(dbInterface);
    }

    public DataBaseInstance chooseDatabase(List<DataBaseInstance> databases) {

        List<Double> loads = loadMeasurer.getLoads(databases);
        double minLoad = Double.POSITIVE_INFINITY;
        int minLoadDatabaseIndex = -1;

        for(int i = 0; i < databases.size(); i++){
            if(loads.get(i) < minLoad){
                minLoad = loads.get(i);
                minLoadDatabaseIndex = i;
            }
        }

        return databases.get(minLoadDatabaseIndex);
    }
}
