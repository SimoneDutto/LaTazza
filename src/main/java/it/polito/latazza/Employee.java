package it.polito.latazza;

import it.polito.latazza.exceptions.*;

public class Employee {
	private String name;
	private String surname;
	private double balance;
	
	public Employee(String name, String surname) throws EmployeeException {
		this.name = name;
		this.surname = surname;
		this.balance = 0.0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public double getBalance() throws NotEnoughBalance {
		return this.balance;
	}
	
	public void updateBalance(double amount) throws NotEnoughBalance {
		this.balance = amount;
		return;
	}
}
