package it.polito.latazza.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
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
	private static final String INSERT_PURCH = "INSERT INTO Purchases(date, beverageId, boxQuantity, amount) VALUES(?, ?, ?, ?)";
	
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
	      " account INTEGER, " +								//0 if credit, 1 if cash
	      " employeeId INTEGER REFERENCES Employees(id) ";	
	      
	      statement.executeUpdate(sql);
	      
	      sql = 
		  "DROP TABLE IF EXISTS Recharges;" +
		  "CREATE TABLE Recharges " +
	      "(date DATETIME PRIMARY KEY ," +
		  " employeeId INTEGER REFERENCES Employees(id), " +
	      " amount INTEGER) " ;

	      statement.executeUpdate(sql);
	      
	      sql =
		  "DROP TABLE IF EXISTS Purchases;" +
		  "CREATE TABLE Purchases " +
	      "(date DATETIME PRIMARY KEY," +
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
        	ps.setNull(6, 0);
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
        int count = 0, shared = 0, price = 0, qty = 0, sum_sells = 0, sum_purchase = 0;
        
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
            	sum_sells = rs.getInt(1);
            }
            
            sql = "SELECT SUM(amount) FROM Purchases";
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
            	sum_purchase = rs.getInt(1);
            }
            
            shared = sum_sells - sum_purchase;
            
            if(shared < count*price*boxQuantity) {
            	return -1;
            }
        	
          	ps = this.connection.prepareStatement(INSERT_PURCH);
        	ps.setInt(2, beverageId);
        	ps.setInt(3, boxQuantity);
        	ps.setInt(4, count*price*boxQuantity);
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
	
	public List<String> getEmplRep(Integer employeeId, Date startDate, Date endDate) {
        PreparedStatement ps = null, ps1 = null;
        int empId = 0, bevId = 0, quant = 0, acc = 0;
        Date date;
        List<String> lista = new ArrayList<>();
        String stringa = new String(); 
        String name = null, surname = null, bev_name = null, str = null;
        double amount = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	       
        	String sql1 = "SELECT date, employeeId, beverageId, quantity, account  FROM Sells WHERE employeeId = " + employeeId + " AND date < " + endDate + " AND date > " + startDate;
            ps1  = connection.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            
            while (rs1.next()){
            	date = rs1.getDate(1);
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
                
                sql = "SELECT name, surname FROM Employee WHERE id = " + empId;
                ps  = connection.prepareStatement(sql);
                rs = ps.executeQuery();
                
                while (rs.next()){
                	name = rs.getString(1);
                	surname = rs.getString(2);
                }      
                
                if(acc == 1) {
                	stringa = date.toGMTString() + " BALANCE " + name + " " + surname + " " + bev_name + " " + quant;
                }
                else stringa = date.toGMTString() + " CASH " + name + " " + surname + " " + bev_name + " " + quant;
                
                lista.add(stringa);
            	
            }
            
        	sql1 = "SELECT date, employeeId, amount FROM Recharges WHERE employeeId = " + employeeId + " AND date < " + endDate + " AND date > " + startDate;
            ps1  = connection.prepareStatement(sql1);
            rs1 = ps.executeQuery();
            
            while (rs1.next()){
            	date = rs1.getDate(1);
            	empId = rs1.getInt(2);
            	amount = (rs1.getInt(3))/100; 
            
                String sql = "SELECT name, surname FROM Employee WHERE id = " + empId;
                ps  = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()){
                	name = rs.getString(1);
                	surname = rs.getString(2);
                }      
                
                str = String.format("%.2f \u20ac", amount);
                stringa = date.toGMTString() + " RECHARGE " + name + " " + surname + " " +  str;
                
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
        int empId = 0, bevId = 0, quant = 0, acc = 0;
        Date date;
        List<String> lista = new ArrayList<>();
        String stringa = new String(); 
        String name = null, surname = null, bev_name = null, str = null;
        double amount = 0;
        
        try {        	
        	connect();
        	connection.setAutoCommit(false);
        	       
        	String sql1 = "SELECT date, employeeId, beverageId, quantity, account  FROM Sells WHERE date < " + endDate + " AND date > " + startDate;
            ps1  = connection.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            
            while (rs1.next()){
            	date = rs1.getDate(1);
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
                if (empId!=0) {
	                sql = "SELECT name, surname FROM Employee WHERE id = " + empId;
	                ps  = connection.prepareStatement(sql);
	                rs = ps.executeQuery();
	                
	                while (rs.next()){
	                	name = rs.getString(1);
	                	surname = rs.getString(2);
	                }      
	                
	                if(acc == 1) {
	                	stringa = date.toGMTString() + " BALANCE " + name + " " + surname + " " + bev_name + " " + quant;
	                }
	                else stringa = date.toGMTString() + " CASH " + name + " " + surname + " " + bev_name + " " + quant;
	            }
                else stringa = date.toGMTString() + " VISITOR " + bev_name + " " + quant;
                
                lista.add(stringa);
            	
            }
            
        	sql1 = "SELECT date, employeeId, amount FROM Recharges WHERE date < " + endDate + " AND date > " + startDate;
            ps1  = connection.prepareStatement(sql1);
            rs1 = ps.executeQuery();
            
            while (rs1.next()){
            	date = rs1.getDate(1);
            	empId = rs1.getInt(2);
            	amount = (rs1.getInt(3))/100; 
            
                String sql = "SELECT name, surname FROM Employee WHERE id = " + empId;
                ps  = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()){
                	name = rs.getString(1);
                	surname = rs.getString(2);
                }      
                
                str = String.format("%.2f \u20ac", amount);
                stringa = date.toGMTString() + " RECHARGE " + name + " " + surname + " " +  str;
                
                lista.add(stringa);
            	
            }
            
            sql1 = "SELECT date, beverageId, quant FROM Purchases WHERE date < " + endDate + " AND date > " + startDate;
            ps1  = connection.prepareStatement(sql1);
            rs1 = ps.executeQuery();
            
            while (rs1.next()){
            	date = rs1.getDate(1);
            	bevId = rs1.getInt(2);
            	quant = rs1.getInt(3); 
            	
            	String sql = "SELECT name FROM Beverages WHERE id = " + bevId;
                ps  = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()){
                    bev_name = rs.getString(1);
                }   
            
                stringa = date.toGMTString() + " BUY " + bev_name + " " +  quant;
                
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
		int numRowsInserted = 0, count = 0;
        PreparedStatement ps = null;
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
            ps = this.connection.prepareStatement("UPDATE Employees SET name = ?, surname = ? WHERE id = ?");
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setInt(3, id);
            
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
	
	public String getEmpSurname(Integer id) {
		String surname = null;
        PreparedStatement ps = null;
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
        	
        	String sql = "SELECT surname FROM Employees WHERE id = " + id;
        	ps  = connection.prepareStatement(sql);
        	ResultSet rs  = ps.executeQuery();
        	
        	while (rs.next()){
        		surname = rs.getString(1);
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
        		surname = rs.getString(2);
        		
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
        int shared = 0, sum_sells = 0, sum_purchase = 0;
        
        try {
        	connect();
        	connection.setAutoCommit(false);
        	
            String sql = "SELECT SUM(amount) FROM Sells";
            ps  = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
            	sum_sells = rs.getInt(1);
            }
            
            sql = "SELECT SUM(amount) FROM Purchases";
            ps  = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
            	sum_purchase = rs.getInt(1);
            }
            
            shared = sum_sells - sum_purchase;
            
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

}