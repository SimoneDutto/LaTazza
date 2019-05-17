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
	
	public DataImpl() {
		DataBase.getInstance().createDatabase();
	}

	@Override
	public Integer sellCapsules(Integer employeeId, Integer beverageId, Integer numberOfCapsules, Boolean fromAccount)
			throws EmployeeException, BeverageException, NotEnoughCapsules {
		
		int balance = DataBase.getInstance().sellCap(employeeId, beverageId, numberOfCapsules, fromAccount); 
		System.out.println("Sell correctly updated");
		return balance;
	
	}

	@Override
	public void sellCapsulesToVisitor(Integer beverageId, Integer numberOfCapsules)
			throws BeverageException, NotEnoughCapsules {
		
		DataBase.getInstance().sellVis(beverageId, numberOfCapsules); 

		System.out.println("Sell correctly updated");
		
	}

	@Override
	public Integer rechargeAccount(Integer id, Integer amountInCents) throws EmployeeException {
		if(amountInCents < 0) {
			System.out.println("Recharge not done: the amount was negative!");
			return DataBase.getInstance().getBal();
		}
		
		int value = DataBase.getInstance().recharge(id, amountInCents);

		System.out.println("Recharge correctly inserted");
		
		return value;
	}

	@Override
	public void buyBoxes(Integer beverageId, Integer boxQuantity) throws NotEnoughBalance, BeverageException {
		DataBase.getInstance().buyB(beverageId, boxQuantity);

		System.out.println("Boxes correctly received and paid for");
	}
	
	@Override
	public List<String> getEmployeeReport(Integer employeeId, Date startDate, Date endDate)
			throws EmployeeException, DateException {
		
		DataBase.getInstance().checkEmp(employeeId);
		List<String> value = new ArrayList<String>();
		
		if (startDate == null || endDate == null || startDate.after(endDate) == true ) {
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
		if (!DataBase.getInstance().beverageIsDuplicate(name)) {
			bevId = DataBase.getInstance().addBeverage(name, capsulesPerBox, boxPrice);
			System.out.println("Beverage correctly inserted");
		} else {
			throw new BeverageException("Beverage already exists");
		}
		return bevId;
	}

	@Override
	public void updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice)
			throws BeverageException {
		DataBase.getInstance().checkBeverageId(id);
		if (name.isEmpty() || capsulesPerBox == 0 || boxPrice == 0) {
			throw new BeverageException("Beverage cannot be inserted");
		} else {
			DataBase.getInstance().updateBeverage(id, name, capsulesPerBox, boxPrice);
			System.out.println("Beverage correctly updated");
		}
		
		return;
	}

	@Override
	public String getBeverageName(Integer id) throws BeverageException {
		String name = null;
		DataBase.getInstance().checkBeverageId(id);
		
		name = DataBase.getInstance().getBeverageName(id);
		System.out.println("Beverage name is " + name);
		
		return name;
	}

	@Override
	public Integer getBeverageCapsulesPerBox(Integer id) throws BeverageException {
		DataBase.getInstance().checkBeverageId(id);
		
		int nCapsules = -1;
		nCapsules = DataBase.getInstance().getBeverageBoxInformation(id, "capPerBox");
		
		if (nCapsules == -1)
			throw new BeverageException("Number of capsules per box for the beverage cannot be retrieved");
		else
			System.out.println("Number of capsules per box: " + nCapsules);

		return nCapsules;
	}

	@Override
	public Integer getBeverageBoxPrice(Integer id) throws BeverageException {
		DataBase.getInstance().checkBeverageId(id);
		
		int boxPrice = -1;
		boxPrice = DataBase.getInstance().getBeverageBoxInformation(id, "boxPrice");
		
		if (boxPrice == -1)
			throw new BeverageException("Box price for the beverage cannot be retrieved");
		else
			System.out.println("Box price: " + boxPrice);
		
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
		DataBase.getInstance().checkBeverageId(id);
		
		int n = DataBase.getInstance().getBeverageAvailableCapsules(id);
		System.out.println("Available beverage capsules: " + n);
		
		return n;
	}

	@Override
	public Integer createEmployee(String name, String surname) throws EmployeeException {
		int empId;
		if(name.isEmpty() || surname.isEmpty()) {
			throw new EmployeeException("Employee cannot be inserted: invalid values");
		}
		if (!DataBase.getInstance().employeeIsDuplicate(name, surname)) {
			empId = DataBase.getInstance().addEmployee(name, surname); 
			System.out.println("Employee correctly inserted");
		} else {
			throw new EmployeeException("Employee already exists");
		}
		return empId;
	}

	@Override
	public void updateEmployee(Integer id, String name, String surname) throws EmployeeException {
		DataBase.getInstance().checkEmp(id);
		
		if(name.isEmpty() || surname.isEmpty()) {
			throw new EmployeeException("Employee cannot be inserted");
		}
		else {
		
			int i = DataBase.getInstance().updateEmp(id, name, surname);
			if(i == 0) System.out.println("Employee correctly updated");
			else throw new EmployeeException("Error");
		
		}
	}

	@Override
	public String getEmployeeName(Integer id) throws EmployeeException {
		String name = null;
		DataBase.getInstance().checkEmp(id);
		name = DataBase.getInstance().getEmpName(id);
		System.out.println("Employee's " + id + " name is " + name);
		return name;
	}

	@Override
	public String getEmployeeSurname(Integer id) throws EmployeeException {
		String surname = null;
		
		DataBase.getInstance().checkEmp(id);
		surname = DataBase.getInstance().getEmpSurname(id);
		System.out.println("Employee surname: " + surname);
		return surname;
	}

	@Override
	public Integer getEmployeeBalance(Integer id) throws EmployeeException {
		Integer balance = 0;
		
		DataBase.getInstance().checkEmp(id);
		balance = DataBase.getInstance().getEmpBalance(id);
		System.out.println("Employee balance: " + balance);
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
		DataBase.getInstance().resetDatabase();
	}

}