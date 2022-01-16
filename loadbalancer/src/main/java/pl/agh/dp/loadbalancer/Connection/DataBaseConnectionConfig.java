package pl.agh.dp.loadbalancer.Connection;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Configuration;
import pl.agh.dp.loadbalancer.ClubPackage.Club;

@Data
public class DataBaseConnectionConfig {
    private final String driverClass;
    private final String connectionUrl;
    private final String userName;
    private final String password;
    private final String dialect;

    public DataBaseConnectionConfig() {

    }

    public Configuration getConfiguration()
    {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class",driverClass );
        configuration.setProperty("hibernate.connection.url", connectionUrl);
        configuration.setProperty("hibernate.connection.username", userName);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.dialect", dialect);
        configuration.addAnnotatedClass(Club.class);
        return configuration;
    }

}
