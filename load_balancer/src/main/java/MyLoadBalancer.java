import Database.Database;
import Database.LoadBalancer;
import java.util.List;

public class MyLoadBalancer implements LoadBalancer {

    public List<Database> databases;

    public MyLoadBalancer(){

    }

    @Override
    public void addDatabase(Database database){

    }

    @Override
    public void deleteDatabase(Database database) {

    }


}
