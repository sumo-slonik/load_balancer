package pl.agh.dp.loadbalancer.command;

import lombok.Getter;

public class SelectCommand extends Command {

    final String selectString;
    @Getter
    final QueryType queryType = QueryType.SELECT;

    public SelectCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor);
        this.selectString = insertString;
    }

    @Override
    public void execute() {
         databasesExecutor.getDatabasesInterface().executeSelect(this);
    }

    @Override
    public String getCommand() {
        return this.selectString;
    }

}
