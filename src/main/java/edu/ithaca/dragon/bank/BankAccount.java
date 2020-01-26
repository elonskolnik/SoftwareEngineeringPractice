package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * Should throw an InsuffiecientFundsException if amount is negative or larger than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException {
        balance -= amount;

    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        else if (email.indexOf('#') != -1 || email.indexOf(' ') != -1){
            return false;
        }
        else if (email.indexOf('.') >= email.length() - 2){
            return false;
        }
        else if (email.indexOf('.') == -1){
            return false;
        }
        else if (email.indexOf('-') == email.indexOf('@') - 1){
            return false;
        }
        else if (email.charAt(0) == '.'){
            return false;
        }
        else if (email.charAt(email.indexOf('.') + 1) == '.'){
            return false;
        }
        else {
            return true;
        }
    }

}
