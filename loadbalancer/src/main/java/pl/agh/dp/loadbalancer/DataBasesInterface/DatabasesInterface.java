package pl.agh.dp.loadbalancer.DataBasesInterface;


import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.List;

public interface DatabasesInterface {

    List<DataBaseInstance> getDatabases();

}
