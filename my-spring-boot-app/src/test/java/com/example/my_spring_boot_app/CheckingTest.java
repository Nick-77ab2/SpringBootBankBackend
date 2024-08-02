package com.example.my_spring_boot_app;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {
    public static final String ID = "10454598";
    public static final float APR = 0.05f;
    Checking checking;

    @BeforeEach
    void setUp() {
        checking = new Checking(ID, APR);
    }

    @Test
    void checking_has_id_and_apr_balance_is_zero() {
        assertEquals(ID, checking.getId());
        assertEquals(APR, checking.getApr());
        assertEquals(new BigDecimal("0.00"), checking.getBalance());
    }
}
