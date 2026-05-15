package accounts_service.controller;

import accounts_service.service.PaymentClient;
import accounts_service.dto.AccountDTO;
import accounts_service.dto.ClientDTO;
import accounts_service.model.Account;
import accounts_service.model.Client;
import accounts_service.service.BankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final BankService bankService;
    private final PaymentClient paymentClient;

    public AccountController(BankService bankService, PaymentClient paymentClient) {
        this.bankService = bankService;
        this.paymentClient = paymentClient;
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable int id) {
        return bankService.findAccount(id);
    }

    @PutMapping("/{id}/balance")
    public void updateBalance(@PathVariable int id, @RequestParam double newBalance) {
        Account account = bankService.findAccount(id);
        if (account != null) {
            account.setBalance(newBalance);
            bankService.saveAccount(account);
        }
    }

    @PostMapping("/clients")
    public void addClient(@RequestBody ClientDTO dto) {
        bankService.addClient(new Client(dto.getId(), dto.getName(), dto.getSurname()));
    }

    @GetMapping("/clients")
    public List<ClientDTO> getAllClients() {
        return bankService.getClients().stream()
                .map(c -> new ClientDTO(c.getId(), c.getName(), c.getSurname()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/clients/{id}")
    public String deleteClient(@PathVariable int id) {
        String paymentStatus = paymentClient.getAccountInfo(id);

        if (paymentStatus != null && !paymentStatus.contains("Error")) {
            Account account = bankService.findAccount(id);

            if (account != null && account.getBalance() < 0) {
                return "Can't delete client: account has a negative balance";
            }
        }
        bankService.deleteClient(id);
        return "Client with ID " + id + " was successfully deleted";
    }

    @PutMapping("/clients/{id}/name")
    public void updateClientName(@PathVariable int id, @RequestParam String name) {
        bankService.updateClientName(id, name);
    }

    @GetMapping("/clients/{id}")
    public ClientDTO findClient(@PathVariable int id) {
        Client c = bankService.findClient(id);
        return (c != null) ? new ClientDTO(c.getId(), c.getName(), c.getSurname()) : null;
    }

    @PostMapping
    public void createAccount(@RequestBody AccountDTO dto) {
        System.out.println("Спроба створення рахунку для клієнта: " + dto.getClientId());

        if (dto.getType() != null && dto.getType().equalsIgnoreCase("SAVINGS")) {
            bankService.createSavings(dto.getClientId(), dto.getBalance());
            System.out.println("Створено SAVINGS рахунок");
        } else {
            double limit = (dto.getCreditLimit() != null) ? dto.getCreditLimit() : 0.0;
            bankService.createCredit(dto.getClientId(), dto.getBalance(), limit);
            System.out.println("Створено CREDIT рахунок з лімітом: " + limit);
        }
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return bankService.getAccounts();
    }
}