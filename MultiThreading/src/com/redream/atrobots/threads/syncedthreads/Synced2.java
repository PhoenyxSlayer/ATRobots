package com.redream.atrobots.threads.syncedthreads;

import com.redream.atrobots.SyncedShared;

public class Synced2 implements Runnable{
	private SyncedShared s;
	
	public Synced2(SyncedShared s) {
		this.s = s;
	}

	@Override
	public void run() {
		for(int i = 0; i <= 100000; i++) {
			s.subtract();
		}
	}

}
