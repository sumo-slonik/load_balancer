package pl.agh.dp.loadbalancer.command;

public class DeleteCommand extends Command{

    final String deleteString;

    public DeleteCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor);
        this.deleteString = insertString;
    }

    @Override
    public void execute() {
        databasesExecutor.databasesInterface.executeCUD(this);
    }

    @Override
    public String getCommand() {
        return this.deleteString;
    }

}
