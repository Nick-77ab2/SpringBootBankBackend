package com.example.my_spring_boot_app;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {
    public static final String ID = "10454598";
    public static final float APR = 0.05f;
    public static final double DEPOSIT = 1001.00;
    CD cd;

    @BeforeEach
    void setUp() {
        cd = new CD(ID, APR, DEPOSIT);
    }

    @Test
    void cd_has_id_and_apr_and_balance() {
        assertEquals(ID, cd.getId());
        assertEquals(APR, cd.getApr());
        assertEquals(new BigDecimal("1001.00"), cd.getBalance());
    }
}
