package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.BeverageException;

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
}