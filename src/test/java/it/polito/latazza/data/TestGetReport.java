package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.DateException;


class TestGetReport{
	DataImpl data = new DataImpl("test_db");
	@Test
	public void testGetReport() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		data.createBeverage("coffee", 10, 10);
		data.rechargeAccount(1, 10);
		data.rechargeAccount(2, 10);
		data.buyBoxes(1, 1);
		
		data.sellCapsules(1, 1, 1, true);
		data.sellCapsules(1, 1, 1, true);
		data.sellCapsules(2, 1, 1, true);
		data.sellCapsulesToVisitor(1, 2);
	
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getReport(inputDate, outDate);
		System.out.println(list);
		assert(list.size()==7);
		list.forEach(a -> {
			assert(a.contains("simone dutto")||a.contains("debora caldarola") || a.contains("coffee") || a.contains("VISITOR"));
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
			data.getReport(outDate,inputDate);
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
		List<String> list = data.getReport(date, date);
		assert(list.size()==4);
		
	}
	
	@Test
	public void testGetReportNoLoop() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		data.createBeverage("coffee", 10, 10);

		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getReport(inputDate, outDate);
		System.out.println(list);
		assert(list.size()==0);	
	}
	
	@Test
	public void testGetReportSellLoop() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		data.createBeverage("coffee", 10, 10);
		
		data.rechargeAccount(1, 1000);
		data.rechargeAccount(2, 1000);
		data.buyBoxes(1, 1);
		data.sellCapsules(1, 1, 5, true);
		data.sellCapsules(2, 1, 5, true);

		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getReport(inputDate, outDate);
		System.out.println(list);
		assert(list.size()==5);	
	}
	
	@Test
	public void testGetReportSellVisLoop() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		data.createBeverage("coffee", 10, 10);
		
		data.rechargeAccount(1, 1000);
		data.buyBoxes(1, 1);
		data.sellCapsulesToVisitor(1, 5);

		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getReport(inputDate, outDate);
		System.out.println(list);
		assert(list.size()==3);	
	}
	
	@Test
	public void testGetReportRechargeLoop() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		data.createBeverage("coffee", 10, 10);

		data.rechargeAccount(1, 1000);
		data.rechargeAccount(2, 1000);
		
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getReport(inputDate, outDate);
		System.out.println(list);
		assert(list.size()==2);	
	}
	
	@Test
	public void testGetReportBuyBoxLoop() throws Exception{
		data.reset();
		data.createEmployee("simone", "dutto");
		data.createEmployee("debora", "caldarola");
		data.createBeverage("coffee", 10, 10);

		data.rechargeAccount(1, 1000);
		data.rechargeAccount(2, 1000);
		
		data.buyBoxes(1, 1);
		data.buyBoxes(1, 1);
		
		String inputString = "11-11-2012";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		List<String> list = data.getReport(inputDate, outDate);
		System.out.println(list);
		assert(list.size()==4);	
	}
	

}
