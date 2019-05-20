package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;

class TestGetEmployeeReport{
	DataImpl data = new DataImpl();
	@Test
	public void testGetEmployeeReport() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 10);
		data.buyBoxes(1, 1);
		
		data.sellCapsules(1, 1, 1, true);
		data.sellCapsules(1, 1, 1, true);
	
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getEmployeeReport(1,inputDate, outDate);
		System.out.println(list);
		assert(list.size()==3);
		list.forEach(a -> {
			assert(a.contains("simone dutto"));
		});
		
	}
	
	@Test
	public void testWrongDate() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 10);
		data.buyBoxes(1, 1);
		
		data.sellCapsules(1, 1, 1, true);
		data.sellCapsules(1, 1, 1, true);
	
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		try {
			data.getEmployeeReport(1,outDate,inputDate);
			assert(false);
		}
		catch(DateException e) {
			assertEquals(e.getMessage(), "Date interval is not valid");
		}	
	}
	@Test
	public void testNullDate() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 10);
		data.buyBoxes(1, 1);
		
		data.sellCapsules(1, 1, 1, true);
		data.sellCapsules(1, 1, 1, true);
	
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		try {
			data.getEmployeeReport(1,null,inputDate);
			assert(false);
		}
		catch(DateException e) {
			assertEquals(e.getMessage(), "Date interval is not valid");
		}	
	}
	@Test
	public void testEqualDates() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 10);
		data.buyBoxes(1, 1);
		
		data.sellCapsules(1, 1, 1, true);
		data.sellCapsules(1, 1, 1, true);
	
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		String dateString = format.format(new Date());
		Date   date       = format.parse (dateString);
		List<String> list = data.getEmployeeReport(1, date, date);
		assert(list.size()==3);
		
	}
	@Test
	public void testBeverageIdNotValid() throws Exception{ 
		data.reset();
		try {
			data.createEmployee("simone", "dutto");
			data.createBeverage("coffee", 10, 2);
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

			String dateString = format.format(new Date());
			Date   date       = format.parse (dateString);
			data.getEmployeeReport(-1,  date, date);
			assert(false);
		}
		catch(EmployeeException b) {
			assertEquals("ID of the employee is not valid", b.getMessage());
		}
			
	}
	
	@Test
	public void testGetEmployeeRepNoLoop() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getEmployeeReport(1,inputDate, outDate);
		System.out.println(list);
		assert(list.size()==0);
		
	}
	
	@Test
	public void testGetEmployeeReportRechLoop() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 10);
	
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getEmployeeReport(1,inputDate, outDate);
		System.out.println(list);
		assert(list.size()==1);
		list.forEach(a -> {
			assert(a.contains("simone dutto"));
		});
		
	}
	
	@Test
	public void testGetEmployeeReportSellLoop() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 100);
		data.buyBoxes(1, 1);
		
		data.sellCapsules(1, 1, 1, true);
		data.sellCapsules(1, 1, 1, true);
	
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getEmployeeReport(1,inputDate, outDate);
		System.out.println(list);
		assert(list.size()==3);
		list.forEach(a -> {
			assert(a.contains("simone dutto"));
		});
		
	}
	

}