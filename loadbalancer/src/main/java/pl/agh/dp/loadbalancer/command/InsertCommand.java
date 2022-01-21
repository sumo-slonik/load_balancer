package pl.agh.dp.loadbalancer.command;

public class InsertCommand extends Command {

    final String insertString;

    public InsertCommand(DatabasesExecutor databasesExecutor, String insertString){
        super(databasesExecutor);
        this.insertString = insertString;
    }

    @Override
    public void execute() {
        databasesExecutor.databasesInterface.executeCUD(this);
    }

    @Override
    public String getCommand() {
        return this.insertString;
    }
}
