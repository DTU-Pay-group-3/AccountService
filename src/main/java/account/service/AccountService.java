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
        this.messageQueue.addHandler("GetDTUPayAccount", this::getDTUPayAccount);
        this.messageQueue.addHandler("RegisterAccountRequested", this::registerDTUPayAccount);
    }

    public DTUPayAccount getDTUPayAccount(Event e) {
        System.out.println("Event GetAllAccounts found");
        String id = e.getArgument(0, String.class);
        DTUPayAccount account = accounts.get(id);

        if (account == null) {
            DTUPayAccount emptyAccount = new DTUPayAccount("", "", "", "");
            emptyAccount.setId("");

            Event notFoundEvent = new Event("DTUPayAccountNotFound", new Object[]{ emptyAccount });
            messageQueue.publish(notFoundEvent);

            return emptyAccount;
        }

        Event event = new Event("DTUPayAccountReturned", new Object[] { account });
        messageQueue.publish(event);

        return account;
    }

    public DTUPayAccount registerDTUPayAccount(Event e) {
        System.out.println("Event RegisterAccountRequested found");
        DTUPayAccount newAccount = e.getArgument(0, DTUPayAccount.class);

        if (this.accounts.containsKey(newAccount.getId())) {
            DTUPayAccount emptyAccount = new DTUPayAccount("", "", "", "");
            emptyAccount.setId("");

            Event event = new Event("AccountAlreadyExists", new Object[] { emptyAccount });
            messageQueue.publish(event);
            return emptyAccount;
        }

        if (newAccount.getId().isBlank()) {
            newAccount.setId(UUID.randomUUID().toString());
        }

        accounts.put(newAccount.getId(), newAccount);

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
