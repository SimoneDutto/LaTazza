package it.polito.latazza.data;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import junit.framework.TestCase;

public class TestGetBeverageBoxPrice extends TestCase{
	
	@Test
	public void testBeverageIdNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 50, 10);
			data.getBeverageBoxPrice(2);
		}
		catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}	
	}
	
	@Test
	public void testGetBoxPriceValid() throws BeverageException{
		DataImpl data = new DataImpl();
		data.reset();
		
		data.createBeverage("coffee", 50, 10);
		data.createBeverage("the", 50, 20);
		assert(data.getBeverageBoxPrice(1) == 10);
		assert(data.getBeverageBoxPrice(2) == 20);
	}
}
