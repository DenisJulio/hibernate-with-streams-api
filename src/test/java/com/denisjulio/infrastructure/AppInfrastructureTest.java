package com.denisjulio.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppInfrastructureTest {

    @Test
    @DisplayName(
            "When: plug() method is invoked; " +
            "Then: no Exception is thrown."
    )
    void appPlugsWithoutThrowing() {
        assertDoesNotThrow(AppInfrastructure::plug);
    }

    @Test
    @DisplayName(
            "When: injector() is invoked before plug(); " +
            "Then: throws UnsupportedOperationException."
    )
    void throwsCallingInjectorBeforePlug() {
        assertThrows(
                UnsupportedOperationException.class,
                AppInfrastructure::injector
        );
    }

    @Test
    @DisplayName(
            "When: invoking injector() after plug(); " +
            "Then: the returned injector is not null."
    )
    void callingInjectorAfterPlugDoesNotReturnNull() {
        AppInfrastructure.plug();
        assertNotNull(AppInfrastructure.injector());
    }
}