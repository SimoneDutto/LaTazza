package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.EmployeeException;

public class TestUpdateEmployee {
	DataImpl data = new DataImpl("test_db");
	
	@Test
	public void TestExceptionId() {
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(2, "vito", "tassielli");
			assert(false);
		}catch(EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
		
	}
	
	@Test
	public void testNullEmployeeId() {
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(null, "vito", "tassielli");
			assert(false);
		}catch(EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
	}
	
	@Test
	public void TestExceptionName() {
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(1, "", "ciao");
			assert(false);
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void testNullEmployeeName() {
		data.reset();
		
		try {
			int empId = data.createEmployee("lisa", "romita");
			data.updateEmployee(empId, null, "tassielli");
			assert(false);
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be inserted", e.getMessage());
		}
	}
	
	@Test
	public void TestExceptionSurname() {
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
			data.updateEmployee(1, "ciao", "");
			assert(false);
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void testNullEmployeeSurname() {
		data.reset();
		
		try {
			int empId = data.createEmployee("lisa", "romita");
			data.updateEmployee(empId, "vito", null);
			assert(false);
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be inserted", e.getMessage());
		}
	}
	
	@Test
	public void TestUpdatEmp() throws EmployeeException{
		data.reset();

		data.createEmployee("lisa", "romita");
		data.updateEmployee(1, "vito", "tassielli");
		
		String name = data.getEmployeeName(1);
		String surname = data.getEmployeeSurname(1);
		
		assertEquals("vito", name);
		assertEquals("tassielli", surname);
	}

}