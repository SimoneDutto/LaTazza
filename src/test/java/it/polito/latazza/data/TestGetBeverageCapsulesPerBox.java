package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;

class TestGetBeverageCapsulesPerBox {
	
	@Test
	public void testBeverageIdNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 50, 10);
			data.getBeverageCapsulesPerBox(2);
		}
		catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
	}
	
	@Test
	public void testCapsulesPerBoxValid() throws BeverageException{
		DataImpl data = new DataImpl();
		data.reset();
		
		data.createBeverage("coffee", 50, 10);
		data.createBeverage("the", 30, 20);
		assert(data.getBeverageCapsulesPerBox(1) == 50);
		assert(data.getBeverageCapsulesPerBox(2) == 30);
	}
}
