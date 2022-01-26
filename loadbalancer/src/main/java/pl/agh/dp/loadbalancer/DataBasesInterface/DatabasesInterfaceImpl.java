package pl.agh.dp.loadbalancer.DataBasesInterface;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.command.Command;

import java.util.LinkedList;
import java.util.List;

@Component("DataBasesInterface")
public class DatabasesInterfaceImpl implements DatabasesInterface{

    @Autowired
    List<DataBaseInstance> dataBaseInstances;
    List<DataBaseInstance> databases;


    public void printConf()
    {

    }

    @Override
    public List<DataBaseInstance> getDatabases() {
        return databases;
    }


    public void executeCUD(Command command){
    }

    @Override
    public void executeSelect(Command command) {

    }

    public List<Club> executeSelect(String command){
//        List clubs = firstConfig.getConfiguration().buildSessionFactory().openSession().createSQLQuery("select * from dp_instance_1.clubs;").list();
//        List<Club> result = new LinkedList<>();
//        for(Object c : clubs)
//        {
//            Club club = new Club((Object[]) c);
//            result.add(club);
//        }
//        return result;
        return null;
    }

    public void test()
    {
        System.out.println("test");
    }

}
