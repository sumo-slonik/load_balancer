package pl.agh.dp.loadbalancer.command;

import lombok.Getter;

public class InsertCommand extends Command {

    final String insertString;

    public InsertCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor,  QueryType.INSERT);
        this.insertString = insertString;
    }

    @Override
    public void execute() {
         databasesExecutor.getDatabasesInterface().executeCUD(this);
    }

    @Override
    public String getCommand() {
        return this.insertString;
    }

}
