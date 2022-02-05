package pl.agh.dp.loadbalancer.command;

import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterfaceImpl;

public class DatabasesExecutor {

    final DatabasesInterface databasesInterface = new DatabasesInterfaceImpl();

    public String performInsert(String insertString){
        Command insertCommand = new InsertCommand(this, insertString);
        return "inserted";
    }

    public String performSelect(String selectString){
        SelectCommand selectCommand = new SelectCommand(this, selectString);
        return selectCommand.getResult();
    }

    public String performUpdate(String updateString){
        Command updateCommand = new UpdateCommand(this, updateString);
        return "updated";
    }

    public String performDelete(String deleteString){
        Command deleteCommand = new DeleteCommand(this, deleteString);
        return "deleted";
    }


}
