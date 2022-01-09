package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBases;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@RequiredArgsConstructor
public class DBInstanceImpl implements DatabaseInstance{

    private final DataBases dataBases;
    private DataBaseState state;
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

    }

    @Override
    public void getConnection() {

    }

    private SessionFactory getSessionFactory()
    {
         return getConfiguration().buildSessionFactory();
    }

//  tutaj walniesz zamiast selecta swojego comanda

    List<Club> processSelect(String select)
    {
        Session session = this.getSession();
        List<Club> results = session.createSQLQuery(select).list();
        return results;
    }

    void checkConnection()
    {
        Pinger.checkConnection(this);
    }
}
