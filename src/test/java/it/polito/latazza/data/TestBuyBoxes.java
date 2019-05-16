package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;

class TestBuyBoxes {
	
	@Test
	public void testBeverageIdNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.createBeverage("coffee", 50, 10);
			data.rechargeAccount(1, 20);
			data.buyBoxes(-1, 1);
		}
		catch(BeverageException | EmployeeException | NotEnoughBalance e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
			
	}
	@Test
	public void testBoxQuantityTooBig(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.createBeverage("coffee", 50, 10);
			data.rechargeAccount(1, 10);
			data.buyBoxes(1, 2);
		}
		catch(BeverageException | EmployeeException | NotEnoughBalance e) {
			assertEquals("Balance available is insufficient", e.getMessage());
		}
			
	}
	@Test
	public void testMaxBoxQuantityNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.createBeverage("coffee", 50, 10);
			data.rechargeAccount(1, 20);
			data.buyBoxes(1, Integer.MAX_VALUE);
		}
		catch(BeverageException | EmployeeException | NotEnoughBalance e) {
			assertEquals("Balance available is insufficient", e.getMessage());
		}
			
	}
	
	@Test
	public void testBuyBoxes() throws Exception{
		DataImpl data = new DataImpl();
		data.reset();
		data.createEmployee("vito", "tassielli");
		data.createBeverage("coffee", 50, 1000);
		data.rechargeAccount(1, 5000);
		
		data.buyBoxes(1, 2);

		assert(data.getBalance() == 3000);
	}
	
	
}