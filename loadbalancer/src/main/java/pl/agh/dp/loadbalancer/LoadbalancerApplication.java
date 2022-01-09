package pl.agh.dp.loadbalancer;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.ClubPackage.ClubRepository;
import pl.agh.dp.loadbalancer.ClubPackage.ClubService;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.DataBaseInstance.DBInstanceImpl;
import pl.agh.dp.loadbalancer.DataBaseInstance.DatabaseInstance;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBases;
import pl.agh.dp.loadbalancer.socetServer.SocetServer;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@ComponentScan("pl.agh.dp.loadbalancer.Connection")
public class LoadbalancerApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(LoadbalancerApplication.class, args);
        System.out.println("kuba");
        DataBaseConnectionConfig dataBaseConnectionConfig1 = new DataBaseConnectionConfig(
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/xdd",
                "root",
                "admin",
                "org.hibernate.dialect.MySQLDialect");
        DataBaseConnectionConfig dataBaseConnectionConfig2 = new DataBaseConnectionConfig(
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/dp_instance_4",
                "root",
                "admin",
                "org.hibernate.dialect.MySQLDialect");

        DatabaseInstance databaseInstance1 = new DBInstanceImpl(DataBases.FIRST,dataBaseConnectionConfig1);
        DatabaseInstance databaseInstance2 = new DBInstanceImpl(DataBases.SECOND,dataBaseConnectionConfig2);
        databaseInstance1.checkConnection();
        databaseInstance2.checkConnection();
        System.out.println(databaseInstance1.getState());
        System.out.println(databaseInstance2.getState());
        //tutaj przyład przekazania sql'a do instancji
        //niby wszystko działa
        // ale mapowanie jest zrobione paskudnie jeszcze je poprawię
        databaseInstance2.processQuery("select * from clubs where province = 'lubelskie'");
        Session test = null;


    }



}
