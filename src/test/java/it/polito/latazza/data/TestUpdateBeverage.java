package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.BeverageException;

public class TestUpdateBeverage {
	
	@Test
	public void TestExceptionId() throws BeverageException{
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
	public void TestExceptionName() throws BeverageException{
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
	public void TestExceptionPrice() throws BeverageException{
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
	public void TestExceptionBox() throws BeverageException{
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
	public void TestUpdateBev() throws BeverageException{
		DataImpl data = new DataImpl();
		data.reset();
		
		try {
			data.createBeverage("coffe", 50, 500);
			data.updateBeverage(1, "", 20, 100);
			
			String name = data.getBeverageName(1);
			int cap =  data.getBeverageCapsulesPerBox(1);
			int price = data.getBeverageBoxPrice(1);
			
			assertEquals("", name);
			assertEquals(20, cap);
			assertEquals(100,price);
		
		}catch(BeverageException e) {
			e.printStackTrace();
		}
		
	}

}