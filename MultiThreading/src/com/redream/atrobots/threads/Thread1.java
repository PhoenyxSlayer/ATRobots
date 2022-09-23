package com.redream.atrobots.threads;

public class Thread1 implements Runnable{
	private final String name;
	
	public Thread1(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		for(int i = 0; i <= 10; i++) {
			System.out.println(""+name+": "+i+"\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(name+": finished");
	}
}
