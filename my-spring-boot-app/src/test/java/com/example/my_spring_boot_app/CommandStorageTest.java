package com.example.my_spring_boot_app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
    public static final String COMMAND1 = "Create abc 12345678 1.0";
    public static final String COMMAND2 = "Deposit 1234e5678 1.0";
    CommandStorage commandStorage;

    @BeforeEach
    void setUp() {
        commandStorage = new CommandStorage();
    }

    @Test
    void check_starting_size_invalid_is_zero() {
        assertEquals(0, commandStorage.getInvalidCommands().size());
    }

    @Test
    void enter_invalid_create_command() {
        commandStorage.addToInvalidStorage(COMMAND1);
        assertEquals(1, commandStorage.getInvalidCommands().size());
    }

    @Test
    void enter_invalid_deposit_command() {

        commandStorage.addToInvalidStorage(COMMAND2);
        assertEquals(1, commandStorage.getInvalidCommands().size());
    }

    @Test
    void enter_many_commands() {
        commandStorage.addToInvalidStorage(COMMAND1);
        commandStorage.addToInvalidStorage(COMMAND2);
        assertEquals(2, commandStorage.getInvalidCommands().size());
    }
}
