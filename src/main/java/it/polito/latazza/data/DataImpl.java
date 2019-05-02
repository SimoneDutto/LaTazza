package it.polito.latazza.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

public class DataImpl implements DataInterface {

	@Override
	public Integer sellCapsules(Integer employeeId, Integer beverageId, Integer numberOfCapsules, Boolean fromAccount)
			throws EmployeeException, BeverageException, NotEnoughCapsules {
		int balance = DataBase.getInstance().sellCap(employeeId, beverageId, numberOfCapsules, fromAccount); 
		
		if(balance == -3) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		else if(balance == -1) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		else if(balance == -2) {
			throw new NotEnoughCapsules("Number of available capsules is insufficient");
		}
		else {
			System.out.println("Sell correctly updated");
			return balance;
		}
	}

	@Override
	public void sellCapsulesToVisitor(Integer beverageId, Integer numberOfCapsules)
			throws BeverageException, NotEnoughCapsules {
		int value = DataBase.getInstance().sellVis(beverageId, numberOfCapsules); 

		if(value == -2) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		else if(value == -1) {
			throw new NotEnoughCapsules("Number of available capsules is insufficient");
		}
		else {
			System.out.println("Sell correctly updated");
		}
		
	}

	@Override
	public Integer rechargeAccount(Integer id, Integer amountInCents) throws EmployeeException {
		int value = DataBase.getInstance().recharge(id, amountInCents);

		if(value == -1) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		else {
			System.out.println("Recharge correctly inserted");
			return value;
		}
	}

	@Override
	public void buyBoxes(Integer beverageId, Integer boxQuantity) throws BeverageException, NotEnoughBalance {
		int value = DataBase.getInstance().buyB(beverageId, boxQuantity);

		if(value == -2) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		else if(value == -1) {
			throw new NotEnoughBalance("Balance is insufficient");
		}
		else {
			System.out.println("Boxes correctly received and paid for");
		}
		
	}

	@Override
	public List<String> getEmployeeReport(Integer employeeId, Date startDate, Date endDate)
			throws EmployeeException, DateException {
		
		int emp = DataBase.getInstance().checkEmp(employeeId);
		List<String> value = new ArrayList<String>();
		
		if(emp == -1) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		else if (startDate.after(endDate) == true) {
			throw new DateException("Date interval is not valid");
		}
		
		else {
			//value = DataBase.getInstance().getEmplRep(employeeId, startDate, endDate);
			System.out.println("Report correctly delivered");
		}
		return value;
	}

	@Override
	public List<String> getReport(Date startDate, Date endDate) throws DateException {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

	@Override
	public Integer createBeverage(String name, Integer capsulesPerBox, Integer boxPrice) throws BeverageException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice)
			throws BeverageException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBeverageName(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Integer getBeverageCapsulesPerBox(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getBeverageBoxPrice(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getBeveragesId() {
		// TODO Auto-generated method stub
		return new ArrayList<Integer>();
	}

	@Override
	public Map<Integer, String> getBeverages() {
		// TODO Auto-generated method stub
		return new HashMap<Integer, String>();
	}

	@Override
	public Integer getBeverageCapsules(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer createEmployee(String name, String surname) throws EmployeeException {
		// TODO Auto-generated method stub
		int empId = DataBase.getInstance().addEmployee(name, surname); 
		if(empId == 0) {
			throw new EmployeeException("Employee cannot be inserted");
		}
		else {
			System.out.println("Employee correctly inserted");
			return empId;
		}
	}

	@Override
	public void updateEmployee(Integer id, String name, String surname) throws EmployeeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEmployeeName(Integer id) throws EmployeeException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getEmployeeSurname(Integer id) throws EmployeeException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Integer getEmployeeBalance(Integer id) throws EmployeeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getEmployeesId() {
		// TODO Auto-generated method stub
		return new ArrayList<Integer>();
	}

	@Override
	public Map<Integer, String> getEmployees() {
		// TODO Auto-generated method stub
		return new HashMap<Integer, String>();
	}

	@Override
	public Integer getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}