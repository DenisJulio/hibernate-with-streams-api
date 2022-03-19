package com.denisjulio.infrastructure;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AppInfrastructure {

    private static final Logger logger = LoggerFactory.getLogger(AppInfrastructure.class);
    private static final String[] SERVER_OPTIONS = {"-tcpPort", "9090"};
    private static final String PERSISTENCE_UNIT_NAME = "MyPU";
    private static final Module[] INJECTOR_MODULES = {new JpaPersistModule(PERSISTENCE_UNIT_NAME), new AppModule()};

    private static InfrastructureObjects infrastructureObjects;

    /**
     * Retrieves the Guice {@link Injector}
     *
     * @return the Injector associated with the application
     * @throws UnsupportedOperationException when invoked before {@code plug()}
     */
    public static Injector injector() {
        if (infrastructureObjects == null) {
            throw new UnsupportedOperationException("The plug method should be called before invoking injector()");
        }
        return infrastructureObjects.getInjector();
    }

    /**
     * Method responsible for bootstrapping the application infrastructure
     * when invoked.
     */
    public static void plug() {
        setInfrastructureObjects();
        if (! infrastructureObjects.getServer().isRunning(true)) {
            try {
                infrastructureObjects.getServer().start();
                infrastructureObjects.getInjector().getInstance(PersistService.class).start();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            startBackgroundThread();
        }
        var h2ConsoleConnectionURL = "jdbc:h2:" + infrastructureObjects.getServer().getURL() + "/mem:test";
        logger.info("Connect to H2 console: {}", h2ConsoleConnectionURL);
    }

    private static void setInfrastructureObjects() {
        if (infrastructureObjects == null) {
            infrastructureObjects = new InfrastructureObjects(
                    Guice.createInjector(INJECTOR_MODULES),
                    SERVER_OPTIONS
            );
        }
    }

    /**
     * This method will keep the application running for debugging
     * purposes with H2 console.
     */
    private static void startBackgroundThread() {
        new Thread(() -> {
            while (true) {
            }
        }).start();
    }
}
