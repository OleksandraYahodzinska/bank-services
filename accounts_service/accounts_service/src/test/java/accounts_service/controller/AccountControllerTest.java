package accounts_service.controller;

import accounts_service.dto.AccountDTO;
import accounts_service.dto.ClientDTO;
import accounts_service.model.*;
import accounts_service.service.BankService;
import accounts_service.service.PaymentClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private BankService bankService;

    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private AccountController accountController;

    @Test
    void getAccount_Success() {
        Account account = new Savings(1, 500.0);

        when(bankService.findAccount(1)).thenReturn(account);

        Account result = accountController.getAccount(1);

        assertNotNull(result);
        assertEquals(500.0, result.getBalance());
        assertEquals(1, result.getId());
    }

    @Test
    void getAllClients_Success() {
        List<Client> clients = List.of(new Client(1, "Sasha", "Y"));
        when(bankService.getClients()).thenReturn(clients);

        List<ClientDTO> result = accountController.getAllClients();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Sasha", result.get(0).getName());
    }

    @Test
    void addClient_Success() {
        ClientDTO dto = new ClientDTO(1, "Sasha", "Y");

        accountController.addClient(dto);

        verify(bankService).addClient(any(Client.class));
    }

    @Test
    void createAccount_Success() {
        AccountDTO dto = new AccountDTO(1, 100.0, "SAVINGS", null);

        accountController.createAccount(dto);

        verify(bankService).createSavings(1, 100.0);
    }

    @Test
    void deleteClient_Success() {
        when(paymentClient.getAccountInfo(1)).thenReturn("Balance: 100.0");
        when(bankService.findAccount(1)).thenReturn(new Savings(1, 100.0));

        accountController.deleteClient(1);

        verify(bankService).deleteClient(1);
    }

    @Test
    void updateClientName_Success() {
        accountController.updateClientName(1, "NewName");

        verify(bankService).updateClientName(1, "NewName");
    }
}