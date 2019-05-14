package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

class TestSellCapsules {

	@Test
	public void testEmployeeIdNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("simone", "dutto");
			data.createBeverage("coffee", 10, 2);
			data.sellCapsules(-1, 1, 1, true);
			assert(false);
		}
		catch(EmployeeException | BeverageException | NotEnoughCapsules e) {
			assertEquals("ID of the employee is not valid", e.getMessage());
		}
			
	}
	@Test
	public void testBeverageIdNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("simone", "dutto");
			data.createBeverage("coffee", 10, 2);
			data.sellCapsules(1, -1, 1, true);
			assert(false);
		}
		catch(EmployeeException | BeverageException | NotEnoughCapsules e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
			
	}
	@Test
	public void testNumberOfCapsulesIdTooBig(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("simone", "dutto");
			data.createBeverage("coffee", 10, 2);
			data.sellCapsules(1, 1, 10, true);
			assert(false);
		}
		catch(EmployeeException | BeverageException | NotEnoughCapsules e) {
			assertEquals("Number of available capsules is insufficient", e.getMessage());
		}
			
	}
	@Test
	public void testMaxNumberOfCapsulesNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("simone", "dutto");
			data.createBeverage("coffee", 10, 2);
			data.sellCapsules(1, 1, Integer.MAX_VALUE, true);
			assert(false);
		}
		catch(EmployeeException | BeverageException | NotEnoughCapsules e) {
			assertEquals("Number of available capsules is insufficient", e.getMessage());
		}
			
	}
	
	@Test
	public void testSellCapsuleAccount() throws Exception{
		DataImpl data = new DataImpl();
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 10);
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
		assert(data.getEmployeeBalance(1) == 9);
	}
	
	public void testSellCapsuleNoAccount() throws Exception{
		DataImpl data = new DataImpl();
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 10);
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
		assert(data.getEmployeeBalance(1) == 10);
		assert(data.getBalance()==1);
	}
	
	
	

}
