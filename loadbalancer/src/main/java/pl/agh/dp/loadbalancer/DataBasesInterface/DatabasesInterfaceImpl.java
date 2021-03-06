package pl.agh.dp.loadbalancer.DataBasesInterface;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBaseInstance.States.DataBaseStates;
import pl.agh.dp.loadbalancer.LoadBalancer.LoadBalancerInterface;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.SelectCommand;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabasesInterfaceImpl implements DatabasesInterface {

    @Autowired
    List<DataBaseInstance> databaseInstances;

    @Autowired
    private LoadBalancerInterface loadBalancer;


    @PostConstruct
    public void initPingers() {
        for (DataBaseInstance dataBaseInstance : databaseInstances) {
            new ConnectionChecker(dataBaseInstance);
            dataBaseInstance.setDatabasesInterface(this);
        }
        this.loadBalancer.setDbInterface(this);
    }


    public void printConf() {

    }

    @Override
    public List<DataBaseInstance> getDatabases() {
        return databaseInstances.stream().
                filter(dataBaseInstance -> dataBaseInstance.getState().equals(DataBaseStates.CONNECTED))
                .collect(Collectors.toList());
    }


    public String executeCUD(Command command) {
        databaseInstances.forEach(database -> database.addCommandToQueue(command));
        return "CUD executed";
    }

    @Override
    public void executeSelect(SelectCommand command) {
        loadBalancer.chooseDatabase().addCommandToQueue(command);
    }

    public List<Club> executeSelect(String command) {
        return null;
    }

    public void test() {
        System.out.println("test");
    }

    @Override
    public void changeBalanceStrategyAsMinLoad() {
        this.loadBalancer.setBalanceStrategy(true);
    }

    @Override
    public void changeBalanceStrategyAsRoundRobin() {
        this.loadBalancer.setBalanceStrategy(false);
    }

    public String getConnectedDataBaseDescription() {
        return getDatabases().stream().map(DataBaseInstance::getDescription).collect(Collectors.joining("\n"));
    }

    @Override
    public String getAllDataBasesDescription() {
        return databaseInstances.stream().map(DataBaseInstance::getDescription).collect(Collectors.joining("\n"));
    }
}
