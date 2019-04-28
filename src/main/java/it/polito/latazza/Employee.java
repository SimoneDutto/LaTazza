package it.polito.latazza;

public class Employee {
	private String name;
	private String surname;
	private double balance;
	
	public Employee(String name, String surname) {
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
	
	public double getBalance() {
		return this.balance;
	}
	
	public void updateBalance(double amount) {
		this.balance += amount;
		return;
	}
}
