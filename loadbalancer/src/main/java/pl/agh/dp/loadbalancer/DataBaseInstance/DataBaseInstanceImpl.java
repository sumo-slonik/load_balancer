package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.DataBasesInterface.ConnectionChecker;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBases;

import java.util.List;

@RequiredArgsConstructor
public class DataBaseInstanceImpl implements DataBaseInstance {

    private final DataBases dataBases;
    private Session dataBaseSession;

    @Setter
    private DataBaseState state = new DisconnectedState(this);

    private final DataBaseConnectionConfig dataBaseConnectionConfig;


    @Override
    public boolean sendQuery(String query) {
        return false;
    }

    @Override
    public DataBaseStates getState() {
        return this.state.getState();
    }

    @Override
    public Double getLoad() {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return dataBaseConnectionConfig.getConfiguration();
    }

    @Override
    public void createSession() {
         this.dataBaseSession = getSessionFactory().openSession();
    }

    @Override
    public Session getSession() {
        return this.dataBaseSession;
    }

    @Override
    public void loseConnection() {
        this.state.loseConnection(this);

    }

    @Override
    public void establishConnection() {
        this.state.establishConnection(this);

    }

    @Override
    public void setState(DataBaseState dataBaseState) {
        this.state = dataBaseState;
    }

    private SessionFactory getSessionFactory() {
        return getConfiguration().buildSessionFactory();
    }


    @Override
    public void processQuery(String select) {

        NativeQuery result = this.dataBaseSession.createSQLQuery(select);
        List clubs = result.list();
        for(Object c : clubs)
        {
            Club club = new Club((Object[]) c);
            System.out.println(club.toString());
        }
    }

    public void checkConnection() {

        if (this.state.isConnected()) {
            establishConnection();
        } else {
            loseConnection();
        }
    }


    private ConnectionChecker connectionChecker;

    public void startConnectionChecker(){

        connectionChecker = new ConnectionChecker(this);

        connectionChecker.startCheckingConnection();

    }


}
