package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.NotEnoughCapsules;

public class TestUpdateBeverage {
	DataImpl data = new DataImpl("test_db");
	@Test
	public void TestExceptionId() {
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(2, "chocolate", 20, 100);
			assert(false);
		}catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
		
	}
	
	@Test
	public void TestExceptionName() {
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "", 20, 100);
			assert(false);
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void TestExceptionPrice() {
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "chocolate", 20, 0);
			assert(false);
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void TestExceptionBox() {
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "chocolate", 0, 100);
			assert(false);
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void testNullBeverageId() {
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(null, "chocolate", 0, 100);
			assert(false);
		}catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
	}
	
	@Test
	public void testNullBeverageName() {
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, null, 0, 100);
			assert(false);
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
	}
	
	@Test
	public void testNullCapsulesPerBox() {
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "chocolate", null, 100);
			assert(false);
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
	}
	
	@Test
	public void testNullBoxPrice() {
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "chocolate", 0, null);
			assert(false);
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
	}
	
	@Test
	public void TestUpdateBev() throws BeverageException{
		data.reset();
		
		data.createBeverage("coffe", 50, 500);
		data.updateBeverage(1, "chocolate", 20, 100);
		
		String name = data.getBeverageName(1);
		int cap =  data.getBeverageCapsulesPerBox(1);
		int price = data.getBeverageBoxPrice(1);
		
		assertEquals("chocolate", name);
		assertEquals(20, cap);
		assertEquals(100,price);	
	}
	
	@Test
	public void testUpdateOldCapsules() throws BeverageException {
		data.reset();
		
		int bevId = data.createBeverage("coffee", 50, 500);
		data.updateBeverage(bevId, "coffee", 60, 1000);
		int oldCapsulesNumber = DataBase.getInstance().getBeverageNumberOfOldCapsules(bevId);
		assertEquals(50, oldCapsulesNumber);
		int oldPrice = DataBase.getInstance().getBeverageOldCapsulesPrice(bevId);
		assertEquals(500, oldPrice);
		int totNumberOfCapsules = data.getBeverageCapsules(bevId);
		assertEquals(50+60, totNumberOfCapsules);
		int newPrice = data.getBeverageBoxPrice(bevId);
		assertEquals(1000, newPrice);
	}
	
	@Test
	public void testUpdateOldCapsulesException() {
		data.reset();
		
		try {
			int bevId = data.createBeverage("coffee", 50, 500);
			data.updateBeverage(bevId, "coffee", 50, 1000);
			data.updateBeverage(bevId, "coffee", 50, 3000);
			assert(false);
		} catch (BeverageException e) {
			assertEquals("Cannot update! There are already 2 different prices for this beverage!", e.getMessage());
		}
	}
	
	@Test 
	public void testPriceUpdateAfterSell() throws BeverageException, NotEnoughCapsules {
		data.reset();
		
		int bevId = data.createBeverage("coffee", 50, 500);
		data.updateBeverage(bevId, "coffee", 50, 1000);
		data.sellCapsulesToVisitor(bevId, 50);
		data.updateBeverage(bevId, "coffee", 60, 600);
	}
}