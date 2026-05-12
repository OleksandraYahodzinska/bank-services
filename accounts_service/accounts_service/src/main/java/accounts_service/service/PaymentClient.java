package accounts_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment-service", url = "http://localhost:8082")
public interface PaymentClient {

    @GetMapping("/payments/account-info/{id}")
    String getAccountInfo(@PathVariable("id") int id);
}