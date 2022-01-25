package pl.agh.dp.loadbalancer.DataBasesInterface;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.command.Command;

import java.util.List;

@Component("DataBasesInterface")
public class DatabasesInterfaceImpl implements DatabasesInterface{

    @Autowired
    private DataBaseConnectionConfig firstConfig;

    List<DataBaseInstance> databases;


    public void printConf()
    {
        this.firstConfig.getConfiguration();
    }

    @Override
    public List<DataBaseInstance> getDatabases() {
        return databases;
    }


    public void executeCUD(Command command){
    }

    public void executeSelect(Command command){

    }

}
