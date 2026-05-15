package payment_service.controller;

import org.springframework.web.bind.annotation.*;
import payment_service.client.AccountClient;
import payment_service.dto.AccountDTO;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final AccountClient accountClient;

    public PaymentController(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam int fromId, @RequestParam int toId, @RequestParam double amount) {
        AccountDTO source = accountClient.getAccount(fromId);
        AccountDTO target = accountClient.getAccount(toId);

        if (source == null) return "Error: sender account not found";
        if (target == null) return "Error: receiver account not found";

        double sourceAvailableFunds = source.getBalance() + (source.getCreditLimit() != null ? source.getCreditLimit() : 0);

        if (sourceAvailableFunds < amount) {
            return String.format("Error: not enough money. Available: %.2f, attempted: %.2f",
                    sourceAvailableFunds, amount);
        }

        accountClient.updateBalance(fromId, source.getBalance() - amount);
        accountClient.updateBalance(toId, target.getBalance() + amount);

        return "Success, transferred " + amount + " from ID " + fromId + " to ID " + toId;
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam int id, @RequestParam double amount) {
        AccountDTO acc = accountClient.getAccount(id);
        if (acc == null) return "Error: account not found";
        if (amount <= 0) return "Error: amount must be positive";

        double newBalance = acc.getBalance() + amount;
        accountClient.updateBalance(id, newBalance);

        return "Successfully deposited " + amount + ". New balance: " + newBalance;
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam int id, @RequestParam double amount) {
        AccountDTO acc = accountClient.getAccount(id);
        if (acc == null) return "Error: account not found";
        if (amount <= 0) return "Error: amount must be positive";

        double availableFunds = acc.getBalance() + (acc.getCreditLimit() != null ? acc.getCreditLimit() : 0);

        if (availableFunds < amount) {
            return "Error: not enough money";
        }

        double newBalance = acc.getBalance() - amount;
        accountClient.updateBalance(id, newBalance);

        return "Successfully withdrawn " + amount + ". New balance: " + newBalance;
    }

    @GetMapping("/account-info/{id}")
    public String getAccountInfo(@PathVariable int id) {
        AccountDTO acc = accountClient.getAccount(id);
        if (acc == null) return "Error: account not found";

        double total = acc.getBalance() + (acc.getCreditLimit() != null ? acc.getCreditLimit() : 0);
        return String.format("Balance: %.2f | Limit: %.2f", acc.getBalance(), total);
    }
}