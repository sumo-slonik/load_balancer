package pl.agh.dp.loadbalancer.DataBasesInterface;


import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.List;

public class DatabasesInterfaceImpl implements DatabasesInterface{


    List<DataBaseInstance> databases;


    @Override
    public List<DataBaseInstance> getDatabases() {
        return databases;
    }
}
