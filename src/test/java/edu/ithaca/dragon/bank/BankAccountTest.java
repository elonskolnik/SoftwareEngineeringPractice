package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        //check positive balance with no decimal points
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance());

        //check positive balance with one decimal point
        bankAccount = new BankAccount("a@b.com", 145.5);
        assertEquals(145.5, bankAccount.getBalance());

        //check positive balance with two decimal points
        bankAccount = new BankAccount("a@b.com", 250.21);
        assertEquals(250.21, bankAccount.getBalance());
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

        //checks zero argument throws exception correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 200).withdraw(0));

        //check negative argument throws exception correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 200).withdraw(-42));

        //check more than two significant numbers argument throws exception correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 200).withdraw(4.252));
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
        //checks normal bankAccount
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());

        //check bankAccount with two decimal points
        bankAccount = new BankAccount("a@b.com", 200.54);
        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200.54, bankAccount.getBalance());

        //check for exceptions thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 0)); //zero balance
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100)); //negative balance
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.421)); //more than two significant digits
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

    @Test
    void depositTest(){
        //positive value less than current balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance());

        //positive value equal to current balance
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(200);
        assertEquals(400, bankAccount.getBalance());

        //positive value greater than equal balance
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(500);
        assertEquals(700, bankAccount.getBalance());

        //two deposits in a row
        bankAccount.deposit(300);
        assertEquals(1000, bankAccount.getBalance());

        //positive number, two decimal points
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(300.67);
        assertEquals(500.67, bankAccount.getBalance());

        //zero value
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 200).deposit(0));

        //negative value
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 200).deposit(-50));

        //value with more than two significant figures
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 200).deposit(50.263));
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        //bankAccountA balance greater than bankAccountB
        BankAccount bankAccountA = new BankAccount("a@b.com", 400);
        BankAccount bankAccountB = new BankAccount("a@c.com", 200);
        bankAccountA.transfer(bankAccountB, 300);
        assertEquals(100, bankAccountA.getBalance());
        assertEquals(500, bankAccountB.getBalance());

        //account balances equal
        bankAccountA = new BankAccount("a@b.com", 200);
        bankAccountB = new BankAccount("a@c.com", 200);
        bankAccountA.transfer(bankAccountB, 100);
        assertEquals(100, bankAccountA.getBalance());
        assertEquals(300, bankAccountB.getBalance());

        //two transfers in a row
        bankAccountA.transfer(bankAccountB, 50);
        assertEquals(50, bankAccountA.getBalance());
        assertEquals(350, bankAccountB.getBalance());

        //bankAccountB balance greater than bankAccountA
        bankAccountA = new BankAccount("a@b.com", 100);
        bankAccountB = new BankAccount("a@c.com", 800);
        bankAccountA.transfer(bankAccountB, 50);
        assertEquals(50, bankAccountA.getBalance());
        assertEquals(850, bankAccountB.getBalance());

        //transfer of two decimal points
        bankAccountA = new BankAccount("a@b.com", 200.56);
        bankAccountB = new BankAccount("a@c.com", 420.44);
        bankAccountA.transfer(bankAccountB, 100.56);
        assertEquals(100, bankAccountA.getBalance());
        assertEquals(521, bankAccountB.getBalance());

        //transfer equal to bankAccountA balance
        bankAccountA = new BankAccount("a@b.com", 300.55);
        bankAccountB = new BankAccount("a@c.com", 200);
        bankAccountA.transfer(bankAccountB, 300.55);
        assertEquals(0, bankAccountA.getBalance());
        assertEquals(500.55, bankAccountB.getBalance());

        //transfer greater than bankAccountA balance
        assertThrows(InsufficientFundsException.class, ()-> new BankAccount("a@b.com", 200).transfer(new BankAccount("a@c.com", 500),250));

        //transfer of zero dollars
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 445.32).transfer(new BankAccount("a@c.com", 200), 0));

        //transfer of negative value
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 300).transfer(new BankAccount("a@c.com", 300), -24));

        //transfer of more than two decimal points
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 63.45).transfer(new BankAccount("a@c.com", 22.42), 25.452));
    }

}