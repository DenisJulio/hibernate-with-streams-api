package com.denisjulio.infrastructure;

import com.google.inject.Injector;
import lombok.Getter;
import org.h2.tools.Server;

import java.sql.SQLException;

public class InfrastructureObjects {

    @Getter
    private final Injector injector;
    @Getter
    private final Server server;
    @Getter
    private final String[] serverOptions;

    protected InfrastructureObjects(Injector injector) {
        this(injector, new String[]{});
    }

    protected InfrastructureObjects(Injector injector, String[] serverOptions) {
        this.injector = injector;
        this.serverOptions = serverOptions;
        Server server;
        try {
            server = Server.createTcpServer(serverOptions);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        this.server = server;
    }

}