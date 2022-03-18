package com.denisjulio.infrastructure;

import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class)
class InfrastructureObjectsTest {

    private InfrastructureObjects infrastructureObjects;
    @Mock
    private Injector injector;

    @Test
    @DisplayName(
            "When: constructor called without serverOptions. " +
            "Then: server gets instantiated without throwing"
    )
    void serverInstantiatedWithoutThrowing() {
        assertDoesNotThrow(
                () -> infrastructureObjects = new InfrastructureObjects(injector)
        );
    }

    @Test
    @DisplayName(
            "When: constructor called with invalid server options. " +
            "Then: throws IllegalArgumentException"
    )
    void assertThrowsAtInvalidOptions() {
        String[] options = {"hello", "world"};
        assertThrows(
                IllegalArgumentException.class,
                () -> infrastructureObjects = new InfrastructureObjects(injector, options)
        );
    }
}