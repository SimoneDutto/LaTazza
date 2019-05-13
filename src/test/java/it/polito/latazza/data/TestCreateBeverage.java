package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.BeverageException;

public class TestCreateBeverage {
	
	@Test
	public void testValidInputs() throws BeverageException {
		DataImpl data = new DataImpl();
		data.reset();
		assert(data.createBeverage("coffee", 50, 2000) == 1);
		assert(data.createBeverage("chocolate", 20, 1500) == 2);
	}
	
	@Test
	public void testDuplicateBeverage() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 50, 2000);
			data.createBeverage("coffee", 40, 2300);
		} catch (BeverageException e) {
			assertEquals("Beverage already exists", e.getMessage());
		}
	}
	
	@Test
	public void testNegativeBoxPrice() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 50, -2000);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testNullBoxPrice() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 50, 0);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testOverflowBoxPrice() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 50, Integer.MAX_VALUE+1);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testNegativeNumberOfCapsules() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", -50, 2000);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testNullNumberOfCapsules() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 0, 2000);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testOverflowNumberOfCapsules() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", Integer.MAX_VALUE+1, 2000);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
	
	@Test
	public void testWrongBeverageName() {
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("", 50, 2000);
		} catch (BeverageException e) {
			assertEquals("Beverage cannot be inserted: invalid values", e.getMessage());
		}
	}
}
