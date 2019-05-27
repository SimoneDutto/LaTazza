package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.BeverageException;

public class TestCreateBeverage {
	
	DataImpl data = new DataImpl("test_db");
	
	@Test
	public void testValidInputs() throws BeverageException {
		data.reset();
		assert(data.createBeverage("coffee", 50, 2000) == 1);
		assert(data.createBeverage("chocolate", 20, 1500) == 2);
	}
	
	@Test
	public void testDuplicateBeverage() {
		data.reset();
		try {
			data.createBeverage("coffee", 50, 2000);
			data.createBeverage("coffee", 40, 2300);
			assert(false);
		} catch (BeverageException e) {
			assertEquals("Beverage already exists", e.getMessage());
		}
	}
	
	@Test
	public void testNegativeBoxPrice() {
		data.reset();
		try {
			data.createBeverage("coffee", 50, -2000);
			assert(false);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testNullBoxPrice() {
		data.reset();
		try {
			data.createBeverage("coffee", 50, 0);
			assert(false);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testOverflowBoxPrice() {
		data.reset();
		try {
			data.createBeverage("coffee", 50, Integer.MAX_VALUE+1);
			assert(false);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testNegativeNumberOfCapsules() {
		data.reset();
		try {
			data.createBeverage("coffee", -50, 2000);
			assert(false);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testNullNumberOfCapsules() {
		data.reset();
		try {
			data.createBeverage("coffee", 0, 2000);
			assert(false);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testOverflowNumberOfCapsules() {
		data.reset();
		try {
			data.createBeverage("coffee", Integer.MAX_VALUE+1, 2000);
			assert(false);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testWrongBeverageName() {
		
		data.reset();
		try {
			data.createBeverage("", 50, 2000);
			assert(false);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
}
