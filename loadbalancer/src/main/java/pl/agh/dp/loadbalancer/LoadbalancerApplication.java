package pl.agh.dp.loadbalancer;

import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import pl.agh.dp.loadbalancer.Connection.DataBaseConnectionConfig;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstanceImpl;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBases;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("pl.agh.dp.loadbalancer.Connection")
public class LoadbalancerApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(LoadbalancerApplication.class, args);
        System.out.println("kuba");
//        DataBaseConnectionConfig dataBaseConnectionConfig1 = new DataBaseConnectionConfig(
//                "com.mysql.cj.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/dp_instance_3",
//                "root",
//                "admin",
//                "org.hibernate.dialect.MySQLDialect");
//        DataBaseConnectionConfig dataBaseConnectionConfig2 = new DataBaseConnectionConfig(
//                "com.mysql.cj.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/dp_instance_4",
//                "root",
//                "admin",
//                "org.hibernate.dialect.MySQLDialect");

//        DataBaseInstance dataBaseInstance1 = new DataBaseInstanceImpl(DataBases.FIRST,dataBaseConnectionConfig1);
//        DataBaseInstance dataBaseInstance2 = new DataBaseInstanceImpl(DataBases.SECOND,dataBaseConnectionConfig2);
//        dataBaseInstance1.checkConnection();
//        dataBaseInstance2.checkConnection();
//        System.out.println(dataBaseInstance1.getState());
//        System.out.println(dataBaseInstance2.getState());
        //tutaj przyład przekazania sql'a do instancji
        //niby wszystko działa
        // ale mapowanie jest zrobione paskudnie jeszcze je poprawię
//        dataBaseInstance2.processQuery("select * from clubs where province = 'lubelskie'");
//        Session test = null;


    }



}
