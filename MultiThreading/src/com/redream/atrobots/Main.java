package com.redream.atrobots;

import com.redream.atrobots.threads.Thread1;
import com.redream.atrobots.threads.Thread2;
import com.redream.atrobots.threads.Thread3;
import com.redream.atrobots.threads.Thread4;
import com.redream.atrobots.threads.syncedthreads.Synced1;
import com.redream.atrobots.threads.syncedthreads.Synced2;
import com.redream.atrobots.threads.unsyncedthreads.Unsynced1;
import com.redream.atrobots.threads.unsyncedthreads.Unsynced2;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		SyncedShared s = new SyncedShared();
		UnsyncedShared u = new UnsyncedShared();
		Thread t1 = new Thread(new Thread1("Thread 1"));
		Thread t2 = new Thread(new Thread2("Thread 2"));
		Thread t3 = new Thread(new Thread3("Thread 3"));
		Thread t4 = new Thread(new Thread4("Thread 4"));
		Thread u1 = new Thread(new Unsynced1(u));
		Thread u2 = new Thread(new Unsynced2(u));
		Thread s1 = new Thread(new Synced1(s));
		Thread s2 = new Thread(new Synced2(s));
		//SyncedShared sync = new SyncedShared();
		//sync.setTotal(total);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		
		System.out.println("Starting Value: "+s.getTotal()+"");
		
		u1.start();
		u2.start();
		u1.join();
		u2.join();
		
		u.printTotal();
		
		s1.start();
		s2.start();
		s1.join();
		s2.join();
		
		s.printTotal();
	}
}
