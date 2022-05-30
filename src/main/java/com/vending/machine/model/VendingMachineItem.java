package com.vending.machine.model;

public class VendingMachineItem {
	
	String name;
	int amount;
	String price;
	String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "VendingMachineItem [name=" + name + ", amount=" + amount + ", price=" + price + ", id=" + id + "]";
	}

}
