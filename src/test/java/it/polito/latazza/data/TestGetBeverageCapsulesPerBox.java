package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;

class TestGetBeverageCapsulesPerBox {
	DataImpl data = new DataImpl();
	@Test
	public void testBeverageIdNotValid(){
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
		data.reset();
		
		data.createBeverage("coffee", 50, 10);
		data.createBeverage("the", 30, 20);
		assert(data.getBeverageCapsulesPerBox(1) == 50);
		assert(data.getBeverageCapsulesPerBox(2) == 30);
	}
}
