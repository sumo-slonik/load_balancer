package pl.agh.dp.loadbalancer.command;

public abstract class Command {

    final DatabasesExecutor databasesExecutor;

    public Command(DatabasesExecutor databasesExecutor){
        this.databasesExecutor = databasesExecutor;
    }

    public abstract String execute();

    public abstract String getCommand();

    public abstract QueryType getQueryType();

}
