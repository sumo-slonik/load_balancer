package pl.agh.dp.loadbalancer.DataBasesInterface;

import lombok.Getter;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConnectionChecker {

    private DataBaseInstance databaseInstance;

    @Getter
    private static final long delay = 5000L;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

    public ConnectionChecker(DataBaseInstance databaseInstance) {
        this.databaseInstance = databaseInstance;
        startCheckingConnection();
    }

    public void startCheckingConnection(){

        executor.scheduleAtFixedRate(new CheckConnectionTask(),0L, delay, TimeUnit.MILLISECONDS);

    }

    private class CheckConnectionTask implements Runnable{

        @Override
        public void run() {
            System.out.println(databaseInstance.getState()+" "+databaseInstance.getDataBaseNumber()+" "+databaseInstance.getLatency());
            databaseInstance.checkConnection();
            databaseInstance.updateLatency();
        }
    }






}
