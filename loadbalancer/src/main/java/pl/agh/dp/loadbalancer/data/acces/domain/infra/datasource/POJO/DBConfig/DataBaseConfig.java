package pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.POJO.DBConfig;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataBaseConfig {
    protected String url;
    protected String password;
    protected String username;
}
