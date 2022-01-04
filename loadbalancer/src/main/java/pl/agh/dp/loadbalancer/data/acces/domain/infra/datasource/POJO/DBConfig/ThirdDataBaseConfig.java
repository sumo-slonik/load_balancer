package pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.POJO.DBConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="datasourcetree.datasource")
public class ThirdDataBaseConfig extends DataBaseConfig {
}
