package pl.agh.dp.loadbalancer.client;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.agh.dp.loadbalancer.Employee.EmployeeEntity;

public class TestApp {

    public void test() {

        EmployeeEntity employee1 = new EmployeeEntity();
        employee1.setFirstName("Pankaj");
        employee1.setLastName("Dam");

        EmployeeEntity employee2 = new EmployeeEntity();
        employee2.setFirstName("Baljeet");
        employee2.setLastName("Kaj");

        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        // start a transaction
        transaction = session.beginTransaction();
        // save the student objects
        session.save(employee1);
        session.save(employee2);
        // commit transaction
        transaction.commit();

        List < EmployeeEntity > employees = session.createQuery("from EmployeeEntity", EmployeeEntity.class).list();
        employees.forEach(e -> System.out.println(e.getFirstName()));

    }
}
