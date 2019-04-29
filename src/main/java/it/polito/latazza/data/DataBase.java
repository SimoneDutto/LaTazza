
package it.polito.latazza.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	
	private static DataBase instance = null;
	private String dbname = "LaTazza.db";
	private Connection connection;
	private static final String INSERT_EMP = "INSERT INTO Employees(name, surname, balance) VALUES(?, ?, ?)";
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
	
	public int addEmployee(String name, String surname) {
		int numRowsInserted = 0, count = 0;
        PreparedStatement ps = null;
        try {
        	connect();
            ps = this.connection.prepareStatement(INSERT_EMP);
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setDouble(3, 0.0);
            
            numRowsInserted = ps.executeUpdate();
            String sql = "SELECT COUNT(*) FROM Employees";
            ps  = connection.prepareStatement(sql);
           
            ResultSet rs  = ps.executeQuery();
            
            while (rs.next()){
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
        }
        if(numRowsInserted == 1)
        	return count;
        else
        	return 0;
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
	      " balance REAL) " ;

	      statement.executeUpdate(sql);
	      
	      sql =
		  "DROP TABLE IF EXISTS Beverages;" +
		  "CREATE TABLE Beverages " +
	      "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
	      " name TEXT NOT NULL, " +
	      " capPerBox iNTEGER, " +
	      " quantity INTEGER"	+
	      " pricePerCapsules REAL) " ;
	      
	      statement.executeUpdate(sql);
	      
	      sql = 
		  "DROP TABLE IF EXISTS Sells;" +
		  "CREATE TABLE Sells " +
	      "(date DATETIME PRIMARY KEY ," +
		  " beverageId TEXT REFERENCES Beverages(id), " +
	      " quantity INTEGER) " ;
	      
	      statement.executeUpdate(sql);
	      
	      sql = 
		  "DROP TABLE IF EXISTS Recharges;" +
		  "CREATE TABLE Recharges " +
	      "(date DATETIME PRIMARY KEY ," +
		  " employeeId TEXT REFERENCES Employees(id), " +
	      " amount REAL) " ;

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


	
}
