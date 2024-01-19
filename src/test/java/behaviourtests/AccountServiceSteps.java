package behaviourtests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import account.service.AccountService;
import account.service.DTUPayAccount;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;

// @Author for entire class: Caroline
public class AccountServiceSteps {
	MessageQueue queue = mock(MessageQueue.class);
	AccountService s = new AccountService(queue);

	DTUPayAccount account;
	DTUPayAccount result;

	@Given("an unregistered account exists")
	public void anUnregisteredAccountExists() {
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

	@Then("the {string} event is sent with an empty response")
	public void theEventIsSentWithAnEmptyResponse(String eventName) {
		DTUPayAccount emptyAccount = new DTUPayAccount("", "", "", "");
		emptyAccount.setId("");
		var event = new Event(eventName, new Object[] { emptyAccount });
		verify(queue).publish(event);
	}


	@And("an account is created")
	public void anAccountIsCreated() {
		assertFalse(s.getAccounts().isEmpty());
		assertTrue(s.getAccounts().containsKey(this.account.getId()));
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

	@And("an account is not created")
	public void anAccountIsNotCreated() {
		assertTrue(this.result.getId().isBlank());

		DTUPayAccount existingAccount = s.getAccounts().get(this.account.getId());
		assertNotEquals(this.account.getFirstName(), existingAccount.getFirstName());
	}

	@And("an account with the same id already exists")
	public void anAccountWithTheSameIdAlreadyExists() {
		DTUPayAccount existingAccount = new DTUPayAccount(
				"existing",
				"account",
				this.account.getCprNumber(),
				this.account.getAccountNumber()
		);
		existingAccount.setId(this.account.getId());
		s.addAccount(existingAccount);
	}

	@And("the information of the account is empty")
	public void theInformationOfTheAccountIsEmpty() {
		assertEquals("", this.result.getId());
		assertEquals("", this.result.getAccountNumber());
		assertEquals("", this.result.getFirstName());
		assertEquals("", this.result.getLastName());
		assertEquals("", this.result.getCprNumber());

	}
}