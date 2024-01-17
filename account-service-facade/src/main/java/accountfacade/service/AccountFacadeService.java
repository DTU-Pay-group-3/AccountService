package accountfacade.service;

import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

public class AccountFacadeService {
    private MessageQueue queue;
    private CompletableFuture<DTUPayAccount> registeredAccount;
    private CompletableFuture<DTUPayAccount> returnedAccount;

    public AccountFacadeService(MessageQueue q) {
        queue = q;
        queue.addHandler("AccountCreated", this::handleAccountCreated);
        queue.addHandler("AccountReturned", this::handleAccountReturned);
    }

    public DTUPayAccount register(DTUPayAccount account) {
        registeredAccount = new CompletableFuture<>();
        Event event = new Event("RegisterAccountRequested", new Object[] { account });
        queue.publish(event);
        return registeredAccount.join();
    }

    public void handleAccountCreated(Event ev) {
        var a = ev.getArgument(0, DTUPayAccount.class);
        registeredAccount.complete(a);
    }

    public DTUPayAccount getAccount(String id) {
        returnedAccount = new CompletableFuture<>();
        Event event = new Event("GetAccountRequested", new Object[] { id });
        queue.publish(event);
        return returnedAccount.join();
    }

    public void handleAccountReturned(Event ev) {
        var a = ev.getArgument(0, DTUPayAccount.class);
        returnedAccount.complete(a);
    }
}
