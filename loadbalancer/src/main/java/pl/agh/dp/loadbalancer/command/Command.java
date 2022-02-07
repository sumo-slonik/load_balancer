package pl.agh.dp.loadbalancer.command;

public abstract class Command {

    final DatabasesExecutor databasesExecutor;

    private String result;

    public Command(DatabasesExecutor databasesExecutor){
        this.databasesExecutor = databasesExecutor;
    }

    public abstract void execute();

    public abstract String getCommand();

    public abstract QueryType getQueryType();

    public void setResult(String result){
        this.result = result;
    }

    public synchronized String getResult() {
        while(this.result == null){
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.result;
    }

}
