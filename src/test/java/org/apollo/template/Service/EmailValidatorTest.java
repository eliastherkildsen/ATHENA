package org.apollo.template.Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    @Test
    public void testEmailInvalid(){

        String email = "testemail123@esav370.dk";
        boolean expected = false;
        boolean actual = EmailValidator.validateEmail(email);
        assertEquals(expected, actual);
    }

    @Test
    public void testEmailInvalid2(){

        String email = "testemail123@easv365.ck";
        boolean expected = false;
        boolean actual = EmailValidator.validateEmail(email);
        assertEquals(expected, actual);
    }

    @Test
    public void testEmailInvalid3(){

        String email = "@easv365.ck";
        boolean expected = false;
        boolean actual = EmailValidator.validateEmail(email);
        assertEquals(expected, actual);
    }

    @Test
    public void testEmailValid(){

        String email = "testemail123@easv365.dk";
        boolean expected = true;
        boolean actual = EmailValidator.validateEmail(email);
        assertEquals(expected, actual);
    }



}