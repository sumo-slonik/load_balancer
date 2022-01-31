package pl.agh.dp.loadbalancer.command;

import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterfaceImpl;

public class DatabasesExecutor {

    final DatabasesInterface databasesInterface = new DatabasesInterfaceImpl();

    public String performInsert(String insertString){
        Command insertCommand = new InsertCommand(this, insertString);
        return insertCommand.execute();
    }

    public String performSelect(String selectString){
        Command selectCommand = new SelectCommand(this, selectString);
        return selectCommand.execute();
    }

    public String performUpdate(String updateString){
        Command updateCommand = new UpdateCommand(this, updateString);
        return updateCommand.execute();
    }

    public String performDelete(String deleteString){
        Command deleteCommand = new DeleteCommand(this, deleteString);
        return deleteCommand.execute();
    }


}
