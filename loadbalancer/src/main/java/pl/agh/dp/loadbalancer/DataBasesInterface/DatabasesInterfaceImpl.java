package pl.agh.dp.loadbalancer.DataBasesInterface;


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
