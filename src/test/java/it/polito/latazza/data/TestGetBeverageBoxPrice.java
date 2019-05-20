package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;

class TestGetBeverageBoxPrice {
	DataImpl data = new DataImpl();
	@Test
	public void testBeverageIdNotValid(){
		data.reset();
		try {
			data.createBeverage("coffee", 50, 10);
			data.getBeverageBoxPrice(2);
			assert(false);
		}
		catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}	
	}
	
	@Test
	public void testGetBoxPriceValid() throws BeverageException{
		data.reset();
		System.out.println("ciao");
		data.createBeverage("coffee", 50, 10);
		data.createBeverage("the", 50, 20);
		assert(data.getBeverageBoxPrice(1) == 10);
		assert(data.getBeverageBoxPrice(2) == 20);
	}
}
