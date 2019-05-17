package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.BeverageException;

public class TestUpdateBeverage {
	DataImpl data = new DataImpl();
	@Test
	public void TestExceptionId() throws BeverageException{
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(2, "chocolate", 20, 100);
		
		}catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
		
	}
	
	@Test
	public void TestExceptionName() throws BeverageException{
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "", 20, 100);
		
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void TestExceptionPrice() throws BeverageException{
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "chocolate", 20, 0);
		
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void TestExceptionBox() throws BeverageException{
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "chocolate", 0, 100);
		
		}catch(BeverageException e) {
			assertEquals("Beverage cannot be inserted", e.getMessage());
		}
		
	}
	
	@Test
	public void TestUpdateBev() throws BeverageException{
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