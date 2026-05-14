package ui_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", url = "http://payment-service:8082")
public interface PaymentClient {

    @GetMapping("/payments/account-info/{id}")
    String getAccountInfo(@PathVariable("id") int id);

    @PostMapping("/payments/transfer")
    String transfer(@RequestParam("fromId") int fromId,
                    @RequestParam("toId") int toId,
                    @RequestParam("amount") double amount);

    @PostMapping("/payments/deposit")
    String deposit(@RequestParam("id") int id,
                   @RequestParam("amount") double amount);

    @PostMapping("/payments/withdraw")
    String withdraw(@RequestParam("id") int id,
                    @RequestParam("amount") double amount);
}