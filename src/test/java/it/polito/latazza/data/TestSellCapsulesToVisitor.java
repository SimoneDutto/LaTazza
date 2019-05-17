package it.polito.latazza.data;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.NotEnoughCapsules;
import junit.framework.TestCase;

public class TestSellCapsulesToVisitor extends TestCase{
	
	@Test
	public void testBeverageIdNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 10, 2);
			data.sellCapsulesToVisitor(-1, 1);
			assert(false);
		}
		catch(BeverageException | NotEnoughCapsules e) {
			assertEquals("ID of the beverage is not valid", e.getMessage());
		}
			
	}
	@Test
	public void testNumberOfCapsulesIdTooBig(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 10, 2);
			data.sellCapsulesToVisitor(1, 10);
			assert(false);
		}
		catch(BeverageException | NotEnoughCapsules e) {
			assertEquals("Number of available capsules is insufficient", e.getMessage());
		}
			
	}
	@Test
	public void testMaxNumberOfCapsulesNotValid(){
		DataImpl data = new DataImpl();
		data.reset();
		try {
			data.createBeverage("coffee", 10, 2);
			data.sellCapsulesToVisitor(1, Integer.MAX_VALUE);
			assert(false);
		}
		catch(BeverageException | NotEnoughCapsules e) {
			assertEquals("Number of available capsules is insufficient", e.getMessage());
		}
			
	}
	
	@Test
	public void testSellCapsules() throws Exception{
		DataImpl data = new DataImpl();
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 10);
		data.buyBoxes(1, 1);
		
		data.sellCapsulesToVisitor(1, 1);
		
		assert(data.getBalance() == 1);
	}
	
	
}
