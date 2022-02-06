package pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor;

import lombok.Setter;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseStates;
import pl.agh.dp.loadbalancer.command.Command;

import org.hibernate.Session;

public class QueryProcessor<T extends Command> implements Runnable {


    private final QueryQueue<T> queryQueue;
    @Setter
    private boolean isEnd = false;

    private DataBaseInstance databaseInstance;

    public QueryProcessor(QueryQueue<T> queryQueue, DataBaseInstance databaseInstance) {
        this.queryQueue = queryQueue;
        this.databaseInstance = databaseInstance;
    }

    public QueryProcessor(DataBaseInstance databaseInstance){
        this.databaseInstance = databaseInstance;
        this.queryQueue = new QueryQueueImpl<T>();

    }

    public QueryProcessor(QueryQueue<T> queryQueue){
        this.queryQueue = queryQueue;
    }

    public T getCommand(){
        return this.queryQueue.get();
    }


    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (isEnd) {
                    return;
                }
                System.out.println("query processor loop");
                this.databaseInstance.getStateObject().queryProcessorHandle();
            }
        }
    }

    public Boolean hasEmptyQueue()
    {
        return queryQueue.isEmpty();
    }

    public void addCommandToQueue(T command)
    {
        queryQueue.add(command);
    }
}
