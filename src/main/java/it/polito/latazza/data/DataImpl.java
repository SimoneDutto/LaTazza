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
	public static final boolean DEBUG=true;
	
	public DataImpl() {
		DataBase.getInstance().createDatabase();
	}
	
	public DataImpl(String name_db) {
		DataBase.getInstance().change_name_db(name_db);
	}

	@Override
	public Integer sellCapsules(Integer employeeId, Integer beverageId, Integer numberOfCapsules, Boolean fromAccount)
			throws EmployeeException, BeverageException, NotEnoughCapsules {
		
		if (employeeId == null) {
			throw new EmployeeException("ID of the employee is not valid");
		} else if (beverageId == null) {
			throw new BeverageException("ID of the beverage is not valid");
		} else if (numberOfCapsules == null || numberOfCapsules < 0 || numberOfCapsules > Integer.MAX_VALUE) {
			throw new NotEnoughCapsules("Number of available capsules is insufficient");
		}
		int balance = DataBase.getInstance().sellCap(employeeId, beverageId, numberOfCapsules, fromAccount); 
		if (DEBUG) {
			System.out.println("Sell correctly updated");
			System.out.println("Balance = " + balance);
		}
		return balance;
	
	}

	@Override
	public void sellCapsulesToVisitor(Integer beverageId, Integer numberOfCapsules)
			throws BeverageException, NotEnoughCapsules {
		if(beverageId == null)
			throw new BeverageException("Beverage Id not valid");
		if(numberOfCapsules == null || numberOfCapsules < 0)
			throw new NotEnoughCapsules("Not valid number of capsules");
		
		DataBase.getInstance().sellVis(beverageId, numberOfCapsules); 

		if (DEBUG) System.out.println("Sell correctly updated");
		
	}

	@Override
	public Integer rechargeAccount(Integer id, Integer amountInCents) throws EmployeeException {
		
		if (id == null || amountInCents == null || amountInCents < 0) {
			throw new EmployeeException("Employee cannot be updated");
		}
		
		int value = DataBase.getInstance().recharge(id, amountInCents);

		if (DEBUG) System.out.println("Recharge correctly inserted");
		
		return value;
	}

	@Override
	public void buyBoxes(Integer beverageId, Integer boxQuantity) throws NotEnoughBalance, BeverageException {
		if (boxQuantity == null || boxQuantity < 0 || boxQuantity == Integer.MAX_VALUE)
			throw new BeverageException("Balance available is insufficient");
		DataBase.getInstance().buyB(beverageId, boxQuantity);
		if (DEBUG) System.out.println("Boxes correctly received and paid for");
	}
	
	@Override
	public List<String> getEmployeeReport(Integer employeeId, Date startDate, Date endDate)
			throws EmployeeException, DateException {
		if (employeeId == null) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		DataBase.getInstance().checkEmp(employeeId);
		List<String> value = new ArrayList<String>();
		
		if (startDate == null || endDate == null || startDate.after(endDate) == true ) {
			throw new DateException("Date interval is not valid");
		}
		else {
			java.sql.Date data1 = new java.sql.Date(startDate.getTime());
			java.sql.Date data2 = new java.sql.Date(endDate.getTime() + 86400000);
			
			value = DataBase.getInstance().getEmplRep(employeeId, data1, data2);
			if (DEBUG) System.out.println("Report correctly delivered");
			
		}
		
		return value;
	}

	@Override
	public List<String> getReport(Date startDate, Date endDate) throws DateException {
		List<String> value = new ArrayList<String>();
		
		if (startDate == null || endDate == null || startDate.after(endDate) == true) {
			throw new DateException("Date interval is not valid");
		}
		
		else {
			java.sql.Date data1 = new java.sql.Date(startDate.getTime());
			java.sql.Date data2 = new java.sql.Date(endDate.getTime() + 86400000);
			
			value = DataBase.getInstance().getRep(data1, data2);
			if (DEBUG) System.out.println("Report correctly delivered");
		}
		return value;
	}

	@Override
	public Integer createBeverage(String name, Integer capsulesPerBox, Integer boxPrice) throws BeverageException {
		int bevId; 
		if (name == null || capsulesPerBox == null || boxPrice == null 
			||name.isEmpty() || capsulesPerBox <= 0 || capsulesPerBox > Integer.MAX_VALUE 
			|| boxPrice <= 0 || boxPrice > Integer.MAX_VALUE) {
			throw new BeverageException("Beverage cannot be inserted: invalid values");
		} 
		if (!DataBase.getInstance().beverageIsDuplicate(name)) {
			bevId = DataBase.getInstance().addBeverage(name, capsulesPerBox, boxPrice);
			if (DEBUG) System.out.println("Beverage correctly inserted");
		} else {
			throw new BeverageException("Beverage already exists");
		}
		return bevId;
	}

	@Override
	public void updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice)
			throws BeverageException {
		if (id == null) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		if (name==null || name.isEmpty() || capsulesPerBox==null || capsulesPerBox <= 0 || boxPrice==null || boxPrice <= 0) {
			throw new BeverageException("Beverage cannot be inserted");
		}
		DataBase.getInstance().checkBeverageId(id);
		DataBase.getInstance().updateBeverage(id, name, capsulesPerBox, boxPrice);
		if (DEBUG) System.out.println("Beverage correctly updated");
		
		return;
	}

	@Override
	public String getBeverageName(Integer id) throws BeverageException {
		String name = null;
		
		if (id == null) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		
		DataBase.getInstance().checkBeverageId(id);
		
		name = DataBase.getInstance().getBeverageName(id);
		if (DEBUG) System.out.println("Beverage name is " + name);
		
		return name;
	}

	@Override
	public Integer getBeverageCapsulesPerBox(Integer id) throws BeverageException {
		
		if (id == null) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		
		DataBase.getInstance().checkBeverageId(id);
		
		int nCapsules = -1;
		nCapsules = DataBase.getInstance().getBeverageBoxInformation(id, "capPerBox");
		
		if (nCapsules == -1)
			throw new BeverageException("Number of capsules per box for the beverage cannot be retrieved");
		else
			if (DEBUG) System.out.println("Number of capsules per box: " + nCapsules);

		return nCapsules;
	}

	@Override
	public Integer getBeverageBoxPrice(Integer id) throws BeverageException {
		if (id == null) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		
		DataBase.getInstance().checkBeverageId(id);
		
		int boxPrice = -1;
		boxPrice = DataBase.getInstance().getBeverageBoxInformation(id, "boxPrice");
		
		if (boxPrice == -1)
			throw new BeverageException("Box price for the beverage cannot be retrieved");
		else
			if (DEBUG) System.out.println("Box price: " + boxPrice);
		
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
		if (id == null) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		
		DataBase.getInstance().checkBeverageId(id);
		
		int n = DataBase.getInstance().getBeverageAvailableCapsules(id);
		if (DEBUG) System.out.println("Available beverage capsules: " + n);
		
		return n;
	}

	@Override
	public Integer createEmployee(String name, String surname) throws EmployeeException {
		int empId;
		if(name == null || surname == null || name.isEmpty() || surname.isEmpty()) {
			throw new EmployeeException("Employee cannot be inserted: invalid values");
		}
		if (!DataBase.getInstance().employeeIsDuplicate(name, surname)) {
			empId = DataBase.getInstance().addEmployee(name, surname); 
			if (DEBUG) System.out.println("Employee correctly inserted");
		} else {
			throw new EmployeeException("Employee already exists");
		}
		return empId;
	}

	@Override
	public void updateEmployee(Integer id, String name, String surname) throws EmployeeException {
		if (id == null) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		
		if(name == null || name.isEmpty() || surname == null || surname.isEmpty()) {
			throw new EmployeeException("Employee cannot be inserted");
		}
		
		DataBase.getInstance().checkEmp(id);
		int i = DataBase.getInstance().updateEmp(id, name, surname);
		if(i == 0) {
			if (DEBUG) 
				System.out.println("Employee correctly updated");
		}
	}

	@Override
	public String getEmployeeName(Integer id) throws EmployeeException {
		String name = null;
		if (id == null) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		
		DataBase.getInstance().checkEmp(id);
		name = DataBase.getInstance().getEmpName(id);
		if (DEBUG) System.out.println("Employee's " + id + " name is " + name);
		return name;
	}

	@Override
	public String getEmployeeSurname(Integer id) throws EmployeeException {
		String surname = null;
		if (id == null) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		DataBase.getInstance().checkEmp(id);
		surname = DataBase.getInstance().getEmpSurname(id);
		if (DEBUG) System.out.println("Employee surname: " + surname);
		return surname;
	}

	@Override
	public Integer getEmployeeBalance(Integer id) throws EmployeeException {
		Integer balance = 0;
		if (id == null) {
			throw new EmployeeException("ID of the employee is not valid");
		}
		DataBase.getInstance().checkEmp(id);
		balance = DataBase.getInstance().getEmpBalance(id);
		if (DEBUG) System.out.println("Employee balance: " + balance);
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