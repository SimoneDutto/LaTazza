package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;

class TestGetBeverageCapsules {
	
	@Test
	public void testBeverageIdNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 50, 10);
			data.getBeverageCapsules(2);
		}
		catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}	
	}
	
	@Test
	public void testGetNameValid() throws EmployeeException, BeverageException, NotEnoughBalance {
		DataImpl data = new DataImpl();
		data.reset();
		
		data.createBeverage("coffee", 50, 10);
		data.createBeverage("the", 50, 20);
		data.buyBoxes(1, 2);
		data.buyBoxes(2, 3);
		
		assert(data.getBeverageCapsules(1) == 100);
		assert(data.getBeverageCapsules(2) == 150);
	}
}
