package it.polito.latazza.data;

import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.EmployeeException;
import junit.framework.TestCase;

public class TestCreateEmployee extends TestCase{
	
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
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("Employee already exists", e.getMessage());
		}
	}
	
	@Test 
	public void testWrongName() {
		DataImpl data = new DataImpl();
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
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("debora", "");
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("Employee cannot be inserted: invalid values", e.getMessage());
		}
	}
}
