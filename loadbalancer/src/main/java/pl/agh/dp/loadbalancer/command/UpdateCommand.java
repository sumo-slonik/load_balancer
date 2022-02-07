package pl.agh.dp.loadbalancer.command;

import lombok.Getter;

public class UpdateCommand extends Command{

    final String updateString;
    @Getter
    final QueryType queryType = QueryType.UPDATE;

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
