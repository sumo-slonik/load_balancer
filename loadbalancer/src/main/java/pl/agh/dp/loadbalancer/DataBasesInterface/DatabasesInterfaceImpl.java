package pl.agh.dp.loadbalancer.DataBasesInterface;


import pl.agh.dp.loadbalancer.DataBaseInstance.DatabaseInstance;
import pl.agh.dp.loadbalancer.command.Command;

import java.util.List;

public class DatabasesInterfaceImpl implements DatabasesInterface{


    List<DatabaseInstance> databases;


    @Override
    public List<DatabaseInstance> getDatabases() {
        return databases;
    }


    public void executeCUD(Command command){
    }

    public void executeSelect(Command command){

    }

}
