package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        //check large positive balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance());

        //check small positive balance
        bankAccount = new BankAccount("a@b.com", 5);
        assertEquals(5, bankAccount.getBalance());

        //check zero balance
        bankAccount = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount.getBalance());

        //check negative balance?
        bankAccount = new BankAccount("a@b.com", -200);
        assertEquals(-200, bankAccount.getBalance());

    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        //amount is positive and smaller than account balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());

        //withdraw called twice in a row; amount is positive and smaller than remaining account balance
        bankAccount.withdraw(50);
        assertEquals(50, bankAccount.getBalance());

        //amount is positive and equal to account balance (edge case)
        bankAccount = new BankAccount("a@b.com", 300);
        bankAccount.withdraw(300);
        assertEquals(0, bankAccount.getBalance());

        //amount is positive but slightly larger than account balance
        assertThrows(InsufficientFundsException.class, () -> new BankAccount("a@b.com", 200).withdraw(201));

        //amount is positive and much larger than account balance
        assertThrows(InsufficientFundsException.class, () -> new BankAccount("a@b.com", 200).withdraw(600));

        //amount is negative and abs val smaller than account balance
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(-1);
        assertEquals(200, bankAccount.getBalance());

        //amount is negative and abs val equal to account balance
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(-200);
        assertEquals(200, bankAccount.getBalance());

        //amount is negative and abs val larger than account balance
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(-500);
        assertEquals(200, bankAccount.getBalance());
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

    @Test
    void isAmountValidTest() {
        assertFalse(BankAccount.isAmountValid(0)); //zero value with no decimal points
        assertFalse(BankAccount.isAmountValid(0.0)); //zero value with one decimal point
        assertFalse(BankAccount.isAmountValid(0.00)); //zero value with two decimal points
        assertFalse(BankAccount.isAmountValid(0.000)); //zero value with three decimal points
        assertFalse(BankAccount.isAmountValid(0.0000000000)); //zero value with ten decimal points

        assertTrue(BankAccount.isAmountValid(50)); //positive value with no decimal points
        assertTrue(BankAccount.isAmountValid(50.3)); //positive value with one decimal point
        assertTrue(BankAccount.isAmountValid(50.0)); //positive value with one decimal point that is a zero
        assertTrue(BankAccount.isAmountValid(50.47)); //positive value with two decimal points
        assertTrue(BankAccount.isAmountValid(50.00)); //positive value with two decimal points and zero

        assertFalse(BankAccount.isAmountValid(50.533)); //positive value with three decimal points
        assertFalse(BankAccount.isAmountValid(50.3462647845)); //positive value with ten decimal points

        assertFalse(BankAccount.isAmountValid(-43)); //negative value with no decimal points
        assertFalse(BankAccount.isAmountValid(-289.3)); //negative value with one decimal point
        assertFalse(BankAccount.isAmountValid(-23.53)); //negative value with two decimal points
        assertFalse(BankAccount.isAmountValid(-6323.552)); //negative value with three decimal points
    }

}