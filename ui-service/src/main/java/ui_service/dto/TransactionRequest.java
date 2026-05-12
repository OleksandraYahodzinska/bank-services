package ui_service.dto;

public class TransactionRequest {
    private int fromId;
    private int toId;
    private double amount;

    public TransactionRequest() {}

    public TransactionRequest(int fromId, int toId, double amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
    }

    public int getFromId() { return fromId; }
    public void setFromId(int fromId) { this.fromId = fromId; }
    public int getToId() { return toId; }
    public void setToId(int toId) { this.toId = toId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}