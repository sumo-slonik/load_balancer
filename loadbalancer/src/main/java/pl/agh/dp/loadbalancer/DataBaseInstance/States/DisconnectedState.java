package pl.agh.dp.loadbalancer.DataBaseInstance.States;

import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor.QueryProcessor;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.QueryType;
import pl.agh.dp.loadbalancer.command.SelectCommand;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class DisconnectedState extends DataBaseState {

    private final DataBaseInstance dataBaseInstance;



    private void returnSelectsToDatabasesInterface() {
        List<Command> cudCommands = new LinkedList<>();
        QueryProcessor<Command> queryProcessor = this.dataBaseInstance.getQueryProcesor();


        synchronized (queryProcessor) {

            while (!queryProcessor.hasEmptyQueue()) {
                Command command = queryProcessor.getCommand();

                if (command.getQueryType().equals(QueryType.SELECT)) {
                    this.dataBaseInstance.throwbackSelectCommand((SelectCommand) command);
                } else {
                    cudCommands.add(command);
                }
            }
            cudCommands.forEach(queryProcessor::addCommandToQueue);

        }

    }

    @Override
    public void loseConnection(DataBaseInstance databaseInstance) {
// nothing
    }

    @Override
    public void establishConnection(DataBaseInstance databaseInstance) {


        databaseInstance.setState(new RestoringState(databaseInstance));

        synchronized (this.dataBaseInstance.getQueryProcesor()) {
            this.dataBaseInstance.getQueryProcesor().notify();
        }


    }

    @Override
    public DataBaseStates getState() {
        return DataBaseStates.DISCONNECTED;
    }

    @Override
    public boolean isConnected() {
        boolean result = true;
        try {
            dataBaseInstance.ping();
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    @Override
    public void queryProcessorHandle() {
        synchronized (this.dataBaseInstance.getQueryProcesor()) {
            this.returnSelectsToDatabasesInterface();
            try {
                this.dataBaseInstance.getQueryProcesor().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
