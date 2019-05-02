package it.polito.latazza.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DataBase {
	
	private static DataBase instance = null;
	private String dbname = "LaTazza.db";
	private Connection connection;
	private static final String INSERT_EMP = "INSERT INTO Employees(name, surname, balance) VALUES(?, ?, ?)";
	private static final String UPDATE_BEV = "UPDATE Beverages SET quantity = ? WHERE id = ?";
	private static final String UPDATE_EMP = "UPDATE Employees SET balance = ? WHERE id = ?";
	private static final String INSERT_SELL = "INSERT INTO Sells(date, beverageID, quantity, amount, account, employeeId) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String INSERT_RECH = "INSERT INTO Recharges(date, employeeId, amount) VALUES(?, ?, ?)";
	
	/*
	 * This function create the database from the start, dropping already existing tables
	 */
	private DataBase() {
		try {
			Class.forName("org.sqlite.JDBC");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public int addEmployee(String name, String surname) {
		int numRowsInserted = 0, count = 0;
        PreparedStatement ps = null;
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
            ps = this.connection.prepareStatement(INSERT_EMP);
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setDouble(3, 0.0);
            
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0)
            	connection.rollback();
            
            String sql = "SELECT COUNT(*) FROM Employees";
            ps  = connection.prepareStatement(sql);
           
            ResultSet rs  = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
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
        return count;
    }
	
	
	public void createDatabase() {
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
	      " balance INTEGER) " ;

	      statement.executeUpdate(sql);
	      
	      sql =
		  "DROP TABLE IF EXISTS Beverages;" +
		  "CREATE TABLE Beverages " +
	      "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
	      " name TEXT NOT NULL, " +
	      " capPerBox INTEGER, " +
	      " quantity INTEGER, "	+
	      " pricePerCapsules INTEGER) " ;
	      
	      statement.executeUpdate(sql);
	      
	      sql = 
		  "DROP TABLE IF EXISTS Sells;" +
		  "CREATE TABLE Sells " +
		  "(date DATETIME PRIMARY KEY ," +
		  " beverageId INTEGER REFERENCES Beverages(id), " +
		  " quantity INTEGER, " +
		  " amount INTEGER, " +
	      " account INTEGER, " +								//0 if credit, 1 if cash, 2 if buyBoxes
	      " employeeId INTEGER REFERENCES Employees(id) ";		//-1 if visitor, 0 if manager (per buyBoxes)
	      
	      statement.executeUpdate(sql);
	      
	      sql = 
		  "DROP TABLE IF EXISTS Recharges;" +
		  "CREATE TABLE Recharges " +
	      "(date DATETIME PRIMARY KEY ," +
		  " employeeId INTEGER REFERENCES Employees(id), " +
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
	
	public int sellCap(Integer employeeId, Integer beverageId, Integer numberOfCapsules, Boolean fromAccount) {
        PreparedStatement ps = null;
        int numRowsInserted = 0, count = 0;
        int balance = 0, price = 0, account = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	
        	String sql = "SELECT COUNT(*) FROM Employees WHERE id = " + employeeId;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == 0) {
            	return -3;
            }
       
        	sql = "SELECT COUNT(*) FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == 0) {
            	return -1;
            }
            
        	sql = "SELECT quantity, pricePerCapsules FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
                price = rs.getInt(2);
            }
            
            if(count < numberOfCapsules) {
            	return -2;
            }          
            
        	sql = "SELECT balance FROM Employee WHERE id = " + employeeId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                balance = rs.getInt(1);
            }          
        	
        	ps = this.connection.prepareStatement(UPDATE_BEV);
        	ps.setInt(2, beverageId);
        	ps.setInt(1, count-numberOfCapsules);
        	
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0)
            	connection.rollback();
            
            if(fromAccount==true) {
            	ps = this.connection.prepareStatement(UPDATE_EMP);
            	ps.setInt(2, employeeId);
            	ps.setDouble(1, balance-(numberOfCapsules*price));
            	account=1;
            }
            
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0)
            	connection.rollback();
            
        	ps = this.connection.prepareStatement(INSERT_SELL);
        	ps.setInt(2, beverageId);
        	ps.setInt(3, count);
        	ps.setInt(4, numberOfCapsules*price);
        	ps.setInt(5, account);
        	ps.setInt(6, employeeId);
        	Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
        	Date d = c.getTime();
        	
        	ps.setDate(1,(java.sql.Date) d);
        	
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
        return count;
    }

	public int sellVis(Integer beverageId, Integer numberOfCapsules) {
        PreparedStatement ps = null;
        int numRowsInserted = 0, count = 0, price = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	       
        	String sql = "SELECT COUNT(*) FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == 0) {
            	return -2;
            }
            
        	sql = "SELECT quantity, pricePerCapsules FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
                price = rs.getInt(2);
            }
            
            if(count < numberOfCapsules) {
            	return -1;
            }          
            
        	
        	ps = this.connection.prepareStatement(UPDATE_BEV);
        	ps.setInt(2, beverageId);
        	ps.setInt(1, count-numberOfCapsules);
        	
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0)
            	connection.rollback();
            
        	ps = this.connection.prepareStatement(INSERT_SELL);
        	ps.setInt(2, beverageId);
        	ps.setInt(3, numberOfCapsules);
        	ps.setInt(4, numberOfCapsules*price);
        	ps.setInt(5, 0);
        	ps.setInt(6, -1);
        	ps.setDate(1, (java.sql.Date) Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY).getTime());
        	
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
        return count;
    }

	public int recharge(Integer id, Integer amountInCents) {
        PreparedStatement ps = null;
        int numRowsInserted = 0;
        int count = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	       
        	String sql = "SELECT COUNT(*) FROM Employees WHERE id = " + id;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == -1) {
            	return 0;
            }
            
        	sql = "SELECT balance FROM Employee WHERE id = " + id;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
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
        	ps.setDouble(3, count+amountInCents);
        
        	ps.setDate(1, (java.sql.Date) Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY).getTime());
        	
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
	
	public int buyB(Integer beverageId, Integer boxQuantity) {
		PreparedStatement ps = null;
        int numRowsInserted = 0;
        int count = 0, shared = 0, price = 0, qty = 0;
        
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
        	String sql = "SELECT COUNT(*) FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == 0) {
            	return -2;
            }
            
            
        	sql = "SELECT capPerBox, pricePerCapsules, quantity FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
                price = rs.getInt(2);
                qty = rs.getInt(3);
            }
            
            sql = "SELECT SUM(amount) FROM Sells";
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
            	shared = rs.getInt(1);
            }
            
            if(shared < count*price*boxQuantity) {
            	return -1;
            }
        	
          	ps = this.connection.prepareStatement(INSERT_SELL);
        	ps.setInt(2, beverageId);
        	ps.setInt(3, boxQuantity*count);
        	ps.setInt(4, -count*price*boxQuantity);
        	ps.setInt(5, 2);
        	ps.setInt(6, 0);
        	ps.setDate(1, (java.sql.Date) Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY).getTime());
        	
            numRowsInserted = ps.executeUpdate();
            if(numRowsInserted == 0)
            	connection.rollback();
            
        	ps = this.connection.prepareStatement(UPDATE_BEV);
        	ps.setInt(2, beverageId);
        	ps.setInt(1, qty+count*boxQuantity);
        	
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
        return count;
	}
	
	public int checkEmp(Integer employeeId) {
		PreparedStatement ps = null;
        int numRowsInserted = 0;
        int count = 0;
        
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
        	String sql = "SELECT COUNT(*) FROM Employees WHERE id = " + employeeId;
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
            }
            
            if(count == 0) {
            	return -1;
            }
        	
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
        return count;
	}
	
	

}