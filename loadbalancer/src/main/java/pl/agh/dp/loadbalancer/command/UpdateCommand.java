package pl.agh.dp.loadbalancer.command;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.Map;

public class UpdateCommand extends Command{


    final String updateString;

    public UpdateCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor);
        this.updateString = insertString;
        queryType = QueryType.UPDATE;
    }

    @Override
    public void execute() {
         databasesExecutor.getDatabasesInterface().executeCUD(this);
    }

    @Override
    public String getCommand() {
        return this.updateString;
    }

    @Override
    public QueryType getQueryType() {
        return queryType;
    }

    @Override
    public int handleQueryParameters(Query updateQuery, Session session) {
        return 0;
    }

}
