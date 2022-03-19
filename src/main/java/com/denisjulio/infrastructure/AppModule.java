package com.denisjulio.infrastructure;

import com.denisjulio.Repository;
import com.google.inject.AbstractModule;

class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Repository.class);
    }
}
