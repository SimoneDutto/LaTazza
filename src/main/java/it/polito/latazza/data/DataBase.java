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
import java.util.HashMap;
import java.util.Map;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;

import java.util.List;

public class DataBase {
	
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
	      " pricePerCapsule INTEGER, " +
	      " boxPrice INTEGER ) " ;
	      
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
            
            System.out.println("SELL TO EMPLOYEE: se TRUE da crediti: " + fromAccount);
            
        	sql = "SELECT quantity, pricePerCapsule FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
                price = rs.getInt(2);
                
                System.out.println("Sell: id=" + beverageId + " quantità_disp=" + count + " pricePerCapsule=" + price);
            }
            
            if(count < numberOfCapsules) {
            	return -2;
            }          
            
        	sql = "SELECT balance FROM Employees WHERE id = " + employeeId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                balance = rs.getInt(1);
                System.out.println("EmplId: " + employeeId + " Crediti: " + balance);
            }          
        	
        	ps = this.connection.prepareStatement(UPDATE_BEV_QTY);
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
        	ps.setInt(3, numberOfCapsules);
        	ps.setInt(4, numberOfCapsules*price);
        	ps.setInt(5, account);
        	ps.setInt(6, employeeId);

        	Date date = new Date();
        	ps.setLong(1, date.getTime());
        	
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
            
        	sql = "SELECT quantity, pricePerCapsule FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
                price = rs.getInt(2);
            }
            
            if(count < numberOfCapsules) {
            	return -1;
            }          
            
        	
        	ps = this.connection.prepareStatement(UPDATE_BEV_QTY);
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
        	ps.setNull(6, 0);

        	Date date = new Date();
        	ps.setLong(1, date.getTime());
        	
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
            
            if(count == 0) {
            	return -1;
            }
            
        	sql = "SELECT balance FROM Employees WHERE id = " + id;
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
        	ps.setDouble(3, amountInCents);
        	
        	Date date = new Date();
        	ps.setLong(1, date.getTime());
        	
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
        int sum_sells = 0, sum_rec = 0, sum_purchase = 0;
        
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
            
            
        	sql = "SELECT capPerBox, pricePerCapsule, quantity FROM Beverages WHERE id = " + beverageId;
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
                price = rs.getInt(2);
                qty = rs.getInt(3);
            }
            
            
            sql = "SELECT SUM(amount) FROM Sells WHERE account = 0";
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
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
            

            
            if(shared < count*price*boxQuantity) {
            	return -1;
            }
        	
          	ps = this.connection.prepareStatement(INSERT_PURCH);
        	ps.setInt(2, beverageId);
        	ps.setInt(3, boxQuantity);
        	ps.setInt(4, count*price*boxQuantity);
        	
        	Date date = new Date();
        	ps.setLong(1, date.getTime());
        	
            numRowsInserted = ps.executeUpdate();
            
            if(numRowsInserted == 0)
            	connection.rollback();
            
        	ps = this.connection.prepareStatement(UPDATE_BEV_QTY);
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
	
	public int checkEmp(Integer employeeId) throws EmployeeException {
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
            try {
				connection.rollback();
				e.printStackTrace();
				throw new EmployeeException("Employee ID not retrieved: query execution failed");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new EmployeeException("Employee ID not retrieved: rollback failed");
			}
            
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new EmployeeException("Employee ID not retrieved: connection closing failed");
			}
        }
        return count;
	}
	
	public List<String> getEmplRep(Integer employeeId, Date startDate, Date endDate) {
        PreparedStatement ps = null, ps1 = null;
        int empId = 0, bevId = 0, quant = 0, acc = 0;
        long date_long;
        List<String> lista = new ArrayList<>();
        String stringa = new String(); 
        String name = null, surname = null, bev_name = null, str = null;
        double amount = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	       
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
                
                while (rs.next()){
                    bev_name = rs.getString(1);
                }      
                
                sql = "SELECT name, surname FROM Employees WHERE id = " + empId;
                ps  = connection.prepareStatement(sql);
                rs = ps.executeQuery();
                
                while (rs.next()){
                	name = rs.getString(1);
                	surname = rs.getString(2);
                }      
                
                Date date = new Date(date_long);
            	SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    			String dateString=sdf.format(date);
                
                if(acc == 1) {
                	
                	stringa = dateString + " BALANCE " + name + " " + surname + " " + bev_name + " " + quant;
                }
                else stringa = dateString + " CASH " + name + " " + surname + " " + bev_name + " " + quant;
                
                lista.add(stringa);
            	
            }
            
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
                
                Date date = new Date(date_long);
            	SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    			String dateString=sdf.format(date);
                
                str = String.format("%.2f \u20ac", amount);
                stringa = dateString + " RECHARGE " + name + " " + surname + " " +  str;
                
                lista.add(stringa);
            	
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
	
	public List<String> getRep(Date startDate, Date endDate) {
        PreparedStatement ps = null, ps1 = null;
        long date_long;
        int empId = 0, bevId = 0, quant = 0, acc = 0;
        List<String> lista = new ArrayList<>();
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
                
                while (rs.next()){
                    bev_name = rs.getString(1);
                }      
                
                Date date = new Date(date_long);
            	SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    			String dateString=sdf.format(date);
                
                if (empId!=0) {
	                sql = "SELECT name, surname FROM Employees WHERE id = " + empId;
	                ps  = connection.prepareStatement(sql);
	                rs = ps.executeQuery();
	                
	                while (rs.next()){
	                	name = rs.getString(1);
	                	surname = rs.getString(2);
	                }      
	                
	                if(acc == 1) {
	                	stringa = dateString + " BALANCE " + name + " " + surname + " " + bev_name + " " + quant;
	                }
	                else stringa = dateString + " CASH " + name + " " + surname + " " + bev_name + " " + quant;
	            }
                else stringa = dateString + " VISITOR " + bev_name + " " + quant;
                
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
                
                Date date = new Date(date_long);
            	SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    			String dateString=sdf.format(date);
                
                str = String.format("%.2f \u20ac", amount);
                stringa = dateString + " RECHARGE " + name + " " + surname + " " +  str;
                
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
            
                Date date = new Date(date_long);
            	SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    			String dateString=sdf.format(date);
                
                stringa = dateString + " BUY " + bev_name + " " +  quant;
                
                lista.add(stringa);
            	
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

	
	public int updateEmp(Integer id, String name, String surname) {
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
            	return -1;
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
        	
        	while (rs.next()){
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
            try {
				connection.rollback();
				e.printStackTrace();
				throw new EmployeeException("Employee surname not retrieved: query execution failed");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new EmployeeException("Employee surname not retrieved: rollback failed");
			}
            
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
	
	public int getEmpBalance(Integer id) {
		int balance = 0;
        PreparedStatement ps = null;
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
        	
        	String sql = "SELECT balance FROM Employees WHERE id = " + id;
        	ps  = connection.prepareStatement(sql);
        	ResultSet rs  = ps.executeQuery();
        	
        	while (rs.next()){
        		balance = rs.getInt(1);
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
		int numRowsInserted = 0, count = 0;
        PreparedStatement ps = null;
        
        try {
        	connect();
        	this.connection.setAutoCommit(false);
        	
        	// Insert new Beverage in DB
        	ps = this.connection.prepareStatement(INSERT_BEV);
        	ps.setString(1, name);
        	ps.setInt(2, capsulesPerBox);
        	ps.setInt(3, 0);
        	ps.setInt(4, (Integer) boxPrice/capsulesPerBox);
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
			
			String sql = "SELECT COUNT(*) FROM Beverages WHERE name = '" + name +"';";
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
			
			while(rs.next()) {
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
	
	public Integer updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice) {
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
				return -1;
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
	
	public String getBeverageName(Integer beverageId) {
		PreparedStatement ps = null;
		String name = null;
		
		try {
			connect();
			this.connection.setAutoCommit(false);
			
			String sql = "SELECT name FROM Beverages WHERE id = " + beverageId;
			ps = this.connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
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
	
	public Integer getBeverageBoxInformation(Integer beverageId, String requiredInformation) {
		PreparedStatement ps = null;
		int boxInfo = -1;
		
		try {
			connect();
			this.connection.setAutoCommit(false);
			
			String sql = "SELECT " + requiredInformation + " FROM Beverages WHERE id = " + beverageId;
			ps = this.connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
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
			while(rs.next()) {
				qty = rs.getInt(1);
				if(rs.wasNull())
					qty = -1;
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
	
}