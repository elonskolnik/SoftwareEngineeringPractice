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
     * Should throw an InsuffiecientFundsException if amount is larger than balance
     * Should do nothing if amount is negative
     */
    public void withdraw (double amount) throws InsufficientFundsException {
        if (amount < 0){
            balance = balance;
        }
        else if (balance < amount) {
            throw new InsufficientFundsException("Cannot withdraw an amount larger than you balance");
        }
        else{
            balance -= amount;
        }

    }

    /**
     *
     * @return true if the given email is valid
     */
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
    /**
     * Returns true if the given amount is positive and has two or less decimal points
     * Returns false otherwise
     */
    public static boolean isAmountValid(double amount){
        if(amount > 0){
            String amtString = Double.toString(amount);
            int pdIndex = amtString.indexOf('.');
            if(pdIndex == -1){
                return true;
            }
            else{
                if((amtString.length() - pdIndex) <= 3){
                    return true;
                }
            }
        }
        return false;
    }
}
