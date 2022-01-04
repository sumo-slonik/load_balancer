package pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.POJO.DBConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="datasourceeigth.datasource")
@Getter
@Setter
public class EigthDataBaseConfig extends DataBaseConfig {

}
