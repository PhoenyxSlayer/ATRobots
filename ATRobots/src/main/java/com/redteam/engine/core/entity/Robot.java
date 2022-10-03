package com.redteam.engine.core.entity;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Robot{
	
	private String s, dir;
	private boolean found = false;
	private File directory;
	private String[] flist;
	public boolean getOutside() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Type in for ex. C:\Users\Downloads or /home/name/Downloads etc...
        System.out.print("Enter Directory of Robot: ");
        dir = br.readLine();
        
        // Type in the name of the AT2 file without the .AT2 part
		System.out.print("Enter Robot Name: ");
        s = br.readLine();
     // Create an object of the File class
        directory = new File(dir);
        
        return directorySearch(flist,directory);
	}
	
	public boolean getInside() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Type in the name of the AT2 file without the .AT2 part
		System.out.print("Enter Robot Name: ");
        s = br.readLine();
     // Create an object of the File class
        directory = new File("src/main/resources/AT2/");

     // store all names with same name
   		return directorySearch(flist,directory);
	}
	
	private boolean directorySearch(String[] fileList, File fileDirectory) {
		// store all names with same name
        flist = directory.list();
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
        			return true;
        		}
        	}
        }
        if (!found) {
        	System.err.println("COULD NOT FIND FILE " + s + ".AT2");
        }
        return false;
	}
	
	public String referenceName() {
		return s;
	}
}