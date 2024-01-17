package accountfacade.service.adapter.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import accountfacade.service.StudentRegistrationService;
import accountfacade.service.Student;

@Path("/students")
public class StudentResource {

	StudentRegistrationService service = new StudentRegistrationFactory().getService();

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Student registerStudent(Student student) {
		return service.register(student);
	}
}
