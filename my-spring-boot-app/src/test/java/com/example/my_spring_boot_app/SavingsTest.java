package com.example.my_spring_boot_app;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {
    public static final String ID = "10454598";
    public static final float APR = 0.05f;
    Savings saving;

    @BeforeEach
    void setUp() {
        saving = new Savings(ID, APR);
    }

    @Test
    void savings_has_id_and_apr_and_balance_is_zero() {
        assertEquals(ID, saving.getAccid());
        assertEquals(APR, saving.getApr());
        assertEquals(new BigDecimal("0.00"), saving.getBalance());
    }
}