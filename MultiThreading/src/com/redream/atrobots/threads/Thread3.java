package com.redream.atrobots.threads;

public class Thread3 implements Runnable{
	private final String name;
	
	public Thread3(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		for(int i = 20; i <= 30; i++) {
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
