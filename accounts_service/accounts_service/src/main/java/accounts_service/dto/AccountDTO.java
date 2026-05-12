package accounts_service.dto;

public class AccountDTO {
    private int clientId;
    private Double balance;
    private String type;
    private Double creditLimit;

    public AccountDTO() {}

    public AccountDTO(int clientId, Double balance, String type, Double creditLimit) {
        this.clientId = clientId;
        this.balance = balance;
        this.type = type;
        this.creditLimit = creditLimit;
    }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getCreditLimit() { return creditLimit; }
    public void setCreditLimit(Double creditLimit) { this.creditLimit = creditLimit; }
}