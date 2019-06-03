package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.EmployeeException;

class TestGetEmployeeName {
	DataImpl data = new DataImpl("test_db");
	@Test
	public void testEmployeeIdNotValid() throws EmployeeException{
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.getEmployeeName(2);
			assert(false);
		}
		catch(EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}	
	}
	
	@Test
	public void testNullEmployeeId() {
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.getEmployeeName(null);
			assert(false);
		}
		catch(EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}	
	}
	
	@Test
	public void testGetNameValid() throws EmployeeException {
		data.reset();
		
		data.createEmployee("vito", "tassielli");
		data.createEmployee("lisa", "romita");
		
		assert(data.getEmployeeName(1).equals("vito"));
		assert(data.getEmployeeName(2).equals("lisa"));
	}
}
