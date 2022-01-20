package pl.agh.dp.loadbalancer.command;

public class UpdateCommand extends Command{

    final String updateString;

    public UpdateCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor);
        this.updateString = insertString;
    }

    @Override
    public void execute() {
        databasesExecutor.databasesInterface.executeCUD(this);
    }

}
