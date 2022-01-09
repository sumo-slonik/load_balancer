package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.service.spi.ServiceException;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBases;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@RequiredArgsConstructor
public class DBInstanceImpl implements DatabaseInstance {

    private final DataBases dataBases;
    @Setter
    private DataBaseState state = new DisconnectedState();
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
    public Session getSession() {
        return getSessionFactory().openSession();
    }

    @Override
    public void loseConnection() {
        this.state.loseConnection(this);

    }

    @Override
    public void getConnection() {
        this.state.getConnection(this);

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
        Session session = getSession();
        NativeQuery result = session.createSQLQuery(select);
        List clubs = result.list();
        for(Object c : clubs)
        {
            Club club = new Club((Object[]) c);
            System.out.println(club.toString());
        }


    }

    public void checkConnection() {
        Pinger.checkConnection(this);
    }
}
