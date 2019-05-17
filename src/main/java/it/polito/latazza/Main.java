package it.polito.latazza;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.latazza.data.DataBase;
import it.polito.latazza.data.DataImpl;
import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataBase db = DataBase.getInstance();
		db.createDatabase();
		DataImpl data = new DataImpl();

		
		try {
			
			int empId1 = data.createEmployee("Simone", "Dutto");
			int empId2 = data.createEmployee("Pennello", "Cinghiale");
			int empId3 = data.createEmployee("Vito", "Tassielli");
			int empId4 = data.createEmployee("Lisa", "Romita");
				
			data.rechargeAccount(empId3, 1000);
			data.rechargeAccount(empId2, 1000);
			data.rechargeAccount(empId1, 1000);
			
			System.out.println(empId1+ " " + empId2 + " " + empId3 + " " + empId4);
			
			
			List<Integer> lista = new ArrayList<>();
			lista = data.getEmployeesId();
			
			for(int i=0; i<lista.size(); i++) {
				System.out.println(lista.get(i));
			}
			
			Map<Integer, String> mappa = new HashMap<>();
			mappa = data.getEmployees();
			
			for(int i : mappa.keySet()) {
				System.out.println(i + " " + mappa.get(i));
			}

			
			
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			data.createBeverage("Caffè", 50, 1000);
			data.createBeverage("The", 50, 500);
			data.createBeverage("Camomilla", 50, 750);
			
			Map<Integer, String> bev_map= new HashMap<>();
			bev_map = data.getBeverages();
			
			for(int i : bev_map.keySet()) {
				System.out.println("ID: " + i + " Nome: " + bev_map.get(i));
			}
			
			
		} catch (BeverageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			data.buyBoxes(1, 1);
			data.buyBoxes(2, 1);
			data.buyBoxes(3, 1);
			
			
			data.sellCapsules(1, 1, 25, true);
			data.sellCapsules(3, 3, 10, false);

			data.sellCapsulesToVisitor(3, 15);
			data.sellCapsulesToVisitor(1, 20);

			data.rechargeAccount(3, 59);
			
			Date data2 = new Date();
			Date data3 = new Date(data2.getTime() - 7*1000000);
			Date data4 = new Date(data2.getTime() + 7*1000);
			
			
			List<String> lista = new ArrayList<>();
			lista = data.getEmployeeReport(3, data3, data4);
			
			for(String i : lista) {
				System.out.println(i);
			}
			
			lista = data.getReport(data3, data4);
			
			for(String i : lista) {
				System.out.println(i);
			}
			
			System.out.println("Balance: " + data.getBalance());
			
			
		} catch (BeverageException e) {
			e.printStackTrace();
		} catch (EmployeeException e) {
			e.printStackTrace();
		} catch (NotEnoughCapsules e) {
			e.printStackTrace();
		} catch (NotEnoughBalance e){
			e.printStackTrace();
		} catch (DateException e) {
			e.printStackTrace();
		}
	}
}