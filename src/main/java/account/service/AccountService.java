package account.service;

import messaging.Event;
import messaging.MessageQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountService {

    MessageQueue messageQueue;
    HashMap<String, DTUPayAccount> accounts = new HashMap<>();

    public AccountService(MessageQueue mq) {
        messageQueue = mq;
        this.messageQueue.addHandler("GetAllAccountsRequested", this::getAllDTUPayAccounts);
        this.messageQueue.addHandler("GetDTUPayAccount", this::getDTUPayAccount);
        this.messageQueue.addHandler("RegisterAccountRequested", this::registerDTUPayAccount);
    }

    public Map<String, DTUPayAccount> getAllDTUPayAccounts(Event e) {
        System.out.println("Event GetAllAccounts found");
        Event event = new Event("AllAccountsReturned", new Object[] { accounts });
        messageQueue.publish(event);
        return accounts;
    }

    public DTUPayAccount getDTUPayAccount(Event e) {
        System.out.println("Event GetAllAccounts found");
        String accNumber = e.getArgument(0, String.class);
        DTUPayAccount account = accounts.get(accNumber);

        Event event = new Event("DTUPayAccountReturned", new Object[] { account });
        messageQueue.publish(event);

        return account;
    }

    public DTUPayAccount registerDTUPayAccount(Event e) {
        System.out.println("Event RegisterAccount found");
        DTUPayAccount newAccount = e.getArgument(0, DTUPayAccount.class);

        if (this.accounts.containsKey(newAccount.getId())) {
            Event event = new Event("AccountAlreadyExists", new Object[] { new DTUPayAccount() });
            messageQueue.publish(event);
            return newAccount;
        }

        if (newAccount.getId().isBlank()) {
            newAccount.setId(UUID.randomUUID().toString());
        }

        accounts.put(newAccount.getAccountNumber(), newAccount);

        Event event = new Event("AccountCreated", new Object[] { newAccount });
        messageQueue.publish(event);

        return newAccount;
    }

    public HashMap<String, DTUPayAccount> getAccounts() {
        return accounts;
    }

    public void addAccount(DTUPayAccount account) {
        this.accounts.put(account.getId(), account);
    }
}
