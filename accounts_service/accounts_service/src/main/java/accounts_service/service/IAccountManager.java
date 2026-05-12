package accounts_service.service;

import java.util.List;
import accounts_service.model.Account;

public interface IAccountManager {
    void createSavings(int var1, double var2);
    void createCredit(int var1, double var2, double var4);
    Account findAccount(int var1);
    List<Account> getAccounts();
}
