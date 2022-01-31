package pl.agh.dp.loadbalancer.command;

import lombok.Getter;

public class InsertCommand extends Command {

    final String insertString;
    @Getter
    final QueryType queryType = QueryType.INSERT;


    public InsertCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor);
        this.insertString = insertString;
    }

    @Override
    public String execute() {
        return databasesExecutor.databasesInterface.executeCUD(this);
    }

    @Override
    public String getCommand() {
        return this.insertString;
    }
}
