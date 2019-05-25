package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestUtilityFunction{
	DataImpl data = new DataImpl("test_db");
	@Test
	public void testMapEmployee() throws Exception{
		
		data.reset();
		
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		data.createEmployee("lisa", "vito");
		
		HashMap<Integer, String> test = new HashMap<>();
		test.put(1, "simone dutto");
		test.put(2, "debora caldarola");
		test.put(3, "lisa vito");
		
		assertEquals(test, data.getEmployees());
	}
	
	@Test
	public void tesGetBalance() throws Exception{
		data.reset();
		
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		data.createEmployee("lisa", "vito");
		
		data.createBeverage("coffee", 10, 10);
		
		data.rechargeAccount(1, 10);
		assert(data.getBalance() == 10);
		
		data.buyBoxes(1, 1);
		assert(data.getBalance() == 0);
		
		data.sellCapsulesToVisitor(1, 1);
		assert(data.getBalance() == 1);
	}	
	
	@Test
	public void testReset() throws Exception{
		data.reset();
		
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		data.createBeverage("coffee", 10, 10);
		data.reset();
		
		
		assert(data.getEmployeesId().size() == 0);
		assert(data.getBeveragesId().size() == 0);
		
	}
	
	@Test
	public void testGetIds() throws Exception {
		data.reset();
		
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		List<Integer> list = DataBase.getInstance().getIds();
		assert(list.size() == 2);
	}
	
	
}
