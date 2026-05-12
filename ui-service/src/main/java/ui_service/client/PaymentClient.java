package ui_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", url = "http://localhost:8082")
public interface PaymentClient {

    //THIS
    @GetMapping("/payments/account-info/{id}")
    String getAccountInfo(@PathVariable("id") int id);

    @GetMapping("/payments/transfer")
    String transfer(@RequestParam("from") int from, @RequestParam("to") int to, @RequestParam("amount") double amount);

    @GetMapping("/payments/deposit")
    String deposit(@RequestParam("id") int id,
                   @RequestParam("amount") double amount);

    @GetMapping("/payments/withdraw")
    String withdraw(@RequestParam("id") int id,
                    @RequestParam("amount") double amount);
}