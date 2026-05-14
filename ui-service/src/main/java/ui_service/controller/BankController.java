package ui_service.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ui_service.client.AccountClient;
import ui_service.client.PaymentClient;
import ui_service.dto.AccountDTO;
import ui_service.dto.ClientDTO;

@Controller
@RequestMapping("/bank")
public class BankController {

    private final AccountClient accountClient;
    private final PaymentClient paymentClient;

    public BankController(AccountClient accountClient, PaymentClient paymentClient) {
        this.accountClient = accountClient;
        this.paymentClient = paymentClient;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        model.addAttribute("isAdmin", isAdmin != null && isAdmin);
        model.addAttribute("clients", accountClient.getAllClients());
        return "dashboard";
    }

    @PostMapping("/auth")
    public String authenticate(@RequestParam String apiKey, HttpSession session) {
        if ("ADMIN123".equals(apiKey)) {
            session.setAttribute("isAdmin", true);
        } else {
            session.setAttribute("isAdmin", false);
        }
        return "redirect:/bank/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/bank/dashboard";
    }

    @PostMapping("/clients/create")
    public String createClient(@ModelAttribute ClientDTO client, HttpSession session) {
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            accountClient.addClient(client);
        }
        return "redirect:/bank/dashboard";
    }

    @PostMapping("/accounts/create")
    public String createAccount(@ModelAttribute AccountDTO account, HttpSession session) {
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            accountClient.createAccount(account);
        }
        return "redirect:/bank/dashboard";
    }

    @PostMapping("/clients/delete")
    public String deleteClient(@RequestParam int id, HttpSession session) {
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            accountClient.deleteClient(id);
        }
        return "redirect:/bank/dashboard";
    }

    @PostMapping("/transfer")
    public String doTransfer(@RequestParam int fromId,
                             @RequestParam int toId,
                             @RequestParam double amount,
                             Model model) {
        String result = paymentClient.transfer(fromId, toId, amount);
        model.addAttribute("message", result);
        return "redirect:/bank/dashboard";
    }
}