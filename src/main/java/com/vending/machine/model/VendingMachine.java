package com.vending.machine.model;

import java.util.ArrayList;

public class VendingMachine {
	
	VendingMachineConfig config;
	ArrayList<VendingMachineItem> items;
	
	
	
	public VendingMachineConfig getConfig() {
		return config;
	}



	public void setConfig(VendingMachineConfig config) {
		this.config = config;
	}



	public ArrayList<VendingMachineItem> getItems() {
		return items;
	}



	public void setItems(ArrayList<VendingMachineItem> items) {
		this.items = items;
	}



	@Override
	public String toString() {
		return "VendingMachine [config=" + config + ", items=" + items.toString() + "]";
	}
	
	
	
	
}
