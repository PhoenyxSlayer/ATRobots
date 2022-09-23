package com.redream.atrobots;

public class SyncedShared {
	private int total = 10;
	
	public synchronized void add() {
		total++;
	}
	
	public synchronized void subtract() {
		total--;
	}
	
	public synchronized int getTotal() {
		int t = total;
		return t;
	}
	
	public void printTotal() {
		System.out.println("Synchronized Value: "+total+"");
	}
}
