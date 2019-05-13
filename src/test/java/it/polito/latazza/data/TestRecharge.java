package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.EmployeeException;

public class TestRecharge {

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
	
	@Test
	public void testRechargeNegative() throws EmployeeException{
		
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createEmployee("lisa", "romita");

		    final ByteArrayOutputStream out = new ByteArrayOutputStream();
		    System.setOut(new PrintStream(out));
		    
		    int balanceBefore = data.getBalance();
			data.rechargeAccount(1, -100);
			int balanceAfter = data.getBalance();
   
		    assertEquals("Recharge not done: the amount was negative!\r\n", out.toString());
		    assertEquals(balanceBefore,balanceAfter);
		
		}catch(EmployeeException e) {
			e.printStackTrace();
		}
		
	}
}
