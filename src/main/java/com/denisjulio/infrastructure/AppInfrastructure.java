package com.denisjulio.infrastructure;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppInfrastructure {

    private static final Logger logger = LoggerFactory.getLogger(AppInfrastructure.class);
    private static final String[] SERVER_OPTIONS = {"-tcpPort", "9090"};
    private static final String PERSISTENCE_UNIT_NAME = "MyPU";
    private static final Module[] INJECTOR_MODULES = {new JpaPersistModule(PERSISTENCE_UNIT_NAME), new AppModule()};
    private static AppInfrastructure INSTANCE;

    private final Injector injector;
    private final Server server;

    /**
     * Method responsible for bootstrapping the application infrastructure
     * when invoked.
     */
    public static void plug() {
        initInstance();
        if (!INSTANCE.server.isRunning(false)) {
            try {
                INSTANCE.server.start();
                INSTANCE.injector.getInstance(PersistService.class).start();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            keepApplicationRunning();
        }
        var consoleConnectionURL = "jdbc:h2:" + INSTANCE.server.getURL() + "/mem:test";
        logger.info("Connect to H2 console: {}", consoleConnectionURL);
    }

    /**
     * Retrieves the Guice {@link Injector}
     *
     * @return the Injector associated with the application
     */
    public static Injector injector() {
        return INSTANCE.injector;
    }

    private static void initInstance() {
        if (INSTANCE == null) {
            Server server = null;
            try {
                server = Server.createTcpServer(SERVER_OPTIONS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            INSTANCE = new AppInfrastructure(
                    Guice.createInjector(INJECTOR_MODULES),
                    server
            );
        }
    }

    /**
     * This method will keep the application running for debugging
     * purposes with H2 console.
     */
    private static void keepApplicationRunning() {
        new Thread(() -> {
            while (true) {
            }
        }).start();
    }
}
