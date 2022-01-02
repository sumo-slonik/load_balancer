package pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="datasourcetwo.datasource")
@Getter
@Setter
public class SecondDataBaseConfig {
    private String url;
    private String password;
    private String username;
}
