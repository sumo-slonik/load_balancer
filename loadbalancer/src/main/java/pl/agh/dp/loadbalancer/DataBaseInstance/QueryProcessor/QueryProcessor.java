package pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor;

import lombok.Setter;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseStates;
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

    public QueryProcessor(QueryQueue<T> queryQueue){
        this.queryQueue = queryQueue;
    }


    @Override
    public void run() {
        while (true){
            if(isEnd){
                return;
            }

            T commandToProcess = this.queryQueue.get();

            if(databaseInstance.getState().equals(DataBaseStates.CONNECTED)){
                
            }

            String commandAsString = commandToProcess.getCommand();

            // send to db



        }
    }
}
