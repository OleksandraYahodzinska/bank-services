package accounts_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "savings_accounts")
public class Savings extends Account {

    public Savings() {}

    public Savings(int id, double balance) {
        super(id, balance);
    }

    public void withdraw(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
        } else {
            System.out.println("Invalid amount");
        }
    }
}