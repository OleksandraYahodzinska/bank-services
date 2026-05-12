package accounts_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "credit_accounts")
public class Credit extends Account {
    private double creditLimit;

    public Credit() {}

    public Credit(int id, double balance, double creditLimit) {
        super(id, balance);
        this.creditLimit = creditLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void withdraw(double amount) {
        if (amount > 0 && (this.balance + creditLimit) >= amount) {
            this.balance -= amount;
        } else {
            System.out.println("Credit limit exceeded or invalid amount");
        }
    }
}