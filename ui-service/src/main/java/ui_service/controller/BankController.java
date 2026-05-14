package ui_service.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

        try {
            model.addAttribute("clients", accountClient.getAllClients());
        } catch (Exception e) {
            model.addAttribute("message", "❌ Connection error: Accounts Service is offline.");
        }
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
    public String createClient(@ModelAttribute ClientDTO client, HttpSession session, RedirectAttributes ra) {
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            try {
                accountClient.addClient(client);
                ra.addFlashAttribute("message", "✅ Client created successfully!");
            } catch (Exception e) {
                ra.addFlashAttribute("message", "❌ Error: Could not create client.");
            }
        }
        return "redirect:/bank/dashboard";
    }

    @PostMapping("/accounts/create")
    public String createAccount(@ModelAttribute AccountDTO account, HttpSession session, RedirectAttributes ra) {
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            try {
                accountClient.createAccount(account);
                ra.addFlashAttribute("message", "✅ Account opened successfully!");
            } catch (Exception e) {
                ra.addFlashAttribute("message", "❌ Error: Account opening failed.");
            }
        }
        return "redirect:/bank/dashboard";
    }

    @PostMapping("/clients/delete")
    public String deleteClient(@RequestParam int id, HttpSession session, RedirectAttributes ra) {
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            try {
                accountClient.deleteClient(id);
                ra.addFlashAttribute("message", "✅ Client deleted successfully.");
            } catch (Exception e) {
                ra.addFlashAttribute("message", "❌ Cannot delete: Client might have active accounts or connection failed.");
            }
        }
        return "redirect:/bank/dashboard";
    }

    @PostMapping("/transfer")
    public String doTransfer(@RequestParam int fromId,
                             @RequestParam int toId,
                             @RequestParam double amount,
                             RedirectAttributes ra) {
        try {
            String result = paymentClient.transfer(fromId, toId, amount);
            ra.addFlashAttribute("message", "✅ " + result);
        } catch (Exception e) {
            ra.addFlashAttribute("message", "❌ Transaction failed: Check if IDs exist and balance is sufficient.");
        }
        return "redirect:/bank/dashboard";
    }
}