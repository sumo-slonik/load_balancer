package pl.agh.dp.loadbalancer.command;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class UpdateCommand extends Command{

    final String updateString;
    @Getter
    final QueryType queryType = QueryType.UPDATE;

    public Map<String, String> parameters = new HashMap<>();

    public UpdateCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor);
        this.updateString = insertString;
    }

    @Override
    public void execute() {
         databasesExecutor.getDatabasesInterface().executeCUD(this);
    }

    @Override
    public String getCommand() {
        return this.updateString;
    }

}
