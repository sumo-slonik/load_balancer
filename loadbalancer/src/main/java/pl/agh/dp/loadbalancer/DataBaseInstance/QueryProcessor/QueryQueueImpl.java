package pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor;

import lombok.Getter;
import lombok.Setter;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstanceImpl;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryQueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueryQueueImpl<T> implements QueryQueue<T> {

    @Setter
    @Getter
    private LinkedBlockingQueue<T> queue;

    public QueryQueueImpl() {

        this.queue = new LinkedBlockingQueue<T>();

    }



    @Override
    public T get() {

        try {
            return queue.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void add(T elementToAdd) {
        this.queue.add(elementToAdd);
    }

    @Override
    public Boolean isEmpty() {
        return queue.isEmpty();
    }
}
