package pl.agh.dp.loadbalancer.client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import pl.agh.dp.loadbalancer.Employee.EmployeeEntity;

import java.io.IOException;
import java.net.Socket;

/***
 * Used only to create the session
 */

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                registry = new StandardServiceRegistryBuilder().configure().build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();
                SessionFactoryBuilder sessionFactoryBuilder = metadata.getSessionFactoryBuilder();
                sessionFactory = sessionFactoryBuilder.build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    // Session has a socket related to it
    public static CustomSession getSession() throws IOException {

        Socket socket = createSocket();

        QueryInterceptor interceptor = new QueryInterceptor(socket);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.withOptions().interceptor(interceptor).openSession();

        return new CustomSession(session, socket);
    }

    static Socket createSocket() throws IOException {

        String hostname = "localhost";
        int port = 9090;

        Socket socket = new Socket(hostname, port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        return socket;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

