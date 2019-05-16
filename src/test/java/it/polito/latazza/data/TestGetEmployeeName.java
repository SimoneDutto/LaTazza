package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.EmployeeException;

class TestGetEmployeeName {
	
	@Test
	public void testEmployeeIdNotValid() throws EmployeeException{
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.getEmployeeName(2);
		}
		catch(EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}	
	}
	
	@Test
	public void testGetNameValid() throws EmployeeException {
		DataImpl data = new DataImpl();
		data.reset();
		
		data.createEmployee("vito", "tassielli");
		data.createEmployee("lisa", "romita");
		
		assert(data.getEmployeeName(1).equals("vito"));
		assert(data.getEmployeeName(2).equals("lisa"));
	}
}
