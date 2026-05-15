package ui_service.client;

import ui_service.dto.AccountDTO;
import ui_service.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "accounts-service", url = "http://accounts-service:8081")
public interface AccountClient {

    @GetMapping("/accounts/{id}")
    AccountDTO getAccount(@PathVariable("id") int id);

    @PostMapping("/accounts/clients")
    void addClient(@RequestBody ClientDTO client);

    @GetMapping("/accounts/clients")
    List<ClientDTO> getAllClients();

    @DeleteMapping("/accounts/clients/{id}")
    void deleteClient(@PathVariable("id") int id);

    @PutMapping("/accounts/clients/{id}/name")
    void updateClientName(@PathVariable("id") int id, @RequestParam("name") String name);

    @PostMapping("/accounts")
    void createAccount(@RequestBody AccountDTO account);

    @GetMapping("/accounts")
    List<AccountDTO> getAllAccounts();
}