package accounts_service.controller;

import accounts_service.model.Client;
import accounts_service.service.BankService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final BankService bankService;

    public ClientController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable int id) {
        return bankService.findClient(id);
    }

    @GetMapping
    public List<Client> getAllClients() {
        return bankService.getClients();
    }
}