package it.polito.latazza.data;


import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.EmployeeException;
import junit.framework.TestCase;

public class TestRecharge extends TestCase{

	@Test
	public void testRechargeBalance() throws EmployeeException{
		
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
		
			int balance = data.getEmployeeBalance(1);
			int value = data.rechargeAccount(1, 100);
			assertEquals(100 + balance,value);
		
		}catch(EmployeeException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testRechargeException() throws EmployeeException {
		
		DataImpl data = new DataImpl();
		data.reset();		
		
		try {
			data.rechargeAccount(2, 500);
		}catch (EmployeeException e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
		
		
	}
	
	@Test
	public void testRechargeWithZero() throws EmployeeException{
		
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
		
			int balance = data.getEmployeeBalance(1);
			int value = data.rechargeAccount(1, 0);
			assertEquals(balance,value);
		
		}catch(EmployeeException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testRechargeMAXINT() throws EmployeeException{
		
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");
		
			int balance = data.getEmployeeBalance(1);
			int value = data.rechargeAccount(1, Integer.MAX_VALUE);
			assertEquals(Integer.MAX_VALUE + balance,value);
		
		}catch(EmployeeException e) {
			e.printStackTrace();
		}
		
	}
	
}
