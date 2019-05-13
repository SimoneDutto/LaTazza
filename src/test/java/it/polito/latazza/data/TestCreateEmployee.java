package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.EmployeeException;

public class TestCreateEmployee {
	
	@Test
	public void testValidInputs() throws EmployeeException {
		DataImpl data = new DataImpl();
		data.reset();
		assert(data.createEmployee("debora", "caldarola") == 1);
		assert(data.createEmployee("simone", "dutto") == 2);
	}
	
	@Test 
	public void testDuplicateEmployee() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("debora", "caldarola");
			data.createEmployee("debora", "caldarola");
		} catch (EmployeeException e) {
			assertEquals("Employee cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test 
	public void testWrongName() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("", "caldarola");
		} catch (EmployeeException e) {
			assertEquals("Employee cannot be inserted: invalid values", e.getMessage());
		}
	}

	@Test 
	public void testWrongSurname() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("debora", "");
		} catch (EmployeeException e) {
			assertEquals("Employee cannot be inserted: invalid values", e.getMessage());
		}
	}
}
