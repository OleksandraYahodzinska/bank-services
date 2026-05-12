package accounts_service.service;

import accounts_service.model.Client;
import java.util.List;

public interface IClientManager {
    void addClient(Client client);
    void deleteClient(int id);
    void updateClientName(int id, String newName);
    Client findClient(int id);
    List<Client> getClients();
}