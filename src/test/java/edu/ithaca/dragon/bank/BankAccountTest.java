package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid("a@b.com")); //This is just a basic correct email
        assertFalse(BankAccount.isEmailValid("")); //Checks for an empty string

        assertTrue(BankAccount.isEmailValid("a-b@b.com")); //Checks that hyphens are ok as long as they're not right before the @, border case
        assertTrue(BankAccount.isEmailValid("a.b@b.com")); //Checks that periods before the .com are ok, as long as they aren't right before the @, border case
        assertTrue(BankAccount.isEmailValid("a_d@b.com")); //Checks that underscores are ok
        assertTrue(BankAccount.isEmailValid("a@b.cc")); //Checks that having 2 characters after the last period is ok, border case
        assertTrue(BankAccount.isEmailValid("a@b-c.com"));  //Checks that a hyphen in  domain name is ok
        assertTrue(BankAccount.isEmailValid("a@b.org")); //Checks that a .org is ok

        assertFalse(BankAccount.isEmailValid("a-@b.com"));  //Checks for hyphens right before the @, border case
        assertFalse(BankAccount.isEmailValid("a..b@b.com")); //Checks for multiple periods in a row in username
        assertFalse(BankAccount.isEmailValid(".a@b.com")); //Checks for a leading  period
        assertFalse(BankAccount.isEmailValid("a#b@b.com")); //Checks for # in the username
        assertFalse(BankAccount.isEmailValid("a@b.c")); //Checks for only having one character after the last period, border case
        assertFalse(BankAccount.isEmailValid("a@b#c.com")); //Checks for # in the server name
        assertFalse(BankAccount.isEmailValid("a@b")); //Checks for a lack of .com, .org, etc. extension
        assertFalse(BankAccount.isEmailValid("a@b..com")); //Checks for multiple periods in a row in server name
    }
    /*
    The only border case I can think of that's missing is having no or only one character after the  last period,
    while there is another period in the address, for example, a.b@c.c, I think my current isEmailValid would fail
    that test, due to indexOf('.') returning  the index of the first period and not the last.
     */

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}