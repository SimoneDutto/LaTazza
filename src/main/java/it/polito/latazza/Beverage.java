package it.polito.latazza;

public class Beverage {
	String name;
	int boxPrice;
	int capsulesPerBox;
	int quantity;
	float pricePerCapsule;
	
	public Beverage(String name, int boxPrice, int capsulesPerBox, int quantity, int pricePerCapsule) {
		this.name = name;
		this.boxPrice = boxPrice;
		this.capsulesPerBox = capsulesPerBox;
		this.quantity = quantity;
		this.pricePerCapsule = (float) boxPrice/capsulesPerBox;
	}
	public String getName() {
		return name;
	}
	public int getBoxPrice() {
		return boxPrice;
	}
	public int getCapsulesPerBox() {
		return capsulesPerBox;
	}
	public int getQuantity() {
		return quantity;
	}
	public float getPriceForCapsule() {
		return	pricePerCapsule;
	}
	

	
	
}
