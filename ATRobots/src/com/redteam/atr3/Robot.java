package com.redteam.atr3;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Robot{
	
	private String s, dir;
	private int flag;
	private File directory;
	private String[] flist;
	public void getName() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Type in for ex. C:\Users\Downloads or /home/name/Downloads etc...
        System.out.print("Enter Directory of Robot: ");
        dir = br.readLine();
        
        // Type in the name of the AT2 file without the .AT2 part
		System.out.print("Enter Robot Name: ");
        s = br.readLine();
     // Create an object of the File class
        directory = new File(dir);

     // store all names with same name
        		flist = directory.list();
        		flag = 0;
        		if (flist == null) {
        			System.out.println("Empty directory.");
        			s = null;
        		}
        		else {
      // Linear search in the array
        			for (int i = 0; i < flist.length; i++) {
        				String filename = flist[i];
        				if (filename.equalsIgnoreCase(s + ".AT2")) {
        					System.out.println(filename + " found!");
        					flag = 1;
        				}
        			}
        		}
        		if (flag == 0) {
        			System.out.println("File Not Found");
        }
	}
	public String referenceName() {
		return s;
	}
	
	public boolean equals () {
		if (flist != null)
			return true;
		else 
			return false;
		
	}
}