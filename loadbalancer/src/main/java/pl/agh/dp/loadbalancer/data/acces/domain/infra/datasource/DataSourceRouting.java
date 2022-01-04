package pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.POJO.DBConfig.FirstDataBaseConfig;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.POJO.DBConfig.SecondDataBaseConfig;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRouting extends AbstractRoutingDataSource {
    private FirstDataBaseConfig firstDataBaseConfig;
    private SecondDataBaseConfig secondDataBaseConfig;
    private DataBasesContextHolder dataBasesContextHolder;


    public DataSourceRouting(DataBasesContextHolder dataBasesContextHolder, FirstDataBaseConfig firstDataBaseConfig, SecondDataBaseConfig secondDataBaseConfig) {
        this.firstDataBaseConfig = firstDataBaseConfig;
        this.secondDataBaseConfig = secondDataBaseConfig;
        this.dataBasesContextHolder = dataBasesContextHolder;

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataBases.FIRST,firstDataBase());
        dataSourceMap.put(DataBases.SECOND, secondDataBase());
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(firstDataBase());
    }
    @Override
    protected Object determineCurrentLookupKey() {
        return dataBasesContextHolder.getBranchContext();
    }
    public DataSource firstDataBase()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(firstDataBaseConfig.getUrl());
        dataSource.setUsername(firstDataBaseConfig.getUsername());
        dataSource.setPassword(firstDataBaseConfig.getPassword());
        return dataSource;
    }

    public DataSource secondDataBase()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(secondDataBaseConfig.getUrl());
        dataSource.setUsername(secondDataBaseConfig.getUsername());
        dataSource.setPassword(secondDataBaseConfig.getPassword());
        return dataSource;
    }

}

