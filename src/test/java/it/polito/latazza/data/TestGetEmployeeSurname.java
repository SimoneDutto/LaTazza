package it.polito.latazza.data;

import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.EmployeeException;
import junit.framework.TestCase;

public class TestGetEmployeeSurname extends TestCase{
	
	@Test
	public void testValidInputs() throws EmployeeException {
		Integer employeeId;
		String surname;
		DataImpl data = new DataImpl();
		data.reset();
		employeeId = data.createEmployee("debora", "caldarola");
		surname = data.getEmployeeSurname(employeeId);
		assertEquals(surname, "caldarola");
	}
	
	@Test
	public void testNegativeId() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("debora", "caldarola");
			data.getEmployeeSurname(-1);
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
	}
	
	@Test
	public void testNullId() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("debora", "caldarola");
			data.getEmployeeSurname(0);
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
	}
	
	@Test
	public void testOutOfMaxBoundaryId() {
		DataImpl data = new DataImpl();
		Integer employeeId;
		data.reset();
		try {
			employeeId = data.createEmployee("debora", "caldarola");
			data.getEmployeeSurname(employeeId+1);
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
	}

}
