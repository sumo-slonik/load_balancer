package pl.agh.dp.loadbalancer.LoadBalancer;

import org.springframework.beans.factory.annotation.Autowired;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBaseInstance.States.DataBaseStates;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

public class LoadBalancerImpl implements LoadBalancerInterface {

    private DatabasesInterface dbInterface;
    // Here we only store databases that are UP
    private List<DataBaseInstance> databases;
    private BalanceStrategy balancer;

    @Autowired
    private BalanceStrategy roundRobin;

    @Autowired
    private BalanceStrategy minLoad;

    @PostConstruct
    public void init() {
        setBalanceStrategy(false);
    }


    public void setBalanceStrategy(boolean useMinLoadBalance) {
        if (useMinLoadBalance)
            balancer = minLoad;
        else
            balancer = roundRobin;
    }

    private void updateDatabaseList() {
        databases = dbInterface.getDatabases().stream().filter(base -> base.getState().equals(DataBaseStates.CONNECTED)).collect(Collectors.toList());
    }

    public DataBaseInstance chooseDatabase() {
        updateDatabaseList();
        return balancer.chooseDatabase(databases);
    }

    public void newQuery(String query) {

        DataBaseInstance database;
        do {
            updateDatabaseList();
            database = balancer.chooseDatabase(databases);
        } while (!sendQuery(query, database));

    }

    private boolean sendQuery(String query, DataBaseInstance database) {
        return database.sendQuery(query);
    }

    public void setDbInterface(DatabasesInterface dbInterface) {
        this.dbInterface = dbInterface;
        databases = dbInterface.getDatabases();
        updateDatabaseList();
    }


}
