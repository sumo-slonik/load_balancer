package pl.agh.dp.loadbalancer.DataBaseInstance;

import org.hibernate.service.spi.ServiceException;

public class Pinger {
    public static void checkConnection(DataBaseInstance databaseInstance) {
        Boolean connected = true;
        try {
            databaseInstance.getSession();
        } catch (ServiceException ex) {
            connected = false;
            databaseInstance.loseConnection();
        }
        if (connected) {
            databaseInstance.establishConnection();
        }
    }
}
