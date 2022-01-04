package pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.POJO.DBConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix="datasourceone.datasource")
@Getter
@Setter
public class FirstDataBaseConfig extends DataBaseConfig{
}
