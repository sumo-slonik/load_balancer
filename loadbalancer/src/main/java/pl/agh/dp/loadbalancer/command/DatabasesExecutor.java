package pl.agh.dp.loadbalancer.command;

import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterfaceImpl;

public class DatabasesExecutor {

    final DatabasesInterface databasesInterface = new DatabasesInterfaceImpl();

    public void performInsert(String insertString){
        Command insertCommand = new InsertCommand(this, insertString);
        insertCommand.execute();
    }

    public void performSelect(String selectString){
        Command selectCommand = new SelectCommand(this, selectString);
        selectCommand.execute();
    }

    public void performUpdate(String updateString){
        Command updateCommand = new UpdateCommand(this, updateString);
        updateCommand.execute();
    }

    public void performDelete(String deleteString){
        Command deleteCommand = new DeleteCommand(this, deleteString);
        deleteCommand.execute();
    }


}
