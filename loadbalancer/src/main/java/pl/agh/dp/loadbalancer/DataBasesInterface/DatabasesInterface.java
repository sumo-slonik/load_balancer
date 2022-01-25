package pl.agh.dp.loadbalancer.DataBasesInterface;


import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.InsertCommand;

import java.util.List;

public interface DatabasesInterface {

    List<DataBaseInstance> getDatabases();

    public void executeCUD(Command command);

    public void executeSelect(Command command);

}
