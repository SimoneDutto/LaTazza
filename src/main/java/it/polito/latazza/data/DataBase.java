package it.polito.latazza.data;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughCapsules;
import it.polito.latazza.exceptions.NotEnoughBalance;

import java.util.List;

public class DataBase {
	private static final boolean DEBUG = DataImpl.DEBUG;
	private static DataBase instance = null;
	private String dbname = "LaTazza.db";
	private Connection connection;
	private static final String INSERT_EMP = "INSERT INTO Employees(name, surname, balance) VALUES(?, ?, ?)";
	private static final String UPDATE_BEV_QTY = "UPDATE Beverages SET quantity = ? WHERE id = ?";
	private static final String UPDATE_EMP = "UPDATE Employees SET balance = ? WHERE id = ?";
	private static final String INSERT_SELL = "INSERT INTO Sells(date, beverageID, quantity, amount, account, employeeId) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String INSERT_RECH = "INSERT INTO Recharges(date, employeeId, amount) VALUES(?, ?, ?)";
	private static final String INSERT_PURCH = "INSERT INTO Purchases(date, beverageId, boxQuantity, amount) VALUES(?, ?, ?, ?)";
	private static final String INSERT_BEV = "INSERT INTO Beverages(name, capPerBox, quantity, pricePerCapsule, boxPrice) VALUES(?, ?, ?, ?, ?)";
	private static final String UPDATE_BEV = "UPDATE Beverages SET name = ?, capPerBox = ?, pricePerCapsule = ?, boxPrice = ? WHERE id = ?";
	
	
	
