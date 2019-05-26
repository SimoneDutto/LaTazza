package it.polito.latazza.timing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

/*
 * Class used for testing NFR 2: All functions should complete in < 0.5 sec.
 * Methods used for sells management are tested here.
 * */

public class TestSellTiming {
	
	DataImpl data = new DataImpl("test_db");
	
	@Test
	public void testSellCapsulesTiming() throws EmployeeException, BeverageException, NotEnoughBalance, NotEnoughCapsules {
		double avgTime;
		long start, end, totalTime = 0;
		
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 100, 10);
		data.rechargeAccount(1, Integer.MAX_VALUE-1);
		data.buyBoxes(1, 10);
		
		for (int i=0; i<50; i++) {
			start = System.currentTimeMillis();
			data.sellCapsules(1, 1, 1, true);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		avgTime = totalTime/50.0;
		assertTrue(avgTime < 500);		
	}
	
	@Test
	public void testSellCapsulesToVisitorTiming() throws EmployeeException, BeverageException, NotEnoughBalance, NotEnoughCapsules {
		double avgTime;
		long start, end, totalTime = 0;
		int bevId, empId;
		
		data.reset();
		empId = data.createEmployee("simone", "dutto");
		bevId = data.createBeverage("coffee", 100, 10);
		data.rechargeAccount(empId, Integer.MAX_VALUE-1);
		data.buyBoxes(1, 2);
		
		for (int i=0; i<50; i++) {
			start = System.currentTimeMillis();
			data.sellCapsulesToVisitor(bevId, 1);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		avgTime = totalTime/50.0;
		assertTrue(avgTime < 500);	
	}

}
