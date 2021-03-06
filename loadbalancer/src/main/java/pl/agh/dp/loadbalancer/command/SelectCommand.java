package pl.agh.dp.loadbalancer.command;

import lombok.Getter;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;

public class SelectCommand extends Command {

    final String selectString;

    public SelectCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor, QueryType.SELECT);
        this.selectString = insertString;
    }

    @Override
    public void execute() {
         databasesExecutor.getDatabasesInterface().executeSelect(this);
    }

    public void executeOn(DatabasesInterface databasesInterface) {
        databasesInterface.executeSelect(this);
    }

    @Override
    public String getCommand() {
        return this.selectString;
    }

}
