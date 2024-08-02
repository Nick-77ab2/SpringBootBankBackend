package com.example.my_spring_boot_app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

    MasterControl masterControl;
    List<String> command;

    @BeforeEach
    void setUp() {
        command = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(bank, new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage());
    }

    private void assertOnSingleCommand(String input, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(input, actual.get(0));
    }

    @Test
    void create_spelled_wrong_in_command_is_invalid() {
        command.add("creat checking 12345678 1.0");

        List<String> actual = masterControl.start(command);

        assertOnSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void deposit_spelled_wrong_in_command_is_invalid() {
        command.add("deopsit 12345678 565");

        List<String> actual = masterControl.start(command);

        assertOnSingleCommand("deopsit 12345678 565", actual);
    }

    @Test
    void two_commands_typos_invalid() {
        command.add("creat checking 12345678 1.0");
        command.add("deopsit 12345678 565");

        List<String> actual = masterControl.start(command);

        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("deopsit 12345678 565", actual.get(1));
    }

    @Test
    void cannot_create_accounts_with_same_ID() {
        command.add("create checking 12345678 1.0");
        command.add("create checking 12345678 1.0");

        List<String> actual = masterControl.start(command);

        assertOnSingleCommand("create checking 12345678 1.0", actual);
    }
}
