package model;

public class Account {
    private int id;
    private String owner_name;
    private int balance;

    public Account(int id, String owner_name, int balance) {
        this.id = id;
        this.owner_name = owner_name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return owner_name;
    }

    public int getBalance() {
        return balance;
    }
}
