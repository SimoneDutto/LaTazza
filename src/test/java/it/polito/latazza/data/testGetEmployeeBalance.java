package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.EmployeeException;

public class testGetEmployeeBalance {
	DataImpl data = new DataImpl("test_db");
	@Test
	public void testValidInputs() throws EmployeeException {
		Integer employeeId, balance;
		data.reset();
		employeeId = data.createEmployee("debora", "caldarola");
		assert(data.getEmployeeBalance(employeeId) == 0);
		balance = data.rechargeAccount(employeeId, 1000);
		assertEquals(balance, data.getEmployeeBalance(employeeId));
	}
	
	@Test
	public void testNegativeId() {
		data.reset();
		try {
			data.createEmployee("debora", "caldarola");
			data.getEmployeeBalance(-1);
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
			data.getEmployeeBalance(0);
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
			data.getEmployeeBalance(employeeId+1);
			assert(false);
		} catch (EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
	}


}
