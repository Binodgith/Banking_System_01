package Models;

import java.util.Objects;

public class UserAccount {
    private String username;
    private String email;
    private String mobile;
    private String aadhar;
    private String name;
    private long accountno;
    private String password;
    private double balance;
    private boolean account_active;
    private int transaction_pin;


    public UserAccount(String username, String email, String mobile, String aadhar, String name, long accountno, double balance, boolean account_active) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.aadhar = aadhar;
        this.name = name;
        this.accountno = accountno;
        this.balance = balance;
        this.account_active = account_active;
    }

    public UserAccount(String username, String email, String mobile, String aadhar, String name, String password, int transaction_pin) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.aadhar = aadhar;
        this.name = name;
        this.password=password;
        this.transaction_pin=transaction_pin;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getName() {
        return name;
    }

    public long getAccountno() {
        return accountno;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isAccount_active() {
        return account_active;
    }






    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(username, that.username) || Objects.equals(email, that.email) || Objects.equals(mobile, that.mobile) || Objects.equals(aadhar, that.aadhar);
    }

    public String getPassword() {
        return password;
    }

    public int getTransaction_pin() {
        return transaction_pin;
    }
}


