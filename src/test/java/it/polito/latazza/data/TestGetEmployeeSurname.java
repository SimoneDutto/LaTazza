package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.EmployeeException;

public class TestGetEmployeeSurname {
	DataImpl data = new DataImpl();
	@Test
	public void testValidInputs() throws EmployeeException {
		Integer employeeId;
		String surname;
		data.reset();
		employeeId = data.createEmployee("debora", "caldarola");
		surname = data.getEmployeeSurname(employeeId);
		assertEquals(surname, "caldarola");
	}
	
	@Test
	public void testNegativeId() {
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
