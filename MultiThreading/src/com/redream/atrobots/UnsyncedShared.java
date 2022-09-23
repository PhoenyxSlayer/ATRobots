package com.redream.atrobots;

public class UnsyncedShared {
	private int total = 10;
	
	public void add() {
		total++;
	}
	
	public void subtract() {
		total--;
	}
	
	public void printTotal() {
		System.out.println("Unsynchronized Value: "+total+"");
	}
}
