package pl.agh.dp.loadbalancer.DataBasesInterface;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseStates;
import pl.agh.dp.loadbalancer.LoadBalancer.LoadBalancerImpl;
import pl.agh.dp.loadbalancer.LoadBalancer.LoadBalancerInterface;
import pl.agh.dp.loadbalancer.LoadBalancer.RoundRobinStrategy;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.SelectCommand;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabasesInterfaceImpl implements DatabasesInterface {

    @Autowired
    List<DataBaseInstance> dataBaseInstances;
    List<DataBaseInstance> databases;


    @PostConstruct
    public void initPingers() {
        for (DataBaseInstance dataBaseInstance : dataBaseInstances) {
            new ConnectionChecker(dataBaseInstance);
            dataBaseInstance.setDatabasesInterface(this);
        }

    }

    public void printConf() {

    }

    @Override
    public List<DataBaseInstance> getDatabases() {
        return dataBaseInstances.stream().
                filter(dataBaseInstance -> dataBaseInstance.getState().equals(DataBaseStates.CONNECTED))
                .collect(Collectors.toList());
    }


    public String executeCUD(Command command) {
        return "CUD executed";
    }

    @Override
    public void executeSelect(SelectCommand command) {
        LoadBalancerInterface loadBalancerInterface = new LoadBalancerImpl(this);

        RoundRobinStrategy rrs = new RoundRobinStrategy();

        rrs.chooseDatabase(getDatabases()).addCommandToQueue(command);
    }

    public List<Club> executeSelect(String command) {
//        List clubs = firstConfig.getConfiguration().buildSessionFactory().openSession().createSQLQuery("select * from dp_instance_1.clubs;").list();
//        List<Club> result = new LinkedList<>();
//        for(Object c : clubs)
//        {
//            Club club = new Club((Ocbject[]) c);
//            result.add(club);
//        }
//        return result;
        return null;
    }

    public void test() {
        System.out.println("test");
    }


}
