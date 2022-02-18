package pl.agh.dp.loadbalancer.client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import pl.agh.dp.loadbalancer.Employee.EmployeeEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerTest {

    public void test() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Employee");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("Starting Transaction");
        entityManager.getTransaction().begin();

        EmployeeEntity employee = new EmployeeEntity();
        employee.setFirstName("Ahmed");
        employee.setLastName("Sam");
        System.out.println("Saving Employee to Database");

        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        System.out.println("Generated Employee ID = " + employee.getId());

        // get an object using primary key.
        EmployeeEntity emp = entityManager.find(EmployeeEntity.class, employee.getId());
        System.out.println("got object " + emp.getFirstName() + " " + emp.getLastName() + " " + emp.getId());

        // close the entity manager
        entityManager.close();
        entityManagerFactory.close();

    }
}
