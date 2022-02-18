package pl.agh.dp.loadbalancer.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@SpringBootApplication
public class Client {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initDb() {


        System.out.println("****** Inserting more sample data in the table: Employees ******");
        String sqlStatements[] = {
                "insert into employees(first_name, last_name) values('Donald','Trump')",
                "insert into employees(first_name, last_name) values('Barack','Obama')"
        };

        Arrays.asList(sqlStatements).stream().forEach(sql -> {
            System.out.println(sql);
            jdbcTemplate.execute(sql);
        });

//        System.out.println("****** PM part ******");
//        EntityManagerTest entityTest = new EntityManagerTest();
//        entityTest.test();

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

    public static void main(String[] args) {

        System.out.println("****** Client start ******");
        SpringApplication.run(Client.class, args);


    }

}