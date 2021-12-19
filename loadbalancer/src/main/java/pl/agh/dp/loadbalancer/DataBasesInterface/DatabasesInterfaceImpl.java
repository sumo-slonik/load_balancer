package pl.agh.dp.loadbalancer.DataBasesInterface;


import pl.agh.dp.loadbalancer.DataBaseInstance.DatabaseInstance;

import java.util.List;

public class DatabasesInterfaceImpl implements DatabasesInterface{


    List<DatabaseInstance> databases;


    @Override
    public List<DatabaseInstance> getDatabases() {
        return databases;
    }
}
