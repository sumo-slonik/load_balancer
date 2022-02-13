package pl.agh.dp.loadbalancer.command;

import lombok.Getter;

public class DeleteCommand extends Command{

    final String deleteString;

    public DeleteCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor, QueryType.DELETE);
        this.deleteString = insertString;
    }

    @Override
    public void execute() {
         databasesExecutor.getDatabasesInterface().executeCUD(this);
    }

    @Override
    public String getCommand() {
        return this.deleteString;
    }


}
