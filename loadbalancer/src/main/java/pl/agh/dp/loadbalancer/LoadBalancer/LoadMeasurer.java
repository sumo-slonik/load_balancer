package pl.agh.dp.loadbalancer.LoadBalancer;

import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.command.SelectCommand;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LoadMeasurer {

    DatabasesInterface dbInterface;
    List<Double> currentLoads;
    List<DataBaseInstance> databases;

    LoadMeasurer(DatabasesInterface dbInterface){

        this.dbInterface = dbInterface;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public synchronized void run() {
                measureLoads();
            }
        }, 0, 1000); //wait 0 ms before doing the action and do it every 1 second
    }

    synchronized void measureLoads(){
        for(int i = 0; i < databases.size(); i++){
            SelectCommand selectCommand = new SelectCommand(null, "select fetch_latency from sys.`x$schema_table_statistics` where table_schema like \"dp_instance_"+databases.get(i).getDataBaseNumber()+"\";");
            selectCommand.executeOn(dbInterface);
            double load = Double.parseDouble(selectCommand.getResult());
            currentLoads.set(i, load);
        }
    }

    synchronized List<Double> getLoads(List<DataBaseInstance> databases){
        if(!this.databases.equals(databases)){
            this.databases = databases;
            measureLoads();
        }
        return currentLoads;
    }

}
