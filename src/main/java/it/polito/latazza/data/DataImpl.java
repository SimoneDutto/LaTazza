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
		else if (startDate.after(endDate) == true || startDate == null || endDate == null) {
			throw new DateException("Date interval is not valid");
		}
		
		else {
			java.sql.Date data1 = new java.sql.Date(startDate.getTime());
			java.sql.Date data2 = new java.sql.Date(endDate.getTime());
			
			value = DataBase.getInstance().getEmplRep(employeeId, data1, data2);
			System.out.println("Report correctly delivered");
		}
		return value;
	}

	@Override
	public List<String> getReport(Date startDate, Date endDate) throws DateException {
		List<String> value = new ArrayList<String>();
		
		if (startDate.after(endDate) == true || startDate == null || endDate == null) {
			throw new DateException("Date interval is not valid");
		}
		
		else {
			java.sql.Date data1 = new java.sql.Date(startDate.getTime());
			java.sql.Date data2 = new java.sql.Date(endDate.getTime());
			
			value = DataBase.getInstance().getRep(data1, data2);
			System.out.println("Report correctly delivered");
		}
		return value;
	}

	@Override
	public Integer createBeverage(String name, Integer capsulesPerBox, Integer boxPrice) throws BeverageException {
		int bevId; 
		if (name.isEmpty() || capsulesPerBox <= 0 || capsulesPerBox > Integer.MAX_VALUE || boxPrice <= 0 || boxPrice > Integer.MAX_VALUE) {
			throw new BeverageException("Beverage cannot be inserted: invalid values");
		} 
		bevId = DataBase.getInstance().addBeverage(name, capsulesPerBox, boxPrice);
		System.out.println("Beverage correctly inserted");
		return bevId;
	}

	@Override
	public void updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice)
			throws BeverageException {
		int check = DataBase.getInstance().checkBeverageId(id);
		if (check == -1) {
			throw new BeverageException("ID of the beverage is not valid");
		} else {
			check = DataBase.getInstance().updateBeverage(id, name, capsulesPerBox, boxPrice);
			if (check == -1)
				throw new BeverageException("Beverage cannot be updated");
			else
				System.out.println("Beverage correctly updated");
		}
		return;
	}

	@Override
	public String getBeverageName(Integer id) throws BeverageException {
		int check = -1;
		String name = null;
		
		check = DataBase.getInstance().checkBeverageId(id);
		if (check == -1)
			throw new BeverageException("ID of the beverage is not valid");
		else {
			name = DataBase.getInstance().getBeverageName(id);
			if (name == null)
				throw new BeverageException("Beverage name cannot be retrieved");
			else 
				System.out.println("Beverage name is " + name);
		}
		return name;
	}

	@Override
	public Integer getBeverageCapsulesPerBox(Integer id) throws BeverageException {
		int bevId = DataBase.getInstance().checkBeverageId(id), nCapsules = -1;
		if (bevId == -1) {
			throw new BeverageException("ID of the beverage is not valid");
		} else {
			nCapsules = DataBase.getInstance().getBeverageBoxInformation(id, "capPerBox");
			if (nCapsules == -1)
				throw new BeverageException("Number of capsules per box for the beverage cannot be retrieved");
			else
				System.out.println("Number of capsules per box: " + nCapsules);
		}
		return nCapsules;
	}

	@Override
	public Integer getBeverageBoxPrice(Integer id) throws BeverageException {
		int bevId = DataBase.getInstance().checkBeverageId(id), boxPrice = -1;
		if (bevId == -1) {
			throw new BeverageException("ID of the beverage is not valid");
		} else {
			boxPrice = DataBase.getInstance().getBeverageBoxInformation(id, "boxPrice");
			if (boxPrice == -1)
				throw new BeverageException("Box price for the beverage cannot be retrieved");
			else
				System.out.println("Box price: " + boxPrice);
		}
		return boxPrice;
	}

	@Override
	public List<Integer> getBeveragesId() {
		List<Integer> ids = new ArrayList<>();
		
		ids = DataBase.getInstance().getBeverageIds();
		
		return ids;
	}

	@Override
	public Map<Integer, String> getBeverages() {
		Map<Integer, String> beverages = new HashMap<>();
		
		beverages = DataBase.getInstance().getBeverages();
		
		return beverages;
	}

	@Override
	public Integer getBeverageCapsules(Integer id) throws BeverageException {
		Integer n = 0;
		
		n = DataBase.getInstance().checkBeverageId(id);
		if (id == -1) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		
		n = DataBase.getInstance().getBeverageAvailableCapsules(id);
		if (n == -1)
			throw new BeverageException("Available beverage capsules cannot be retrieved");
		
		System.out.println("Available beverage capsules: " + n);
		
		return n;
	}

	@Override
	public Integer createEmployee(String name, String surname) throws EmployeeException {
		int empId;
		if(name.isEmpty() || surname.isEmpty()) {
			throw new EmployeeException("Employee cannot be inserted: invalid values");
		}
		empId = DataBase.getInstance().addEmployee(name, surname); 
		System.out.println("Employee correctly inserted");
		return empId;
	}

	@Override
	public void updateEmployee(Integer id, String name, String surname) throws EmployeeException {
		int emp = DataBase.getInstance().checkEmp(id);
		if(emp == -1) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		else {
			int i = DataBase.getInstance().updateEmp(id, name, surname);
			if(i == 0) System.out.println("Employee correctly updated");
			else throw new EmployeeException("Error");
		}
		
	}

	@Override
	public String getEmployeeName(Integer id) throws EmployeeException {
		int emp = DataBase.getInstance().checkEmp(id);
		String name = null;
		if(emp == -1) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		else {
			name = DataBase.getInstance().getEmpName(id);
			System.out.println("Get Employee Name");
		}
		return name;
	}

	@Override
	public String getEmployeeSurname(Integer id) throws EmployeeException {
		int emp = DataBase.getInstance().checkEmp(id);
		String surname = null;
		if(emp == -1) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		else {
			surname = DataBase.getInstance().getEmpSurname(id);
			System.out.println("Get Employee Name");
		}
		return surname;
	}

	@Override
	public Integer getEmployeeBalance(Integer id) throws EmployeeException {
		int emp = DataBase.getInstance().checkEmp(id);
		int balance = 0;
		if(emp == -1) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		else {
			balance = DataBase.getInstance().getEmpBalance(id);
			System.out.println("Get Employee Name");
		}
		return balance;
	}

	@Override
	public List<Integer> getEmployeesId() {
		List<Integer> lista = new ArrayList<>();
		lista = DataBase.getInstance().getIds();
		return lista;
	}

	@Override
	public Map<Integer, String> getEmployees() {
		Map<Integer, String> mappa = new HashMap<>();
		mappa = DataBase.getInstance().getMap();
		return mappa;
	}

	@Override
	public Integer getBalance() {
		int balance = 0;
		balance = DataBase.getInstance().getBal();
		return balance;
	}

	@Override
	public void reset() {
		DataBase.getInstance().createDatabase();
	}

}