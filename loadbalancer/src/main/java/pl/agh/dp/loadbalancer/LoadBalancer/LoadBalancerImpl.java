package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DatabaseInstance;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabaseState;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;

import java.util.List;

public class LoadBalancerImpl implements LoadBalancerInterface{

    private DatabasesInterface dbInterface;
    // Here we only store database thaat are UP
    private List<DatabaseInstance> databases;
    private BalanceStrategy balancer;

    LoadBalancerImpl(DatabasesInterface dbInterface){
        this.dbInterface = dbInterface;
        databases = dbInterface.getDatabases();
        updateDatabaseList();
        setBalanceStrategy(false);
    }

    public void setBalanceStrategy(boolean useMinLoadBalance) {
        if(useMinLoadBalance)
            balancer = new MinLoadStrategy();
        else
            balancer = new RoundRobinStrategy();
    }

    private void updateDatabaseList(){
        List<DatabaseInstance> currentDatabases = dbInterface.getDatabases();
        for( DatabaseInstance database : currentDatabases){
            if(databases.contains(database) && (database.getState() == DatabaseState.DOWN || database.getState() == DatabaseState.REACTIVATING))
                databases.remove(database);
            else if(!databases.contains(database) && database.getState() == DatabaseState.UP)
                databases.add(database);
        }
    }

    public void newQuery(String query) {

        DatabaseInstance database;
        do{
            updateDatabaseList();
            database = balancer.chooseDatabase(databases);
        }while(!sendQuery(query, database));

    }

    private boolean sendQuery(String query, DatabaseInstance database){
        return database.sendQuery(query);
    }

}
