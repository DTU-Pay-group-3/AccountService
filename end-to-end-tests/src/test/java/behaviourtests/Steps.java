package behaviourtests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Steps {
	StudentRegistrationService service = new StudentRegistrationService();
	private Student student;
	private Student result;

	@Given("an unregistered student with empty id")
	public void anUnregisteredStudentWithEmptyId() {
	    student = new Student();
	    student.setName("James");
	    assertNull(student.getId());
	}

	@When("the student is being registered")
	public void theStudentIsBeingRegistered() {
		result = service.register(student);
	}

	@Then("the student is registered")
	public void thenTheStudentIsRegistered() {
		// test needs to go here
	}

	@Then("has a non empty id")
	public void hasANonEmptyId() {
		assertNotNull(result.getId());
	}

}

