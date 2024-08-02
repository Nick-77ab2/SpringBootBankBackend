package com.example.my_spring_boot_app;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
    public static final String ID = "10454598";
    public static final String ID2 = "15679860";
    public static final String ID3 = "43520554";
    public static final float APR = 0.05f;
    public static final float APR2 = 0.07f;
    public static final float APR3 = 0.09f;
    Account checking;
    Account cd;
    Account savings;

    @BeforeEach
    void setUp() {
        checking = new Checking(ID, APR);
        savings = new Savings(ID2, APR2);
        cd = new CD(ID3, APR3, 1001);

    }

    @Test
    void checking_has_id_and_apr() {
        assertEquals(ID, checking.getId());
        assertEquals(APR, checking.getApr());
    }

    @Test
    void savings_has_id_and_apr() {
        assertEquals(ID2, savings.getId());
        assertEquals(APR2, savings.getApr());
    }

    @Test
    void cd_has_id_and_apr_and_balance() {
        assertEquals(ID3, cd.getId());
        assertEquals(APR3, cd.getApr());
        assertEquals(new BigDecimal("1001.00"), cd.getBalance());
    }

    @Test
    void check_account_types() {
        assertEquals("checking", checking.getAccountType());
        assertEquals("savings", savings.getAccountType());
        assertEquals("cd", cd.getAccountType());
    }

}
