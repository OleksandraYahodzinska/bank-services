package payment_service.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import payment_service.client.AccountClient;
import payment_service.dto.AccountDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private PaymentController paymentController;

    @Test
    void deposit_Success() {
        AccountDTO acc = new AccountDTO(1, 100.0, "SAVINGS", null);
        when(accountClient.getAccount(1)).thenReturn(acc);

        String result = paymentController.deposit(1, 50.0);

        assertTrue(result.contains("Successfully deposited 50.0"));
        verify(accountClient).updateBalance(1, 150.0);
    }

    @Test
    void withdraw_Savings_InsufficientFunds() {
        AccountDTO acc = new AccountDTO(1, 100.0, "SAVINGS", null);
        when(accountClient.getAccount(1)).thenReturn(acc);

        String result = paymentController.withdraw(1, 200.0);

        assertTrue(result.contains("Error: not enough money"));
        verify(accountClient, never()).updateBalance(anyInt(), anyDouble());
    }

    @Test
    void withdraw_Credit_WithinLimit_Success() {
        AccountDTO acc = new AccountDTO(1, 100.0, "CREDIT", 1000.0);
        when(accountClient.getAccount(1)).thenReturn(acc);

        String result = paymentController.withdraw(1, 500.0);

        assertTrue(result.contains("Successfully withdrawn 500.0"));

        verify(accountClient).updateBalance(1, -400.0);
    }

    @Test
    void transfer_BetweenAccounts_Success() {
        AccountDTO from = new AccountDTO(1, 500.0, "SAVINGS", null);
        AccountDTO to = new AccountDTO(2, 100.0, "SAVINGS", null);

        when(accountClient.getAccount(1)).thenReturn(from);
        when(accountClient.getAccount(2)).thenReturn(to);

        String result = paymentController.transfer(1, 2, 200.0);

        assertTrue(result.contains("Success"));
        verify(accountClient).updateBalance(1, 300.0);
        verify(accountClient).updateBalance(2, 300.0);
    }

    @Test
    void withdraw_AccountNotFound() {
        when(accountClient.getAccount(999)).thenReturn(null);

        String result = paymentController.withdraw(999, 100.0);

        assertEquals("Error: account not found", result);
    }
}