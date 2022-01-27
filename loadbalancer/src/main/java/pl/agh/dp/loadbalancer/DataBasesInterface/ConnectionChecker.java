package pl.agh.dp.loadbalancer.DataBasesInterface;

import lombok.Getter;
import lombok.Setter;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseStates;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConnectionChecker {

    private DataBaseInstance databaseInstance;

    @Getter
    private static final long delay = 500L;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

    public ConnectionChecker(DataBaseInstance databaseInstance) {
        this.databaseInstance = databaseInstance;
        startCheckingConnection();
    }

    public void startCheckingConnection(){

//        executor.schedule(new CheckConnectionTask(), delay, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(new CheckConnectionTask(),0L, delay, TimeUnit.MILLISECONDS);

    }



    private class CheckConnectionTask implements Runnable{

        @Override
        public void run() {
            System.out.println(databaseInstance.getState()+" "+databaseInstance.getDataBaseNumber());
//            executor.scheduleAtFixedRate(new CheckConnectionTask(),0L, delay, TimeUnit.MILLISECONDS);

            databaseInstance.checkConnection();
        }
    }





}
