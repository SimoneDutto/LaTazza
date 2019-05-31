package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.EmployeeException;

public class TestCreateEmployee {
	DataImpl data = new DataImpl("test_db");
	@Test
	public void testValidInputs() throws EmployeeException {
		data.reset();
		assert(data.createEmployee("debora", "caldarola") == 1);
		assert(data.createEmployee("simone", "dutto") == 2);
	}
	
	@Test
	public void testDuplicateEmployee() {
		data.reset();
		try {
			data.createEmployee("debora", "caldarola");
			data.createEmployee("debora", "caldarola");
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("Employee already exists", e.getMessage());
		}
	}
	
	@Test 
	public void testWrongName() {
		data.reset();
		try {
			data.createEmployee("", "caldarola");
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("Employee cannot be inserted: invalid values", e.getMessage());
		}
	}

	@Test 
	public void testWrongSurname() {
		data.reset();
		try {
			data.createEmployee("debora", "");
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("Employee cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testNullName() {
		data.reset();
		try {
			data.createEmployee(null, "caldarola");
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("Employee cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testNullSurname() {
		data.reset();
		try {
			data.createEmployee("debora", null);
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("Employee cannot be inserted: invalid values", e.getMessage());
		}
	}
}
