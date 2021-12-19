package pl.agh.dp.loadbalancer.DataBasesInterface;


import pl.agh.dp.loadbalancer.DataBaseInstance.DatabaseInstance;

import java.util.List;

public interface DatabasesInterface {

    List<DatabaseInstance> getDatabases();

}
