package data;

public class BankAccount {
    private int id;
    private String accountNumber;
    private String jmbg;
    private double balance;

    public BankAccount(int id, String accountNumber, String jmbg, double balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.jmbg = jmbg;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
