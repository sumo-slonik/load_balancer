package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseStates;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;

import java.util.List;

public class LoadBalancerImpl implements LoadBalancerInterface{

    private DatabasesInterface dbInterface;
    // Here we only store databases that are UP
    private List<DataBaseInstance> databases;
    private BalanceStrategy balancer;

    public LoadBalancerImpl(DatabasesInterface dbInterface){
        this.dbInterface = dbInterface;
        databases = dbInterface.getDatabases();
        updateDatabaseList();
        setBalanceStrategy(false);
    }

    public void setBalanceStrategy(boolean useMinLoadBalance) {
        if(useMinLoadBalance)
            balancer = new MinLoadStrategy(dbInterface);
        else
            balancer = new RoundRobinStrategy();
    }

    private void updateDatabaseList(){
        List<DataBaseInstance> currentDatabases = dbInterface.getDatabases();
        for( DataBaseInstance database : currentDatabases){
            if(databases.contains(database) && (database.getState() == DataBaseStates.DISCONNECTED || database.getState() == DataBaseStates.RESTORING))
                databases.remove(database);
            else if(!databases.contains(database) && database.getState() == DataBaseStates.CONNECTED)
                databases.add(database);
        }
    }

    public DataBaseInstance chooseDatabase(){
        updateDatabaseList();
        return balancer.chooseDatabase(databases);
    }

    public void newQuery(String query) {

        DataBaseInstance database;
        do{
            updateDatabaseList();
            database = balancer.chooseDatabase(databases);
        }while(!sendQuery(query, database));

    }

    private boolean sendQuery(String query, DataBaseInstance database){
        return database.sendQuery(query);
    }

}
