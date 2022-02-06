package pl.agh.dp.loadbalancer.Connection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstanceImpl;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBaseNumber;
import pl.agh.dp.loadbalancer.RequestServer.RequestServer;

import java.util.LinkedList;
import java.util.List;

@Configuration
public class DataBaseInstancesCreation {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    List<DataBaseConnectionConfig> dataBaseConnectionConfigs;

    @Bean
    List<DataBaseInstance> dataBaseInstances()
    {
        List<DataBaseInstance> result = new LinkedList<>();
        DataBaseNumber number = DataBaseNumber.FIRST;
        for (DataBaseConnectionConfig config:dataBaseConnectionConfigs)
        {
            result.add(new DataBaseInstanceImpl(number,config));
            number=number.next();
        }
        return result;
    }

}
