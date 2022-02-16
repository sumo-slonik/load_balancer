package pl.agh.dp.loadbalancer.DataBasesInterface;

import lombok.Getter;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConnectionChecker {

    private DataBaseInstance databaseInstance;

    @Getter
    private static final long delay = 5000L;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

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
            Thread t = new Thread(()-> {
                if (databaseInstance.checkConnection()) {
                    databaseInstance.establishConnection();
                    databaseInstance.updateLatency();
                } else {
                    databaseInstance.loseConnection();
                }
            });
            Timer timer = new Timer();
            timer.schedule(new TimeOutTask(t, timer), delay-100L);
            t.start();
            System.out.println(databaseInstance.getState().toString()+" "+databaseInstance.getDataBaseNumber()+" "+databaseInstance.getLatency());

        }
        class TimeOutTask extends TimerTask {
            private Thread t;
            private Timer timer;

            TimeOutTask(Thread t, Timer timer){
                this.t = t;
                this.timer = timer;
            }

            public void run() {
                if (t != null && t.isAlive()) {
                    t.interrupt();
                    databaseInstance.loseConnection();
                    timer.cancel();
                }
            }
        }

    }






}
