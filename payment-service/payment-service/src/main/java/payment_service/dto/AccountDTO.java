package payment_service.dto; // перевір свій пакет

public class AccountDTO {
    private int id;
    private double balance;
    private String type;
    private Double creditLimit;

    public AccountDTO() {}

    public AccountDTO(int id, double balance, String type, Double creditLimit) {
        this.id = id;
        this.balance = balance;
        this.type = type;
        this.creditLimit = creditLimit;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getCreditLimit() { return creditLimit; }
    public void setCreditLimit(Double creditLimit) { this.creditLimit = creditLimit; }
}