package com.example.my_spring_boot_app;

import java.math.BigDecimal;

public class Checking extends Account {

    Checking(String id, float apr) {
        super(id, apr);
        type = "checking";
    }
}
