package pl.agh.dp.loadbalancer.LoadBalancer;

import org.springframework.context.annotation.Bean;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.List;


public class RoundRobinStrategy implements BalanceStrategy{

    int lastDatabaseIndex = 0;

    public DataBaseInstance chooseDatabase(List<DataBaseInstance> databases) {
        lastDatabaseIndex+=1;
        lastDatabaseIndex %= databases.size();
        System.out.println(""+databases.get(lastDatabaseIndex).getDescription());
        return databases.get(lastDatabaseIndex);
    }
}
