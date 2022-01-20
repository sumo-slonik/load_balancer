package pl.agh.dp.loadbalancer.command;

public class SelectCommand extends Command {

    final String selectString;

    public SelectCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor);
        this.selectString = insertString;
    }

    @Override
    public void execute() {
        databasesExecutor.databasesInterface.executeCUD(this);
    }

}
