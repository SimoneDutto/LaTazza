package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.NotEnoughCapsules;

class TestSellCapsulesToVisitor {
	DataImpl data = new DataImpl("test_db");
	
	@Test
	public void testBeverageIdNotValid() throws Exception {
		data.reset();
		try {
			data.createBeverage("coffee", 10, 200);
			data.sellCapsulesToVisitor(-1, 1);
			assert(false);
		}
		catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
	}
	
	@Test
	public void testNullBeverageId() throws Exception {
		data.reset();
		try {
			data.createBeverage("coffee", 10, 200);
			data.sellCapsulesToVisitor(null, 1);
			assert(false);
		}
		catch(BeverageException e) {
			assertEquals("Beverage Id not valid", e.getMessage());
		} catch (NotEnoughCapsules e) {
			assert(false);
		}
	}
	
	@Test
	public void testNumberOfCapsulesIsTooBig() throws Exception {
		data.reset();
		try {
			data.createBeverage("coffee", 10, 200);
			data.sellCapsulesToVisitor(1, 10);
			assert(false);
		}
		catch(NotEnoughCapsules e) {
			assertEquals("Number of available capsules is insufficient", e.getMessage());
		}
			
	}
	
	@Test
	public void testMaxNumberOfCapsulesNotValid() throws Exception {
		data.reset();
		try {
			data.createBeverage("coffee", 10, 200);
			data.sellCapsulesToVisitor(1, Integer.MAX_VALUE);
			assert(false);
		}
		catch(NotEnoughCapsules e) {
			assertEquals("Number of available capsules is insufficient", e.getMessage());
		}
			
	}
	
	@Test
	public void testNullNumberOfCapsules() throws Exception {
		data.reset();
		try {
			data.createBeverage("coffee", 10, 200);
			data.sellCapsulesToVisitor(1, null);
			assert(false);
		}
		catch(NotEnoughCapsules e) {
			assertEquals("Not valid number of capsules", e.getMessage());
		} 
	}
	
	@Test
	public void testSellCapsules() throws Exception {
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 1000);
		data.rechargeAccount(1, 1000);
		data.buyBoxes(1, 1);
		
		data.sellCapsulesToVisitor(1, 1);
		
		assert(data.getBalance() == 100);
	}
	
	
}
