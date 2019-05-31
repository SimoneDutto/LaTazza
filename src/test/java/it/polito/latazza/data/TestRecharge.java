package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.EmployeeException;

public class TestRecharge {

	DataImpl data = new DataImpl("test_db");
	
	@Test
	public void testRechargeBalance() throws EmployeeException{
		
		data.reset();

		data.createEmployee("lisa", "romita");
	
		int balance = data.getEmployeeBalance(1);
		int value = data.rechargeAccount(1, 100);
		assertEquals(100 + balance,value);
	}
	
	@Test
	public void testRechargeException() {
		data.reset();		
		
		try {
			data.rechargeAccount(2, 500);
			assert(false);
		}catch (EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
		
		
	}
	
	@Test
	public void testRechargeWithZero() throws EmployeeException {
		data.reset();
		data.createEmployee("lisa", "romita");
	
		int balance = data.getEmployeeBalance(1);
		int value = data.rechargeAccount(1, 0);
		assertEquals(balance,value);
	}
	
	@Test 
	public void testNullEmployeeId() {
		data.reset();	
		try {
			data.createEmployee("lisa", "romita");
		
			data.getEmployeeBalance(1);
			data.rechargeAccount(null, Integer.MAX_VALUE);
			assert(false);
		
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be updated", e.getMessage());
		}
	}
	
	@Test
	public void testNullAmountInCents() {
		data.reset();	
		try {
			int empId = data.createEmployee("lisa", "romita");
		
			data.getEmployeeBalance(1);
			data.rechargeAccount(empId, null);
			assert(false);
		
		}catch(EmployeeException e) {
			assertEquals("Employee cannot be updated", e.getMessage());
		}
	}
}
