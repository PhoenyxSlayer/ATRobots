package com.redream.atrobots.threads;

public class Thread4 implements Runnable{
	private final String name;
	
	public Thread4(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		for(int i = 30; i <= 40; i++) {
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
