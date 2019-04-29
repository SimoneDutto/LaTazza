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
			int empId1 = data.createEmployee("Simone", "Dutto");
			int empId2 = data.createEmployee("Pennello", "Cinghiale");
			System.out.println(empId1+" "+empId2);
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
