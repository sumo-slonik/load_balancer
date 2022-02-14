package pl.agh.dp.loadbalancer.DataBaseInstance.States;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.service.spi.ServiceException;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.command.Command;
import pl.agh.dp.loadbalancer.command.QueryType;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestoringState extends DataBaseState {

    private final DataBaseInstance dataBaseInstance;

    public RestoringState(DataBaseInstance dataBaseInstance){
        this.dataBaseInstance = dataBaseInstance;
//        this.dataBaseInstance.notifyQueryProcessor();
    }

//    @PostConstruct
//    @Override
//    public void notifyQueryProcessor() {
//
//        System.out.println("notifyQueryProcessor!!!!!!!!!!!!!!!");
//
//    }

    @Override
    public void addCommandToQueue() {

    }

    @Override
    public void processCommandFromQueue() {

    }

    @Override
    public void loseConnection(DataBaseInstance databaseInstance) {
        databaseInstance.setState(new DisconnectedState(dataBaseInstance));
    }

    @Override
    public void establishConnection(DataBaseInstance databaseInstance) {
        databaseInstance.setState(new ConnectedState(dataBaseInstance));
    }

    @Override
    public DataBaseStates getState() {
        return DataBaseStates.RESTORING;
    }

    @Override
    public boolean isConnected() {
        boolean result = true;
        try {
            dataBaseInstance.getConfiguration().buildSessionFactory().openSession();
        } catch (ServiceException | IllegalStateException ex) {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }

    @Override
    public void queryProcessorHandle() {

        synchronized (this.dataBaseInstance.getQueryProcesor()) {

            if (dataBaseInstance.hasEmptyQueue()) {
                establishConnection(dataBaseInstance);
            } else {

                Command command = this.dataBaseInstance.getQueryProcesor().getCommand();

                if (!command.getQueryType().equals(QueryType.SELECT)) {

                    Session databaseSession = this.dataBaseInstance.getSession();

                    Query resultQuery = null;

                    try {
                        if(command.queryType.equals(QueryType.INSERT)){
                            databaseSession.beginTransaction();
                            //Add new Employee object
                            Club club = new Club();
                            club.setClubName("nowy klub testowy 2");
                            club.setCity("Kraków");
                            club.setProvince("Podkarpacie");
                            Date date1 = null;
                            try {
                                date1=new SimpleDateFormat("yyyy-mm-dd").parse("2022-02-02");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            club.setFoundationDate(date1);
                            //Save the employee in database
                            databaseSession.save(club);
                            //Commit the transaction
                            databaseSession.getTransaction().commit();
                        }
                        else
                        {
                            resultQuery = databaseSession.createQuery(command.getCommand());
                            int transactionResult = command.handleQueryParameters(resultQuery, databaseSession);
                        }

                    } catch (HibernateException exception) {
                        System.out.println(exception.toString());
                    }


                } else {
                    command.setResult("select failed");
                    command.notify();
                }

            }

        }

    }
}


