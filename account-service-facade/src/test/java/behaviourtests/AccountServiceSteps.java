package behaviourtests;

import accountfacade.service.AccountFacadeService;
import accountfacade.service.DTUPayAccount;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountServiceSteps {
    private CompletableFuture<Event> publishedEvent = new CompletableFuture<>();

    private MessageQueue q = new MessageQueue() {

        @Override
        public void publish(Event event) {
            publishedEvent.complete(event);
        }

        @Override
        public void addHandler(String eventType, Consumer<Event> handler) {
        }

    };
    private AccountFacadeService service = new AccountFacadeService(q);
    private DTUPayAccount account;

    @Given("a customer with a bank account")
    public void aCustomerWithABankAccount() {
        this.account = new DTUPayAccount("Alice", "Aname", "1122330000", "1234");
    }

    @And("the customer does not exist in DTUPay")
    public void theCustomerDoesNotExistInDTUPay() {
        DTUPayAccount result = service.getAccount(this.account.getId());
        assertNull(result);
    }

    @When("the customer registers with DTUPay")
    public void theCustomerRegistersWithDTUPay() {
        service.register(this.account);
    }

    @Then("a customer with the same information as the bank customer exists in DTUPay")
    public void aCustomerWithTheSameInformationAsTheBankCustomerExistsInDTUPay() {
        DTUPayAccount result = service.getAccount(this.account.getId());
        assertEquals(this.account, result);
    }
}
