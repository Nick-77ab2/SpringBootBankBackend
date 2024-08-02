package com.example.my_spring_boot_app;

import java.util.List;

public class MasterControl {
    protected Bank bank;
    CommandValidator commandValidator;
    CommandProcessor commandProcessor;
    CommandStorage commandStorage;

    public MasterControl(Bank bank, CommandValidator commandValidator, CommandProcessor commandProcessor, CommandStorage commandStorage) {
        this.bank = bank;
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;

    }

    public List<String> start(List<String> command) {
        for (String input : command) {
            if (commandValidator.calculateValidator(input)) {
                commandProcessor.calculateProcessor(input);
            } else {
                commandStorage.addToInvalidStorage(input);
            }
        }
        return commandStorage.getInvalidCommands();
    }
}