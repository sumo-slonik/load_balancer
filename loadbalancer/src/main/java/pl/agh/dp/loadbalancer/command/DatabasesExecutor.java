package pl.agh.dp.loadbalancer.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterfaceImpl;

@RequiredArgsConstructor
@ComponentScan("pl/agh/dp/loadbalancer/DataBasesInterface")
public class DatabasesExecutor {

    @Autowired
    @Getter
    @Setter
    private DatabasesInterface databasesInterface;

    public String performInsert(String insertString){
        Command insertCommand = new InsertCommand(this, insertString);
        insertCommand.execute();
        return "inserted";
    }

    public String performSelect(String selectString){
        SelectCommand selectCommand = new SelectCommand(this, selectString);
        selectCommand.execute();
        return selectCommand.getResult();
    }

    public String performUpdate(String updateString){
        Command updateCommand = new UpdateCommand(this, updateString);
        updateCommand.execute();
        return "updated";
    }

    public String performDelete(String deleteString){
        Command deleteCommand = new DeleteCommand(this, deleteString);
        deleteCommand.execute();
        return "deleted";
    }


}
