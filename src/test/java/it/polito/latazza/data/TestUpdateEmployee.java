package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.EmployeeException;

public class TestUpdateEmployee {
	DataImpl data = new DataImpl();
	@Test
	public void TestExceptionId() throws EmployeeException{
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(2, "vito", "tassielli");
		
		}catch(EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
		
	}
	
	@Test
	public void TestExceptionName() throws EmployeeException{
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(1, "", "ciao");
		
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void TestExceptionSurname() throws EmployeeException{
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(1, "ciao", "");
			
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void TestUpdatEmp() throws EmployeeException{
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