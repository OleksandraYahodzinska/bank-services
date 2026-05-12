package accounts_service.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account implements Serializable {

    @Id
    protected int id;

    protected double balance;

    public Account() {
    }

    public Account(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            System.out.println("Deposit amount must be positive");
        }
    }
}