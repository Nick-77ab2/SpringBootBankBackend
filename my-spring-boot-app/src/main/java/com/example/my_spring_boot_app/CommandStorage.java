package com.example.my_spring_boot_app;

import java.util.ArrayList;

public class CommandStorage {
    private ArrayList<String> invalidStorage;

    protected CommandStorage() {
        invalidStorage = new ArrayList<>();
    }

    public void addToInvalidStorage(String input) {
        invalidStorage.add(input);
    }

    public ArrayList<String> getInvalidCommands() {
        return invalidStorage;
    }
}