	/*
	 * This function creates the database from the start, dropping already existing tables
	 */
	private DataBase() {
		try {
			Class.forName("org.sqlite.JDBC");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void change_name_db(String new_name_db) {
		dbname = new_name_db;
	}
	
	public static DataBase getInstance() {
		if(instance == null) {
			instance = new DataBase();
		}
		return instance;
	}
	
	private void connect() throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:"+dbname);
	}
	
	/*
	 * Function to insert an Employee in the DB
	 */
	public int addEmployee(String name, String surname) throws EmployeeException {
		int numRowsInserted = 0, count = 0;
        PreparedStatement ps = null;
        
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
        	// Insert new Employee in the DB
            ps = this.connection.prepareStatement(INSERT_EMP);
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setInt(3, 0);
            
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0) {
            	connection.rollback();
            	throw new EmployeeException("Employee not inserted");
            }
            
            // Check presence of new Employee
            String sql = "SELECT COUNT(*) FROM Employees";
            ps  = connection.prepareStatement(sql);
           
            ResultSet rs  = ps.executeQuery();
            
            // Retrieve EmployeeId
            if (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == 0) {
            	throw new EmployeeException("Employee not inserted");
            }
            
            connection.commit();

        } catch (SQLException e) {
            try {
				connection.rollback();
				e.printStackTrace();
				throw new EmployeeException("Employee not inserted: commit failed");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new EmployeeException("Employee not inserted: rollback failed");
			}
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new EmployeeException("Employee not inserted: connection closing failed");
			}
        }
        
        // Return new EmployeeId
        return count;
    }
	
	public void createDatabase() {
		try
	    {
		  connect();
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);
	      
	      String sql = 
	      "CREATE TABLE IF NOT EXISTS Employees" +
	      "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
	      " name TEXT NOT NULL, " +
	      " surname TEXT NOT NULL, " +
	      " balance INTEGER) " ;

	      statement.executeUpdate(sql);
	      
	       sql =
	      "DROP TABLE IF EXISTS Beverages;" +
	      "CREATE TABLE Beverages " +
	      "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
	      " name TEXT NOT NULL, " +
	      " capPerBox INTEGER, " +
	      " quantity INTEGER, "	+
	      " pricePerCapsule INTEGER, " +
	      " boxPrice INTEGER,"+
	      " oldPricePerCap INTEGER," +
	      " oldQty INTEGER)" ;
	      
	      statement.executeUpdate(sql);
	      
	      sql = 
		  "CREATE TABLE IF NOT EXISTS Sells" +
		  "(date BIGINT PRIMARY KEY," +
		  " beverageId INTEGER REFERENCES Beverages(id), " +
		  " quantity INTEGER, " +
		  " amount INTEGER, " +
	      " account INTEGER, " +								//1 if credit, 0 if cash
	      " employeeId INTEGER REFERENCES Employees(id))" ;	
	      
	      statement.executeUpdate(sql);
	      
	      sql = 
		  "CREATE TABLE IF NOT EXISTS Recharges" +
	      "(date BIGINT PRIMARY KEY," +
		  " employeeId INTEGER REFERENCES Employees(id), " +
	      " amount INTEGER) " ;

	      statement.executeUpdate(sql);
	      
	      sql =
		  "CREATE TABLE IF NOT EXISTS Purchases" +
	      "(date BIGINT PRIMARY KEY," +
	      " beverageId INTEGER REFERENCES Beverages(id), " +
	      " boxQuantity INTEGER, " +
	      " amount INTEGER) " ;
	      
	      statement.executeUpdate(sql);
	    }
	    catch(SQLException e)
	    {
	      System.err.println(e.getMessage());
	    }
	    finally
	    {
	      try
	      {
	        if(connection != null)
	          connection.close();
	      }
	      catch(SQLException e)
	      {
	        // connection close failed.
	        System.err.println(e);
	      }
	    }
	}
	
	public void resetDatabase() {
		// Loading class
		try
	    {
		  connect();
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);
	      
	      String sql = 
	      "DROP TABLE IF EXISTS Employees;" +
		  "CREATE TABLE Employees " +
	      "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
	      " name TEXT NOT NULL, " +
	      " surname TEXT NOT NULL, " +
	      " balance INTEGER)" ;

	      statement.executeUpdate(sql);
	      
	      sql =
	      "DROP TABLE IF EXISTS Beverages;" +
	      "CREATE TABLE Beverages " +
	      "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
	      " name TEXT NOT NULL, " +
	      " capPerBox INTEGER, " +
	      " quantity INTEGER, "	+
	      " pricePerCapsule INTEGER, " +
	      " boxPrice INTEGER )" ;
	      
	      statement.executeUpdate(sql);
	      
	      sql = 
		  "DROP TABLE IF EXISTS Sells;" +
		  "CREATE TABLE Sells " +
		  "(date BIGINT PRIMARY KEY," +
		  " beverageId INTEGER REFERENCES Beverages(id), " +
		  " quantity INTEGER, " +
		  " amount INTEGER, " +
	      " account INTEGER, " +								//1 if credit, 0 if cash
	      " employeeId INTEGER REFERENCES Employees(id))" ;	
	      
	      statement.executeUpdate(sql);
	      
	      sql = 
		  "DROP TABLE IF EXISTS Recharges;" +
		  "CREATE TABLE Recharges " +
	      "(date BIGINT PRIMARY KEY," +
		  " employeeId INTEGER REFERENCES Employees(id), " +
	      " amount INTEGER) " ;

	      statement.executeUpdate(sql);
	      
	      sql =
		  "DROP TABLE IF EXISTS Purchases;" +
		  "CREATE TABLE Purchases " +
	      "(date BIGINT PRIMARY KEY," +
	      " beverageId INTEGER REFERENCES Beverages(id), " +
	      " boxQuantity INTEGER, " +
	      " amount INTEGER) " ;
	      
	      statement.executeUpdate(sql);
	    }
	    catch(SQLException e)
	    {
	      System.err.println(e.getMessage());
	    }
	    finally
	    {
	      try
	      {
	        if(connection != null)
	          connection.close();
	      }
	      catch(SQLException e)
	      {
	        // connection close failed.
	        System.err.println(e);
	      }
	    }
	}
	
	public boolean employeeIsDuplicate(String name, String surname) throws EmployeeException {
		PreparedStatement ps = null;
		int count = 0;
		
		try {
			connect();
			
			String sql = "SELECT COUNT(*) FROM Employees WHERE name = '" + name +"' AND surname = '" + surname + "';";
			ps = this.connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			if (count != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
    		throw new EmployeeException("Beverage duplicate check failed");
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new EmployeeException("Beverage duplicate check failed");
			}
		}
		return false;
	}
	
	
	public int sellCap(Integer employeeId, Integer beverageId, Integer numberOfCapsules, Boolean fromAccount) 
	throws EmployeeException, NotEnoughCapsules, BeverageException{
        PreparedStatement ps = null;
        int numRowsInserted = 0, count = 0;
        int balance = 0, price = 0, account = 0;
        double newBalance = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	// checking validity of employeeId
        	String sql = "SELECT COUNT(*) FROM Employees WHERE id = " + employeeId;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
            	count = rs.getInt(1);
            }
            
            if(count == 0) {
            	throw new EmployeeException("ID of the employee is not valid");
            }
            // checking validity of beverageId
        	sql = "SELECT COUNT(*) FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            count = 0;
            if(rs.next()) {
            	count = rs.getInt(1);
            }
            if(count == 0) {
            	throw new BeverageException("ID of the beverage is not valid");
            }
            
            if (DEBUG) System.out.println("SELL TO EMPLOYEE: if TRUE from account: " + fromAccount);
            
            // retrieving price of capsules
        	sql = "SELECT quantity, pricePerCapsule FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            count = 0;
            if(rs.next()) {
	            count = rs.getInt(1);
	            price = rs.getInt(2);
            }
      
            if (DEBUG) System.out.println("Sell: id=" + beverageId + " remaining_quantity=" + count + " pricePerCapsule=" + price);
            
            
            if(count < numberOfCapsules) {
            	throw new NotEnoughCapsules("Number of available capsules is insufficient");
            }          
            
            // retrieving balance from employeeId
        	sql = "SELECT balance FROM Employees WHERE id = " + employeeId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if(rs.next()) {
	            balance = rs.getInt(1);
	            if (DEBUG) System.out.println("EmplId: " + employeeId + " Crediti: " + balance);
            }  
            
        	// update beverage quantity
        	ps = this.connection.prepareStatement(UPDATE_BEV_QTY);
        	ps.setInt(2, beverageId);
        	ps.setInt(1, count-numberOfCapsules);
        	
            numRowsInserted = ps.executeUpdate();
            
            if(numRowsInserted == 0) {
            	connection.rollback();
            	throw new BeverageException("Beverage not inserted");
            }
            
            // update employee balance
            if(fromAccount==true) {
            	ps = this.connection.prepareStatement(UPDATE_EMP);
            	ps.setInt(2, employeeId);
            	newBalance = (balance-(numberOfCapsules*price));
            	ps.setDouble(1, newBalance);
            	//System.out.println("new balance= " + newBalance);
            	account=1;
            	numRowsInserted = ps.executeUpdate();
            	if(numRowsInserted == 0) {
                	connection.rollback();
                	throw new EmployeeException("Employee balance not updated");
                }
            } else
            	newBalance = balance;
            
            // insert sell
            
        	ps = this.connection.prepareStatement(INSERT_SELL);
        	ps.setInt(2, beverageId);
        	ps.setInt(3, numberOfCapsules);
        	ps.setInt(4, numberOfCapsules*price);
        	ps.setInt(5, account);
        	ps.setInt(6, employeeId);

        	Date date = new Date();
        	ps.setLong(1, date.getTime());
        	
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0) {
            	connection.rollback();
            	throw new EmployeeException("Sell not inserted");
            }
            
            connection.commit();
        	

        } catch (SQLException e) {
            try {
				connection.rollback();
				throw new EmployeeException("Sell not performed");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return (int)newBalance;
    }

	
	public int sellVis(Integer beverageId, Integer numberOfCapsules) 
			throws BeverageException, NotEnoughCapsules{
        PreparedStatement ps = null;
        int numRowsInserted = 0, count = 0, price = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	// checking beverageId    
        	String sql = "SELECT COUNT(*) FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            count = 0;
            if (rs.next()){
                count = rs.getInt(1);
            }
            if(count == 0) {
            	throw new BeverageException("ID of the beverage is not valid");
            }
            // retrieve price and quantity from beverages
        	sql = "SELECT quantity, pricePerCapsule FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()){
                count = rs.getInt(1);
                price = rs.getInt(2);
            }
            if(count < numberOfCapsules) {
            	throw new NotEnoughCapsules("Number of available capsules is insufficient");
            }          
            // update beverage
        	ps = this.connection.prepareStatement(UPDATE_BEV_QTY);
        	ps.setInt(2, beverageId);
        	ps.setInt(1, count-numberOfCapsules);
        	
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0) {
            	connection.rollback();
            	throw new BeverageException("Cannot update Beverages");
            }
            // insert sell
        	ps = this.connection.prepareStatement(INSERT_SELL);
        	ps.setInt(2, beverageId);
        	ps.setInt(3, numberOfCapsules);
        	ps.setInt(4, numberOfCapsules*price);
        	ps.setInt(5, 0);
        	ps.setNull(6, 0);

        	Date date = new Date();
        	ps.setLong(1, date.getTime());
        	
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0) {
            	connection.rollback();
            	throw new BeverageException("Sell not inserted");
            }
            connection.commit();
        	

        } catch (SQLException e) {
            try {
				connection.rollback();
				throw new BeverageException("Sell not performed");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return count;
    }

	public int recharge(Integer id, Integer amountInCents) throws EmployeeException {
        PreparedStatement ps = null;
        int numRowsInserted = 0;
        int count = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	
        	String sql = "SELECT COUNT(*) FROM Employees WHERE id = " + id;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == 0) {
    			throw new EmployeeException("ID of the employee is not valid");

            }
            
        	sql = "SELECT balance FROM Employees WHERE id = " + id;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()){
                count = rs.getInt(1);
            }          
            
        	
        	ps = this.connection.prepareStatement(UPDATE_EMP);
        	ps.setInt(2, id);
        	ps.setDouble(1, count+amountInCents);
        	
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0)
            	connection.rollback();
            
        	ps = this.connection.prepareStatement(INSERT_RECH);
        	ps.setInt(2, id);
        	ps.setDouble(3, amountInCents);
        	
        	Date date = new Date();
        	ps.setLong(1, date.getTime());
        	
        	
        	if (DEBUG) System.out.println();
        	
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0)
            	connection.rollback();
            
            connection.commit();
        	

        } catch (SQLException e) {
            try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return count+amountInCents;
    }
	
	public void buyB(Integer beverageId, Integer boxQuantity) throws NotEnoughBalance, BeverageException {
		PreparedStatement ps = null;
        int numRowsInserted = 0;
        int count = 0, shared = 0, price = 0, qty = 0;
        int sum_sells = 0, sum_rec = 0, sum_purchase = 0;
        
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
        	String sql = "SELECT COUNT(*) FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            
            if (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == 0) {
            	throw new BeverageException("ID of the beverage is not valid");
            }
            
            
        	sql = "SELECT capPerBox, pricePerCapsule, quantity FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()){
                count = rs.getInt(1);
                price = rs.getInt(2);
                qty = rs.getInt(3);
            }
            
            sql = "SELECT SUM(amount) FROM Sells WHERE account = 0";
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()){
            	sum_sells = rs.getInt(1);
            }
            
            sql = "SELECT SUM(amount) FROM Recharges";
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()){
            	sum_rec = rs.getInt(1);
            }
            
            sql = "SELECT SUM(amount) FROM Purchases";
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()){
            	sum_purchase = rs.getInt(1);
            }
            shared = sum_sells + sum_rec - sum_purchase;

            if(shared < count*price*boxQuantity) {
            	throw new NotEnoughBalance("Balance is insufficient");
            }
        	
          	ps = this.connection.prepareStatement(INSERT_PURCH);
        	ps.setInt(2, beverageId);
        	ps.setInt(3, boxQuantity);
        	ps.setInt(4, count*price*boxQuantity);
        	
        	Date date = new Date();
        	ps.setLong(1, date.getTime());
        	
            numRowsInserted = ps.executeUpdate();
            
            if(numRowsInserted == 0) {
            	connection.rollback();
            	throw new BeverageException("Not update Beverage table");
            }
            	
            
        	ps = this.connection.prepareStatement(UPDATE_BEV_QTY);
        	ps.setInt(2, beverageId);
        	ps.setInt(1, qty+count*boxQuantity);
        	
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0) {
            	connection.rollback();
            	throw new BeverageException("Not update Beverage table");
            }
            
            
            connection.commit();

        } catch (SQLException e) {
            try {
				connection.rollback();
				throw new BeverageException("Not update Beverage table");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	public void checkEmp(Integer employeeId) throws EmployeeException {
		PreparedStatement ps = null;
        int count = 0;
        
        try {
        	connect();
        	
        	String sql = "SELECT COUNT(*) FROM Employees WHERE id = " + employeeId;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == 0) {
            	throw new EmployeeException("ID of the employee is not valid");
            }

        } catch (SQLException e) {
			e.printStackTrace();
			throw new EmployeeException("Employee ID not retrieved: query execution failed");            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new EmployeeException("Employee ID not retrieved: connection closing failed");
			}
        }
        return;
	}
	
	public List<String> getEmplRep(Integer employeeId, Date startDate, Date endDate) throws EmployeeException{
        PreparedStatement ps = null, ps1 = null;
        int empId = 0, bevId = 0, quant = 0, acc = 0;
        long date_long;
        List<String> lista = new ArrayList<>();
        List<String> lista2 = new ArrayList<>();
        String stringa = new String(); 
        String name = null, surname = null, bev_name = null, str = null;
        double amount = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	// queries about employee buys of capsules 
        	String sql1 = "SELECT date, employeeId, beverageId, quantity, account  FROM Sells WHERE employeeId = " + employeeId + " AND date < " + endDate.getTime() + " AND date > " + startDate.getTime();
            ps1  = connection.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            
            while (rs1.next()){
            	date_long = rs1.getLong(1);
            	empId = rs1.getInt(2);
            	bevId = rs1.getInt(3);
            	quant = rs1.getInt(4);
            	acc = rs1.getInt(5);
            	
            	String sql = "SELECT name FROM Beverages WHERE id = " + bevId;
                ps  = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()){
                    bev_name = rs.getString(1);
                }      
                
                sql = "SELECT name, surname FROM Employees WHERE id = " + empId;
                ps  = connection.prepareStatement(sql);
                rs = ps.executeQuery();
                
                if (rs.next()){
                	name = rs.getString(1);
                	surname = rs.getString(2);
                }      
                
                if(acc == 1) {
                	
                	stringa = date_long + " BALANCE " + name + " " + surname + " " + bev_name + " " + quant;
                } else stringa = date_long + " CASH " + name + " " + surname + " " + bev_name + " " + quant;
                
                lista.add(stringa);
            	
            }
            
            // queries about employee recharges
        	sql1 = "SELECT date, employeeId, amount FROM Recharges WHERE employeeId = " + employeeId + " AND date < " + endDate.getTime() + " AND date > " + startDate.getTime();
            ps1  = connection.prepareStatement(sql1);
            rs1 = ps1.executeQuery();
            
            while (rs1.next()){
            	date_long = rs1.getLong(1);
            	empId = rs1.getInt(2);
            	amount = ((double)rs1.getInt(3))/100; 
            
                String sql = "SELECT name, surname FROM Employees WHERE id = " + empId;
                ps  = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()){
                	name = rs.getString(1);
                	surname = rs.getString(2);
                }      
                
                str = String.format("%.2f \u20ac", amount);
                String str2 = str.replace(",", ".");
                stringa = date_long + " RECHARGE " + name + " " + surname + " " +  str2;
                
                lista.add(stringa);
            	
            }
         
            Collections.sort(lista);
        	for(int i = 0; i < lista.size(); i++) {
            	String[] millis = lista.get(i).split(" ");
            	long data = Long.parseLong(millis[0]);
            	Date date = new Date(data);
            	SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    			String dateString=sdf.format(date);
    			
    			String str_tmp = dateString;
    			int j;
    			
    			for(j=1; j<millis.length; j++) {
    				str_tmp += " " + millis[j]; 
    			}
    			
    			lista2.add(str_tmp);
    			
            }
            
            connection.commit();
        	

        } catch (SQLException e) {
            try {
				connection.rollback();
				throw new EmployeeException("Cannot retrieve report");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        return lista2;
    }
	
	public List<String> getRep(java.sql.Date startDate, java.sql.Date endDate) {
        PreparedStatement ps = null, ps1 = null;
        long date_long;
        int empId = 0, bevId = 0, quant = 0, acc = 0;
        List<String> lista = new ArrayList<>();
        List<String> lista2 = new ArrayList<>();
        String stringa = new String(); 
        String name = null, surname = null, bev_name = null, str = null;
        double amount = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	       	       
        	String sql1 = "SELECT date, employeeId, beverageId, quantity, account  FROM Sells WHERE date < " + endDate.getTime() + " AND date > " + startDate.getTime();
            ps1  = connection.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            
            while (rs1.next()){
            	date_long = rs1.getLong(1);
            	empId = rs1.getInt(2);
            	bevId = rs1.getInt(3);
            	quant = rs1.getInt(4);
            	acc = rs1.getInt(5);
            	
            	String sql = "SELECT name FROM Beverages WHERE id = " + bevId;
                ps  = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()){
                    bev_name = rs.getString(1);
                }      
                
                if (empId!=0) {
	                sql = "SELECT name, surname FROM Employees WHERE id = " + empId;
	                ps  = connection.prepareStatement(sql);
	                rs = ps.executeQuery();
	                
	                if (rs.next()){
	                	name = rs.getString(1);
	                	surname = rs.getString(2);
	                }      
	                
	                if(acc == 1) {
	                	stringa = date_long + " BALANCE " + name + " " + surname + " " + bev_name + " " + quant;
	                }
	                else stringa = date_long + " CASH " + name + " " + surname + " " + bev_name + " " + quant;
	            }
                else stringa = date_long + " VISITOR " + bev_name + " " + quant;
                
                lista.add(stringa);
            	
            }
            
        	sql1 = "SELECT date, employeeId, amount FROM Recharges WHERE date < " + endDate.getTime() + " AND date > " + startDate.getTime();
            ps1  = connection.prepareStatement(sql1);
            rs1 = ps1.executeQuery();
            
            while (rs1.next()){
            	date_long = rs1.getLong(1);
            	empId = rs1.getInt(2);
            	amount = ((double) rs1.getInt(3))/100; 
            
                String sql = "SELECT name, surname FROM Employees WHERE id = " + empId;
                ps  = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()){
                	name = rs.getString(1);
                	surname = rs.getString(2);
                }      
                
                str = String.format("%.2f \u20ac", amount);
                String str2 = str.replace(",", ".");
                stringa = date_long + " RECHARGE " + name + " " + surname + " " +  str2;
                
                lista.add(stringa);
            	
            }
            
            sql1 = "SELECT date, beverageId, boxQuantity FROM Purchases WHERE date < " + endDate.getTime() + " AND date > " + startDate.getTime();
            ps1  = connection.prepareStatement(sql1);
            rs1 = ps1.executeQuery();
            
            while (rs1.next()){
            	date_long = rs1.getLong(1);
            	bevId = rs1.getInt(2);
            	quant = rs1.getInt(3); 
            	
            	String sql = "SELECT name FROM Beverages WHERE id = " + bevId;
                ps  = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()){
                    bev_name = rs.getString(1);
                }   
                
                stringa = date_long + " BUY " + bev_name + " " +  quant;
                
                lista.add(stringa);
            	
            }
            
            Collections.sort(lista);
        	for(int i = 0; i < lista.size(); i++) {
            	String[] millis = lista.get(i).split(" ");
            	long data = Long.parseLong(millis[0]);
            	Date date = new Date(data);
            	SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    			String dateString=sdf.format(date);
    			
    			String str_tmp = dateString;
    			int j;
    			
    			for(j=1; j<millis.length; j++) {
    				str_tmp += " " + millis[j]; 
    			}
    			
    			lista2.add(str_tmp);
    			
            }
            
            connection.commit();
        	

        } catch (SQLException e) {
            try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return lista2;
    }

	
	public int updateEmp(Integer id, String name, String surname) throws EmployeeException {
		int numRowsInserted = 0;
        PreparedStatement ps = null;
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
            ps = this.connection.prepareStatement("UPDATE Employees SET name = ?, surname = ? WHERE id = ?");
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setInt(3, id);
            
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0) {
            	connection.rollback();
            	throw new EmployeeException("ID of the employee is not valid");
            }
            connection.commit();

        } catch (SQLException e) {
            try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return 0;
    }
	
	public String getEmpName(Integer id) {
		String name = null;
        PreparedStatement ps = null;
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
        	String sql = "SELECT name FROM Employees WHERE id = " + id;
        	ps  = connection.prepareStatement(sql);
        	ResultSet rs  = ps.executeQuery();
        	
        	if (rs.next()){
                name = rs.getString(1);
            }
            
            connection.commit();
            
        } catch (SQLException e) {
            try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return name;
    }
	
	/*
	 * Employee surname getter
	 * */
	public String getEmpSurname(Integer id) throws EmployeeException {
		String surname = null;
        PreparedStatement ps = null;
        try {
        	connect();
        	
        	String sql = "SELECT surname FROM Employees WHERE id = " + id;
        	ps  = connection.prepareStatement(sql);
        	ResultSet rs  = ps.executeQuery();
        	
        	if (rs.next()){
        		surname = rs.getString(1);
            }
        	if (rs.wasNull())
        		throw new EmployeeException("Employee surname could not be retrieved");
            
        } catch (SQLException e) {
			e.printStackTrace();
			throw new EmployeeException("Employee surname not retrieved: query execution failed");
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new EmployeeException("Employee surname not retrieved: connection closing failed");
			}
        }
        return surname;
    }
	
	/*
	 * Employee balance getter
	 * */
	public int getEmpBalance(Integer id) throws EmployeeException {
		int balance = 0;
        PreparedStatement ps = null;
        try {
        	connect();
        	
        	String sql = "SELECT balance FROM Employees WHERE id = " + id;
        	ps  = connection.prepareStatement(sql);
        	ResultSet rs  = ps.executeQuery();
        	
        	if (rs.next()){
        		balance = rs.getInt(1);
            }
            if (rs.wasNull()) {
            	throw new EmployeeException("Employee balance could not be retrieved");
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        	throw new EmployeeException("Employee balance could not be retrieved: query execution failed");        	
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new EmployeeException("Employee balance could not be retrieved: connection closing failed");
			}
        }
        return balance;
    }
	
	public List<Integer> getIds() {
		List<Integer> lista = new ArrayList<>();
        PreparedStatement ps = null;
        int id = 0;
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
        	String sql = "SELECT id FROM Employees";
        	ps  = connection.prepareStatement(sql);
        	ResultSet rs  = ps.executeQuery();
        	
        	while (rs.next()){
        		id = rs.getInt(1);
        		lista.add(id);
            }
            
            connection.commit();
            
        } catch (SQLException e) {
            try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return lista;
    }
	
	public Map<Integer,String> getMap() {
		Map<Integer, String> mappa = new HashMap<>();
        PreparedStatement ps = null;
        int id = 0;
        String name = null, surname = null;
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
        	String sql = "SELECT id, name, surname FROM Employees";
        	ps  = connection.prepareStatement(sql);
        	ResultSet rs  = ps.executeQuery();
        	
        	while (rs.next()){
        		id = rs.getInt(1);
        		name = rs.getString(2);
        		surname = rs.getString(3);
        		
        		mappa.put(id, name + " " + surname);
            }
            
            connection.commit();
            
        } catch (SQLException e) {
            try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return mappa;
    }
		
	public int getBal() {
		PreparedStatement ps = null;
        int shared = 0, sum_sells = 0, sum_purchase = 0, sum_rec = 0;
        
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
            String sql = "SELECT SUM(amount) FROM Sells WHERE account = 0";
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
            	sum_sells = rs.getInt(1);
            }
            
            sql = "SELECT SUM(amount) FROM Recharges";
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
            	sum_rec = rs.getInt(1);
            }
            
            sql = "SELECT SUM(amount) FROM Purchases";
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
            	sum_purchase = rs.getInt(1);
            }
            
            shared = sum_sells + sum_rec - sum_purchase;
            
            connection.commit();

        } catch (SQLException e) {
            try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return shared;
	}

	/*
	 * Method to add a new Beverage in the DB
	 * */
	public Integer addBeverage(String name, Integer capsulesPerBox, Integer boxPrice) throws BeverageException{
		int numRowsInserted = 0, count = 0, pricePerCapsules = 0;
        PreparedStatement ps = null;
        
        try {
        	connect();
        	this.connection.setAutoCommit(false);
        	pricePerCapsules = boxPrice/capsulesPerBox;
        	
        	// Insert new Beverage in DB
        	ps = this.connection.prepareStatement(INSERT_BEV);
        	ps.setString(1, name);
        	ps.setInt(2, capsulesPerBox);
        	ps.setInt(3, 0);
        	ps.setInt(4, pricePerCapsules);
        	ps.setInt(5, boxPrice);
        	
        	numRowsInserted = ps.executeUpdate();
        	if(numRowsInserted == 0) {
        		this.connection.rollback();
        		throw new BeverageException("Beverage not inserted");
        	}
        	
        	// Check presence of new beverage
        	String sql = "SELECT COUNT(*) FROM BEVERAGES";
        	ps = this.connection.prepareStatement(sql);
        	
        	ResultSet rs = ps.executeQuery();
        	
        	// Retrieve BeverageId
        	if(rs.next()) {
        		count = rs.getInt(1);
        	}
        	
        	if(count == 0) {
        		throw new BeverageException("Beverage not inserted");
        	}
        	
        	this.connection.commit();
        	
        } catch (SQLException e) {
        	try {
        		this.connection.rollback();
        		e.printStackTrace();
        		throw new BeverageException("Beverage not inserted: commit failed");
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        		throw new BeverageException("Beverage not inserted: rollback failed");
        	}
        	
        } finally {
        	try {
        		this.connection.close();
        	} catch (SQLException e2) {
        		e2.printStackTrace();
        		throw new BeverageException("Beverage not inserted: connection closing failed");
        	}
        }
        // return new BeverageId
        return count;
	}
	
	/*
	 * Method that checks presence of duplicate beverages (same name) in the DB
	 * */
	public boolean beverageIsDuplicate(String name) throws BeverageException {
		PreparedStatement ps = null;
		int count = 0;
		
		try {
			connect();
			
			String sql = "SELECT COUNT(*) FROM Beverages WHERE name = '" + name +"'";
			ps = this.connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			if (count != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
    		throw new BeverageException("Beverage duplicate check failed");
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BeverageException("Beverage duplicate check failed");
			}
		}
		return false;
	}
	
	public Integer checkBeverageId(Integer beverageId) throws BeverageException {
		PreparedStatement ps = null;
		int count = 0;
		
		try {
			connect();
			
			String sql = "SELECT COUNT(*) FROM Beverages WHERE id = " + beverageId;
			ps = this.connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			if (count == 0)
				throw new BeverageException("ID of the beverage is not valid");
			
		} catch (SQLException e) {
        	e.printStackTrace();
        	throw new BeverageException("Beverage ID could not be retrieved");
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BeverageException("Beverage ID could not be retrieved: closing connection failed");
			}
		}
		return count;
	}
	
	public Integer updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice) throws BeverageException {
		PreparedStatement ps = null;
		int numRowsUpdated = 0;
		
		try {
			connect();
			this.connection.setAutoCommit(false);
			
			ps = this.connection.prepareStatement(UPDATE_BEV);
			ps.setString(1, name);
			ps.setInt(2, capsulesPerBox);
			ps.setInt(3, (Integer) boxPrice/capsulesPerBox);
			ps.setInt(4, boxPrice);
			ps.setInt(5, id);
			
			numRowsUpdated = ps.executeUpdate();
			if (numRowsUpdated == 0) {
				this.connection.rollback();
				throw new BeverageException("Beverage cannot be updated");
			} else
				this.connection.commit();
			
		} catch (SQLException e) {
			try {
        		this.connection.rollback();
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        	e.printStackTrace();
        	
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
	
	public String getBeverageName(Integer beverageId) throws BeverageException {
		PreparedStatement ps = null;
		String name = null;
		
		try {
			connect();
			this.connection.setAutoCommit(false);
			
			String sql = "SELECT name FROM Beverages WHERE id = " + beverageId;
			ps = this.connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				name = rs.getString(1);
			}
			
		} catch (SQLException e) {
			try {
        		this.connection.rollback();
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        	e.printStackTrace();
        	
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return name;
	}
	
	public Integer getBeverageBoxInformation(Integer beverageId, String requiredInformation) throws BeverageException {
		PreparedStatement ps = null;
		int boxInfo = -1;
		
		try {
			connect();
			this.connection.setAutoCommit(false);
			
			String sql = "SELECT " + requiredInformation + " FROM Beverages WHERE id = " + beverageId;
			ps = this.connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				boxInfo = rs.getInt(1);
				if (rs.wasNull())
					boxInfo = -1;
			}
			
		} catch (SQLException e) {
			try {
        		this.connection.rollback();
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        	e.printStackTrace();
        	
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boxInfo;
	}
	
	public List<Integer> getBeverageIds() {
		List<Integer> ids = new ArrayList<>();
		PreparedStatement ps = null;
		int id = 0;
		
		try {
			connect();
			this.connection.setAutoCommit(false);
			
			String sql = "SELECT id FROM Beverages";
			ps = this.connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getInt(1);
				if (rs.wasNull()) {
					break;
				}
				ids.add(id);
			}
		} catch (SQLException e) {
			try {
        		this.connection.rollback();
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        	e.printStackTrace();
        	
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		
		return ids;
	}
	
	public Map<Integer, String> getBeverages() {
		Map<Integer, String> beverages = new HashMap<>();
		PreparedStatement ps = null;
		String name;
		Integer id;
		
		try {
			connect();
			this.connection.setAutoCommit(false);
			
			String sql = "SELECT id, name FROM Beverages";
			ps = this.connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				name = rs.getString(2);
				if(rs.wasNull())
					break;
				beverages.put(id, name);
			}
					
		} catch (SQLException e) {
			try {
        		this.connection.rollback();
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        	e.printStackTrace();
        	
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		return beverages;
	}
	
	public Integer getBeverageAvailableCapsules(Integer beverageId) {
		PreparedStatement ps = null;
		Integer qty = -1;
		
		try {
			connect();
			this.connection.setAutoCommit(false);
			
			String sql = "SELECT quantity FROM Beverages WHERE id = " + beverageId;
			ps = this.connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				qty = rs.getInt(1);
			} 
			
		} catch (SQLException e) {
			try {
        		this.connection.rollback();
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        	e.printStackTrace();
        	
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		return qty;
	}
	
	public Integer getBeverageNumberOfOldCapsules(Integer beverageId) throws BeverageException {
		PreparedStatement ps = null;
		Integer oldQuantity = -1;
		
		if (checkBeverageId(beverageId)!= 1) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		
		try {
			connect();
			
			String sql = "SELECT oldQty FROM Beverages WHERE id = " + beverageId;
			ps = this.connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				oldQuantity = rs.getInt(1);
			} 
			
		} catch (SQLException e) {
			try {
        		this.connection.rollback();
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        	e.printStackTrace();
        	
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		return oldQuantity;
	}
	
	public Integer getBeverageOldCapsulesPrice(Integer beverageId) throws BeverageException {
		PreparedStatement ps = null;
		Integer oldPrice = -1;
		
		if (checkBeverageId(beverageId)!= 1) {
			throw new BeverageException("ID of the beverage is not valid");
		}
		
		try {
			connect();
			
			String sql = "SELECT oldPricePerCap FROM Beverages WHERE id = " + beverageId;
			ps = this.connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				oldPrice = rs.getInt(1);
			} 
			
		} catch (SQLException e) {
			try {
        		this.connection.rollback();
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        	e.printStackTrace();
        	
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		return oldPrice;
	}
	
}