package com.denisjulio;

import com.denisjulio.entities.Person;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.Transactional;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.h2.tools.Server;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AppTest {

    private EntityManagerFactory emf;
    private static Server server;
    private static Injector injector;

    @BeforeAll
    public static void initServer() throws SQLException {
        server = Server.createTcpServer().start();
//        injector = Guice.createInjector(new MyModule());
        injector = Guice.createInjector(new JpaPersistModule("MyPU"));
        injector.getInstance(PersistService.class).start();
    }

    @BeforeEach
    void init() {
        emf = Persistence.createEntityManagerFactory("MyPU");
        var consoleConnectionURL = "jdbc:h2:" + server.getURL() + "/mem:test";
        System.out.println(consoleConnectionURL);
    }

    @Test
    @DisplayName("Entity Manager Factory from Guice Persist Module gets instantiated")
    void emfFromGuicePersistExists() {
        var emfgp = injector.getInstance(EntityManagerFactory.class);
        assertNotNull(emfgp);
    }

    @Test
    public void shouldAnswerWithTrue() {
        assertNotNull(emf);
    }

    void repositoryExists() {
    }

    @Test
    void persistenceFromGuiceInfrastructeWork() {
//        var em = emf.createEntityManager();
        var em = injector.getInstance(EntityManager.class);
        persistPerson(em);

//        em.getTransaction().begin();

//        var p = new Person("Darlen Martins", LocalDate.of(1994, 3, 18));
//        System.out.println(p.getId());
//        em.persist(p);

//        em.getTransaction().begin();

        List<Person> lst = em.createQuery("select p from Person p", Person.class).getResultList();
        assertFalse(lst.isEmpty());
//        assertTrue(true);
    }

    @AfterAll
    public static void dontShutDown() {
        keepAlive();
//        assertTrue(true);
    }

    private List<Person> people() {
        return List.of(
                new Person("Denis", LocalDate.of(1992, 5, 28)),
                new Person("Darlen", LocalDate.of(1994, 3, 18)),
                new Person("Sophia", LocalDate.of(2016, 1, 3)),
                new Person("Renata", LocalDate.of(1991, 8, 3))
        );
    }

    static void keepAlive() {
        while (true) {
        }
    }

    @Transactional
    private void persistPerson(EntityManager em) {
        var p = new Person("Darlen Martins", LocalDate.of(1994, 3, 18));
        em.persist(p);
    }

    @Transactional
    private static void SpersistPerson(EntityManager em) {
        var p = new Person("Darlen Martins", LocalDate.of(1994, 3, 18));
        em.persist(p);
    }
}
