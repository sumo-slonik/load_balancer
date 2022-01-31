package pl.agh.dp.loadbalancer.command;

import lombok.Getter;

public class DeleteCommand extends Command{

    final String deleteString;
    @Getter
    final QueryType queryType = QueryType.DELETE;


    public DeleteCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor);
        this.deleteString = insertString;
    }

    @Override
    public String execute() {
        return databasesExecutor.databasesInterface.executeCUD(this);
    }

    @Override
    public String getCommand() {
        return this.deleteString;
    }

}
