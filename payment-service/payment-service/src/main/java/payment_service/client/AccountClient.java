package payment_service.client;

import payment_service.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "accounts-service", url = "http://accounts-service:8081")
public interface AccountClient {
    @GetMapping("/accounts/{id}")
    AccountDTO getAccount(@PathVariable("id") int id);

    @PutMapping("/accounts/{id}/balance")
    void updateBalance(@PathVariable("id") int id, @RequestParam("newBalance") double newBalance);
}