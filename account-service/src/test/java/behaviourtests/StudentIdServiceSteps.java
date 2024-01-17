package behaviourtests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import account.service.Student;
import account.service.StudentIdService;

public class StudentIdServiceSteps {
	MessageQueue queue = mock(MessageQueue.class);
	StudentIdService s = new StudentIdService(queue);
	Student student;
	Student expected;

	@When("a {string} event for a student is received")
	public void aEventForAStudentIsReceived(String eventName) {
		student = new Student();
		student.setName("James");
		assertNull(student.getId());
		s.handleStudentRegistrationRequested(new Event(eventName,new Object[] {student}));
	}

	@Then("the {string} event is sent")
	public void theEventIsSent(String eventName) {
		expected = new Student();
		expected.setName("James");
		expected.setId("123");
		var event = new Event(eventName, new Object[] {expected});
		verify(queue).publish(event);
	}

	@Then("the student gets a student id")
	public void theStudentGetsAStudentId() {
	    assertNotNull(expected.getId());
	}

}

