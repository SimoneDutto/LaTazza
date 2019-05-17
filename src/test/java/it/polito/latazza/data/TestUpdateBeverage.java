package it.polito.latazza.data;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.BeverageException;
import junit.framework.TestCase;

public class TestUpdateBeverage extends TestCase{
	
	@Test
	public void testExceptionId() throws BeverageException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(2, "chocolate", 20, 100);
		
		}catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
		
	}
	
	@Test
	public void testExceptionName() throws BeverageException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "", 20, 100);
		
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void testExceptionPrice() throws BeverageException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "chocolate", 20, 0);
		
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void testExceptionBox() throws BeverageException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "chocolate", 0, 100);
		
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void testUpdateBev() throws BeverageException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "chocolate", 20, 100);
			
			String name = data.getBeverageName(1);
			int cap =  data.getBeverageCapsulesPerBox(1);
			int price = data.getBeverageBoxPrice(1);
			
			assertEquals("chocolate", name);
			assertEquals(20, cap);
			assertEquals(100,price);
		
		}catch(BeverageException e) {
			e.printStackTrace();
		}
		
	}

}