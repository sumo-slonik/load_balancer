package pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor;

import lombok.Getter;
import lombok.Setter;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstanceImpl;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryQueue;

import java.util.concurrent.LinkedBlockingQueue;

public class QueryQueueImpl<T> implements QueryQueue<T> {

    @Setter
    @Getter
    private LinkedBlockingQueue<T> queue;

    public QueryQueueImpl(LinkedBlockingQueue<T> queue) {
        this.queue = queue;
    }

    public QueryQueueImpl() {

        this.queue = new LinkedBlockingQueue<T>();

    }



    @Override
    public T get() {

        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void add(T elementToAdd) {
        this.queue.add(elementToAdd);
    }


}
