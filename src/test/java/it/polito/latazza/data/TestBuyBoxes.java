package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;

class TestBuyBoxes {
	DataImpl data = new DataImpl("test_db");
	@Test
	public void testBeverageIdNotValid() throws Exception {
		
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.createBeverage("coffee", 50, 10);
			data.rechargeAccount(1, 999);
			data.buyBoxes(-1, 1);
			assert(false);
		}
		catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
			
	}
	
	@Test
	public void testNullBeverageId() throws Exception {
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.createBeverage("coffee", 50, 10);
			data.rechargeAccount(1, 999);
			data.buyBoxes(null, 1);
			assert(false);
		}
		catch(BeverageException e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
	}
	
	@Test
	public void testBoxQuantityTooBig(){
		data.reset();
		
		try {
			data.createEmployee("vito", "tassielli");
			data.createBeverage("coffee", 50, 1000);
			data.rechargeAccount(1, 10);
			data.buyBoxes(1, 2);
			assert(false);
		}
		catch(BeverageException | EmployeeException | NotEnoughBalance e) {
			assertEquals("Balance is insufficient", e.getMessage());
		}
			
	}
	@Test
	public void testMaxBoxQuantityNotValid() throws Exception {
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.createBeverage("coffee", 50, 10);
			data.rechargeAccount(1, 20);
			data.buyBoxes(1, -1);
			assert(false);
		}
		catch(BeverageException e) {
			assertEquals("Balance available is insufficient", e.getMessage());
		}
			
	}
	
	@Test
	public void testNullBoxQuantity() throws Exception {
		data.reset();
		try {
			data.createEmployee("vito", "tassielli");
			data.createBeverage("coffee", 50, 10);
			data.rechargeAccount(1, 20);
			data.buyBoxes(1, null);
			assert(false);
		}
		catch(BeverageException e) {
			assertEquals("Balance available is insufficient", e.getMessage());
		}
	}
	
	@Test
	public void testBuyBoxes() throws Exception{
		data.reset();
		data.createEmployee("vito", "tassielli");
		data.createBeverage("coffee", 50, 1000);
		data.rechargeAccount(1, 5000);
		data.buyBoxes(1, 2);

		assert(data.getBalance() == 3000);
	}
	
	
}