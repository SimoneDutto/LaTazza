package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;

class TestGetBeverageName {
	DataImpl data = new DataImpl();
	@Test
	public void testBeverageIdNotValid(){
		data.reset();
		try {
			data.createBeverage("coffee", 50, 10);
			data.getBeverageName(2);
		}
		catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}	
	}
	
	@Test
	public void testGetNameValid() throws BeverageException{
		data.reset();
		
		data.createBeverage("coffee", 50, 10);
		data.createBeverage("the", 50, 20);
		assert(data.getBeverageName(1).equals("coffee"));
		assert(data.getBeverageName(2).equals("the"));
	}
}
