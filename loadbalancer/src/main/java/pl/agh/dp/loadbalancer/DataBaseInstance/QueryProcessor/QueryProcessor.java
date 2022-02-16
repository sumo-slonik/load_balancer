package pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor;

import lombok.Setter;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.command.Command;

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

    public T getCommand(){
        return this.queryQueue.get();
    }


    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    if (isEnd) {
                        return;
                    }
                    System.out.println("query processor loop");
                    try {


                        this.databaseInstance.getStateObject().queryProcessorHandle();
                    } catch (Exception ex){
                        System.out.println("queryProcessorHandle exception");
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }catch (Exception ex)
        {
            System.out.println("wyjątek w procesorze");
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
