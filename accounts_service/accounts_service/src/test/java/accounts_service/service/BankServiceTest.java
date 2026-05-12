package accounts_service.service;

import accounts_service.model.*;
import accounts_service.repository.AccountRepository;
import accounts_service.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    void addClient_Success() {
        Client client = new Client(1, "Sasha", "Yahodzinska");
        when(clientRepository.existsById(1)).thenReturn(false);

        bankService.addClient(client);

        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void addClient_AlreadyExists_ShouldNotSave() {
        Client client = new Client(1, "Sasha", "Yahodzinska");
        when(clientRepository.existsById(1)).thenReturn(true);

        bankService.addClient(client);

        verify(clientRepository, never()).save(any());
    }

    @Test
    void updateClientName_Success() {
        Client client = new Client(1, "OldName", "Surname");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        bankService.updateClientName(1, "NewName");

        assertEquals("NewName", client.getName());
        verify(clientRepository).save(client);
    }

    @Test
    void deleteClient_Success() {
        when(clientRepository.existsById(1)).thenReturn(true);

        bankService.deleteClient(1);

        verify(clientRepository).deleteById(1);
    }

    @Test
    void createSavings_Success() {
        when(clientRepository.existsById(1)).thenReturn(true);

        bankService.createSavings(1, 1000.0);

        verify(accountRepository).save(any(Savings.class));
    }

    @Test
    void createCredit_Failure_WhenClientMissing() {
        when(clientRepository.existsById(99)).thenReturn(false);

        bankService.createCredit(99, 100.0, 500.0);

        verify(accountRepository, never()).save(any());
    }

    @Test
    void findAccount_ReturnsNullIfNotFound() {
        when(accountRepository.findById(100)).thenReturn(Optional.empty());

        Account result = bankService.findAccount(100);

        assertNull(result);
    }

    @Test
    void printTotalBalance_ShouldSumAllAccounts() {
        List<Account> accounts = List.of(
                new Savings(1, 500.0),
                new Credit(2, 300.0, 1000.0)
        );

        when(accountRepository.findAll()).thenReturn(accounts);

        bankService.printTotalBalance();

        verify(accountRepository).findAll();
    }

    @Test
    void showClients_ShouldSortAndPrint() {
        Client c1 = new Client(1, "Zhenya", "Test");
        Client c2 = new Client(2, "Alex", "Test");

        List<Client> clients = new java.util.ArrayList<>(List.of(c1, c2));

        when(clientRepository.findAll()).thenReturn(clients);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());

        bankService.showClients();

        verify(clientRepository).findAll();
    }
}