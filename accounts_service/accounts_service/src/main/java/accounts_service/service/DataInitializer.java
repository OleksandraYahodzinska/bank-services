package accounts_service.service;

import accounts_service.model.Client;
import accounts_service.model.Savings;
import accounts_service.repository.AccountRepository;
import accounts_service.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public DataInitializer(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {
        Client sasha = new Client(1, "Sasha", "Yahodzinska");
        clientRepository.save(sasha);

        accountRepository.save(new Savings(1, 1000.0));

        Client friend = new Client(2, "Mashka", "Romashka");
        clientRepository.save(friend);

        accountRepository.save(new Savings(2, 500.0));

        System.out.println("---Test data initialized---");
    }
}