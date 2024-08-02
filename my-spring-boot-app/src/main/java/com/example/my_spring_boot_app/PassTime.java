package com.example.my_spring_boot_app;

import java.util.ArrayList;
import java.util.List;

public class PassTime {
    private static final List<Bank> observers = new ArrayList<>();
    private static int currentTimePassed = 0;

    public static void passTime(int time) {
        notify(time);
        currentTimePassed += time;
    }

    public static void notify(int time) {
        observers.forEach(observer -> observer.passTime(time));
    }

    public static int getCurrentTime() {
        return currentTimePassed;
    }

    public static void registerObserver(Bank bank) {
        observers.add(bank);
    }

}
