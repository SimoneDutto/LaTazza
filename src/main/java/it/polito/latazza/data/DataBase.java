
package it.polito.latazza.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	
	static String dbname = "LaTazza.db";
	/*
	 * This function create the database from the start, dropping already existing tables
	 */
	public static void createDatabase() {
		Connection connection = null;
		// Loading class
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
	    {
	      connection = DriverManager.getConnection("jdbc:sqlite:"+dbname);
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
