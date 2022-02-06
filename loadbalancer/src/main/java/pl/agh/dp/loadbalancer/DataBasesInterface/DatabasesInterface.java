package pl.agh.dp.loadbalancer.DataBasesInterface;


import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.InsertCommand;
import pl.agh.dp.loadbalancer.command.SelectCommand;

import java.util.List;

public interface DatabasesInterface {

    List<DataBaseInstance> getDatabases();

    String executeCUD(Command command);


    void executeSelect(SelectCommand command);

    List<Club> executeSelect(String command);


}
