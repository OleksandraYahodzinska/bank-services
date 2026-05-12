package accounts_service.service;

import accounts_service.model.*;
import accounts_service.repository.AccountRepository;
import accounts_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService implements IClientManager, IAccountManager, IReportService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BankService(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void addClient(Client client) {
        if (clientRepository.existsById(client.getId())) {
            System.out.println("Client already exists in database");
            return;
        }
        clientRepository.save(client);
        System.out.println("Client saved to database");
    }

    @Override
    public void deleteClient(int id) {
        if (!clientRepository.existsById(id)) {
            System.out.println("Client not found");
            return;
        }
        clientRepository.deleteById(id);
        System.out.println("Client and their accounts deleted from database");
    }

    @Override
    public void updateClientName(int id, String newName) {
        clientRepository.findById(id).ifPresentOrElse(client -> {
            client.setName(newName);
            clientRepository.save(client);
            System.out.println("Name updated in database");
        }, () -> System.out.println("Client not found"));
    }

    @Override
    public Client findClient(int id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public void createSavings(int id, double balance) {
        if (!clientRepository.existsById(id)) {
            System.out.println("Can't create account because client does not exist");
            return;
        }
        accountRepository.save(new Savings(id, balance));
        System.out.println("Savings account saved to database");
    }

    @Override
    public void createCredit(int id, double balance, double limit) {
        if (!clientRepository.existsById(id)) {
            System.out.println("Can't create account because client does not exist");
            return;
        }
        accountRepository.save(new Credit(id, balance, limit));
        System.out.println("Credit account saved to database");
    }

    @Override
    public Account findAccount(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void printTotalBalance() {
        double total = accountRepository.findAll().stream()
                .mapToDouble(Account::getBalance)
                .sum();
        System.out.println("Total bank balance: " + total);
    }

    @Override
    public void printAudit() {
        System.out.println("Total clients in DB: " + clientRepository.count());
        System.out.println("Total accounts in DB: " + accountRepository.count());
    }

    @Override
    public void showClients() {
        List<Client> clients = clientRepository.findAll();
        clients.sort(null);

        for (Client c : clients) {
            Account acc = accountRepository.findById(c.getId()).orElse(null);
            double balance = (acc != null) ? acc.getBalance() : 0;

            System.out.println("ID: " + c.getId() + " | " + c.getName() + " " + c.getSurname() +
                    " | Balance: " + balance);
        }
    }
}