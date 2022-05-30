package com.vending.machine.model;

public class VendingMachineConfig {
	
	int rows;
	int columns;
	
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int coulmns) {
		this.columns = coulmns;
	}
	@Override
	public String toString() {
		return "VendingMachineConfig [rows=" + rows + ", coulmns=" + columns + "]";
	}	
	
}
