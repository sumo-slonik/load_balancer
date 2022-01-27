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


    @Override
    public void run() {
        while (true){
            if(isEnd){
                return;
            }

            T commandToProcess = this.queryQueue.get();

            if(databaseInstance.getState().equals(DataBaseStates.CONNECTED)){
                // wysylamy wszystko


            } else if(databaseInstance.getState().equals(DataBaseStates.RESTORING)){
                // odbiermay wszystko, wysyłamy dmle



            } else{
                // nie pobiera


            }

            String commandAsString = commandToProcess.getCommand();

            // send to db

            Session databaseSession =  databaseInstance.getSession();

            Query resultQuery;

            try{
                resultQuery = databaseSession.createQuery(commandAsString);
            } catch (HibernateException exception){
                System.out.println(exception.toString());
            }

            

        }
    }
}
