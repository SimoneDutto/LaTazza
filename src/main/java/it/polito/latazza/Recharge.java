package it.polito.latazza;

public class Recharge {
	String date;
	int amount;
	
	public Recharge(String date, int amount) {
		this.date = date;
		this.amount = amount;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getAmount() {
		return amount;
	}
	
}
