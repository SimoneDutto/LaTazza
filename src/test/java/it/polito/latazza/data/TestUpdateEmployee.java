package it.polito.latazza.data;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.EmployeeException;
import junit.framework.TestCase;

public class TestUpdateEmployee extends TestCase{
	
	@Test
	public void testExceptionId() throws EmployeeException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(2, "vito", "tassielli");
		
		}catch(EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
		
	}
	
	@Test
	public void testExceptionName() throws EmployeeException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(1, "", "ciao");
		
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void testExceptionSurname() throws EmployeeException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(1, "ciao", "");
			
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void testUpdatEmp() throws EmployeeException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(1, "vito", "tassielli");
			
			String name = data.getEmployeeName(1);
			String surname = data.getEmployeeSurname(1);
			
			assertEquals("vito", name);
			assertEquals("tassielli", surname);
		
		}catch(EmployeeException e) {
			e.printStackTrace();
		}
		
	}

}