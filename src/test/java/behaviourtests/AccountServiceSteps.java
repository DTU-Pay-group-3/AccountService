package behaviourtests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import account.service.AccountService;
import account.service.DTUPayAccount;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.Assert;

public class AccountServiceSteps {
	MessageQueue queue = mock(MessageQueue.class);
	AccountService s = new AccountService(queue);
	DTUPayAccount account;
	DTUPayAccount result;

	@Given("an account exists")
	public void anAccountExists() {
		this.account = new DTUPayAccount("Alice", "Aname", "1122330000", "1234");
	}

	@When("a {string} event for a customer is received")
	public void aEventForACustomerIsReceived(String eventName) {
		this.result = s.registerDTUPayAccount(new Event(eventName,new Object[] {this.account}));
	}

	@Then("the {string} event is sent")
	public void theEventIsSent(String eventName) {
		var event = new Event(eventName, new Object[] {this.account});
		verify(queue).publish(event);
	}

	@And("an account is created")
	public void anAccountIsCreated() {
		assertFalse(s.getAccounts().isEmpty());
		assertEquals(this.account, this.result);
	}

	@Given("a customer in DTUPay with id {string}")
	public void aCustomerInDTUPayWithId(String id) {
		this.account.setId(id);
		s.addAccount(this.account);
	}

	@When("a {string} event for an account with id {string} is received")
	public void aEventForAnAccountWithIdIsReceived(String eventName, String id) {
		this.result = s.getDTUPayAccount(new Event(eventName,new Object[] {id}));
	}

	@And("the information of the account is returned")
	public void theInformationOfTheAccountIsReturned() {
		assertEquals(this.account, this.result);
	}

	@And("the account is already registered with DTUPay")
	public void theAccountIsAlreadyRegisteredWithDTUPay() {
		s.addAccount(this.account);
	}

	@And("no account is created")
	public void noAccountIsCreated() {
		assertNull(this.result);
	}
}