package pl.agh.dp.loadbalancer.command;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.Map;

public abstract class Command {

    final DatabasesExecutor databasesExecutor;

    private String result;

    public Map<String, String> parameters = new HashMap<>();

    @Getter
    public QueryType queryType;

    public Command(DatabasesExecutor databasesExecutor){
        this.databasesExecutor = databasesExecutor;
    }

    public abstract void execute();

    public abstract String getCommand();

    public abstract QueryType getQueryType();

    public int handleQueryParameters(Query updateQuery, Session session){

        if(this.queryType.equals(QueryType.SELECT)){
            return 0;
        }

        // sets parameters in transaction to values from map
        // key is parameter name, value is parameter value
        // insert doesn't use parameters
        if(! this.queryType.equals(QueryType.INSERT)) {
            this.parameters.forEach(updateQuery::setParameter);
        }

        Transaction transaction =  session.beginTransaction();

        int result = updateQuery.executeUpdate();

        transaction.commit();

        session.close();

        return result;
    }

    public void setResult(String result){
        this.result = result;
    }

    public synchronized String getResult() {
        while(this.result == null){
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.result;
    }

}
