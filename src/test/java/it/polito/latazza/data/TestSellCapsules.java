package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughCapsules;


class TestSellCapsules{
	DataImpl data = new DataImpl("test_db");
	@Test
	public void testEmployeeIdNotValid(){
		data.reset();
		try {
			data.createEmployee("simone", "dutto");
			data.createBeverage("coffee", 10, 200);
			data.sellCapsules(-1, 1, 1, true);
			assert(false);
		}
		catch(EmployeeException | BeverageException | NotEnoughCapsules e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
	}
	@Test
	public void testBeverageIdNotValid(){
		data.reset();
		try {
			data.createEmployee("simone", "dutto");
			data.createBeverage("coffee", 10, 200);
			data.sellCapsules(1, -1, 1, true);
			assert(false);
		}
		catch(EmployeeException | BeverageException | NotEnoughCapsules e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
			
	}
	@Test
	public void testNumberOfCapsulesIdTooBig(){
		data.reset();
		try {
			data.createEmployee("simone", "dutto");
			data.createBeverage("coffee", 10, 200);
			data.sellCapsules(1, 1, 10, true);
			assert(false);
		}
		catch(EmployeeException | BeverageException | NotEnoughCapsules e) {
			assertEquals("Number of available capsules is insufficient", e.getMessage());
		}
			
	}
	@Test
	public void testMaxNumberOfCapsulesNotValid(){
		data.reset();
		try {
			data.createEmployee("simone", "dutto");
			data.createBeverage("coffee", 10, 200);
			data.sellCapsules(1, 1, Integer.MAX_VALUE+1, true);
			assert(false);
		}
		catch(EmployeeException | BeverageException | NotEnoughCapsules e) {
			assertEquals("Number of available capsules is insufficient", e.getMessage());
		}
			
	}
	
	@Test
	public void testSellCapsuleAccount() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 1000);
		data.rechargeAccount(1, 1000);
		data.buyBoxes(1, 1);
		
		data.sellCapsules(1, 1, 1, true);
	
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getReport(inputDate, outDate);
		String s = list.get(0);
		assert(s.contains("simone dutto coffee 1"));
		assert(data.getEmployeeBalance(1) == 900);
	}
	
	@Test
	public void testNegativeBalance() throws Exception {
		int bevId, empId1, empId2, balance;
		
		data.reset();
		empId1 = data.createEmployee("simone", "dutto");
		empId2 = data.createEmployee("debora", "caldarola");
		bevId = data.createBeverage("coffee", 100, 1000);
		data.rechargeAccount(empId1, 1000);
		data.buyBoxes(bevId, 1);
		
		balance = data.sellCapsules(empId2, bevId, 1, true);
		assertTrue(balance < 0);
	}
	
	@Test
	public void testSellCapsuleNoAccount() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 1000);
		data.rechargeAccount(1, 1000);
		data.buyBoxes(1, 1);
		
		data.sellCapsules(1, 1, 1, false);
	
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getReport(inputDate, outDate);
		String s = list.get(0);
		assert(s.contains("simone dutto coffee 1"));
		assert(data.getEmployeeBalance(1) == 1000);
		assert(data.getBalance()==100);
	}
	
	
	

}
