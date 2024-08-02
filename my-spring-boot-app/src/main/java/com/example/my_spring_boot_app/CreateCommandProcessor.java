package com.example.my_spring_boot_app;

public class CreateCommandProcessor extends CommandProcessor {

    public CreateCommandProcessor(Bank bank) {
        super(bank);
    }

    public void processInput(String[] splitInput) {
        splitInput = removeCommandFromInput(splitInput);
        String id = splitInput[1];
        String type = splitInput[0];
        float apr = Float.parseFloat(splitInput[2]);
        if (type.equals("checking")) {
            Account checking = new Checking(id, apr);
            bank.addAccount(id, checking);
        } else if (type.equals("savings")) {
            Account savings = new Savings(id, apr);
            bank.addAccount(id, savings);
        } else {
            double deposit = Integer.parseInt(splitInput[3]);
            Account cd = new CD(id, apr, deposit);
            bank.addAccount(id, cd);
        }

    }
}
