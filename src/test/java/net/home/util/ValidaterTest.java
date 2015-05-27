package net.home.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidaterTest {

    @Test
    public void testValidateDate() {
        Validater.validateDate("20150527");;
    }

    @Test
    public void testValidateMoney() {
        Validater.validateMoney("+12.01");
    }

}
