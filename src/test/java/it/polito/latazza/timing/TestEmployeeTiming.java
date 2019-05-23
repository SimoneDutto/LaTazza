package it.polito.latazza.timing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.Test;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;


/*
 * Class used for testing NFR 2: All functions should complete in < 0.5 sec.
 * Methods used for employees management are tested here.
 * */

public class TestEmployeeTiming {
	
	DataImpl data = new DataImpl();
	Integer ids[] = new Integer[50];
	
	@Test
	public void testEmployeeTime() throws EmployeeException, ParseException, DateException {
		double avgTime;
		data.reset();
		
		avgTime = timeCreateEmployee();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetEmployeeName();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetEmployeeSurname();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetEmployeeBalance();
		assertTrue(avgTime < 500);
		
		avgTime = timeGetEmployeeReport();
		assertTrue(avgTime < 500);
		
		avgTime = timeRechargeAccount();
		assertTrue(avgTime < 500);
		
		avgTime = timeUpdateEmployee();
		assertTrue(avgTime < 500);
		
		
	}
	
	private double timeCreateEmployee() throws EmployeeException {
		long start, end, totalTime = 0;
		String name, surname;
		double avgTime;
		
		for (int i=0; i<50; i++) {
			name = i + "debora";	// avoid duplicate name exception
			surname = i + "caldarola";
			start = System.currentTimeMillis();
			ids[i] = data.createEmployee(name, surname);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		avgTime = totalTime/50.0;
		return avgTime;
	}
	
	private double timeGetEmployeeName() throws EmployeeException {
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<50; i++) {
			start = System.currentTimeMillis();
			data.getEmployeeName(ids[i]);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/50.0;
		return avgTime;
	}
	
	private double timeGetEmployeeSurname() throws EmployeeException {
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<50; i++) {
			start = System.currentTimeMillis();
			data.getEmployeeSurname(ids[i]);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/50.0;
		return avgTime;
	}
	
	private double timeGetEmployeeBalance() throws EmployeeException {
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<50; i++) {
			start = System.currentTimeMillis();
			data.getEmployeeBalance(ids[i]);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/50.0;
		return avgTime;
	}
	
	private double timeGetEmployeeReport() throws EmployeeException, ParseException, DateException {
		long start, end, totalTime = 0;
		double avgTime;
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String inputString = "11-11-2012";
		Date inputDate = dateFormat.parse(inputString);
		inputString = "11-11-2020";
		Date outDate;
		outDate = dateFormat.parse(inputString);
		
		for (int i=0; i<50; i++) {
			start = System.currentTimeMillis();
			data.getEmployeeReport(ids[i], inputDate, outDate);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/50.0;
		return avgTime;
	}
	
	private double timeRechargeAccount() throws EmployeeException{
		long start, end, totalTime = 0;
		double avgTime;
		
		for (int i=0; i<50; i++) {
			start = System.currentTimeMillis();
			data.rechargeAccount(ids[i], 1000);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/50.0;
		return avgTime;
	}
	
	private double timeUpdateEmployee() throws EmployeeException {
		long start, end, totalTime = 0;
		double avgTime;
		String name, surname;
		
		for (int i=0; i<50; i++) {
			name = i + "simone";
			surname = i + "dutto";
			start = System.currentTimeMillis();
			data.updateEmployee(ids[i], name, surname);
			end = System.currentTimeMillis();
			totalTime += end - start;
		}
		
		avgTime = totalTime/50.0;
		return avgTime;
	}
	
}

