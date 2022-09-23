package com.redream.atrobots.threads.unsyncedthreads;

import com.redream.atrobots.UnsyncedShared;

public class Unsynced1 implements Runnable{
	private UnsyncedShared u;
	
	public Unsynced1(UnsyncedShared u) {
		this.u = u;
	}
	
	@Override
	public void run() {
		for(int i = 0; i <= 100000; i++) {
			u.add();
		}
	}
}
