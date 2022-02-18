package pl.agh.dp.loadbalancer;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


@SpringBootApplication
@ComponentScan("pl.agh.dp.loadbalancer.Connection")
//@EnableJpaRepositories(basePackages = { "pl.agh.dp.loadbalancer" })
public class LoadbalancerApplication {
    private static ApplicationContext applicationContext;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(LoadbalancerApplication.class, args);
        System.out.println("kuba");
    }


    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer() throws SQLException, SQLException {
        return Server.createTcpServer(
                "-tcp", "-tcpAllowOthers", "-tcpPort", "9099");
    }

    @PostConstruct
    private void initDb() {

        System.out.println(String.format("****** Creating table: %s, and Inserting test data ******", "Employees"));

        String sqlStatements[] = {
                "drop table employees if exists",
                "create table employees(id serial,first_name varchar(255),last_name varchar(255))",
                "insert into employees(first_name, last_name) values('Eugen','Paraschiv')",
                "insert into employees(first_name, last_name) values('Scott','Tiger')"
        };

        Arrays.asList(sqlStatements).stream().forEach(sql -> {
            System.out.println(sql);
            jdbcTemplate.execute(sql);
        });

        System.out.println(String.format("****** Fetching from table: %s ******", "Employees"));
        jdbcTemplate.query("select id,first_name,last_name from employees",
                new RowMapper<Object>() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        System.out.println(String.format("id:%s,first_name:%s,last_name:%s",
                                rs.getString("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name")));
                        return null;
                    }
                });
    }
}
