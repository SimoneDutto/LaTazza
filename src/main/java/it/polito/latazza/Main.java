package it.polito.latazza;

import it.polito.latazza.data.DataBase;
import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.EmployeeException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataBase db = DataBase.getInstance();
		db.createDatabase();
		DataImpl data = new DataImpl();
		try {
			data.createEmployee("Simone", "Dutto");
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
