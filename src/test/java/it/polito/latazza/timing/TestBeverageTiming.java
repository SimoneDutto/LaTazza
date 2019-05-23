package it.polito.latazza.timing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;

/*
 * Class used for testing NFR 2: All functions should complete in < 0.5 sec.
 * Methods used for beverages management are tested here.
 * */

public class TestBeverageTiming {
	
	DataImpl data = new DataImpl();
	Integer ids[] = new Integer[100];
	
	@Test
	public void testBeverageTime() throws BeverageException, NotEnoughBalance, EmployeeException {
		double avgTime;
		
		data.reset();
		
		avgTime = timeCreateBeverage();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetBeverageName();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetBeverageCapsules();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetBeverageBoxPrice();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetBeverageCapsulesPerBox();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetBeverages();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetBeveragesId();
		assertTrue(avgTime < 500);
		
		avgTime = timeUpdateBeverage();
		assertTrue(avgTime < 500);
		
		avgTime = timeBuyBoxes();
		assertTrue(avgTime < 500);
	}
	
	private double timeCreateBeverage() throws BeverageException {
		long start, end, totalTime = 0;
		String beverageName = "";
		double avgTime;
		
		for (int i=0; i<100; i++) {
			beverageName = i + "coffee";	// avoid duplicate name exception
			start = System.currentTimeMillis();
			ids[i] = data.createBeverage(beverageName, 100, 10);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		avgTime = totalTime/100.0;
		return avgTime;
	}
	
	private double timeGetBeverageName() throws BeverageException {
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<100; i++) {
			start = System.currentTimeMillis();
			data.getBeverageName(ids[i]);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/100.0;
		return avgTime;
	}
	
	private double timeGetBeverageCapsules() throws BeverageException{
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<100; i++) {
			start = System.currentTimeMillis();
			data.getBeverageCapsules(ids[i]);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/100.0;
		return avgTime;
	}
	
	private double timeGetBeverageBoxPrice() throws BeverageException{
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<100; i++) {
			start = System.currentTimeMillis();
			data.getBeverageBoxPrice(ids[i]);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/100.0;
		return avgTime;
	}
	
	private double timeGetBeverageCapsulesPerBox() throws BeverageException{
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<100; i++) {
			start = System.currentTimeMillis();
			data.getBeverageCapsulesPerBox(ids[i]);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/100.0;
		return avgTime;
	}
	
	private double timeGetBeverages() throws BeverageException{
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<100; i++) {
			start = System.currentTimeMillis();
			data.getBeverages();
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/100.0;
		return avgTime;
	}
	
	private double timeGetBeveragesId() throws BeverageException{
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<100; i++) {
			start = System.currentTimeMillis();
			data.getBeveragesId();
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/100.0;
		return avgTime;
	}
	
	private double timeUpdateBeverage() throws BeverageException{
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<100; i++) {
			start = System.currentTimeMillis();
			data.updateBeverage(ids[i], "the", 100, 8);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/100.0;
		return avgTime;
	}
	
	private double timeBuyBoxes() throws NotEnoughBalance, BeverageException, EmployeeException {
		long start, end, totalTime = 0;
		double avgTime;
		int id;
		
		id = data.createEmployee("lorem", "ipsum");
		data.rechargeAccount(id, Integer.MAX_VALUE-1);
		
		for (int i=0; i<100; i++) {
			start = System.currentTimeMillis();
			data.buyBoxes(ids[i], 1);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/100.0;
		return avgTime;
	}
	
}
