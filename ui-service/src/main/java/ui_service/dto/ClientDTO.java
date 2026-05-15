package ui_service.dto;

public class ClientDTO {
    private int id;
    private String name;
    private String surname;
    private Double balance;
    private String accountType;

    public ClientDTO() {}

    public ClientDTO(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
}