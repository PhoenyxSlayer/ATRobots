package com.redteam.engine.core.entity;
import java.io.File;
import java.util.Scanner;

public class Robot{
	
	private String s, dir;
	private boolean found = false;
	private File directory;
	private String[] flist;
	
	
	public boolean getFile(){
		Scanner scan = new Scanner(System.in);		
		System.out.print("Is the Robot you want to run in the src directory? (yes/no): ");
		String y = new String();
		y = scan.nextLine();
		if(y.equalsIgnoreCase("yes")) {
				System.out.println("Looking for robot inside of src...");
		System.out.print("Enter Name of Robot");
		s = scan.next();
		directory = new File("src/main/resources/AT2/");
		
		scan.close();
		
		return directorySearch(flist, directory);
		}
		else {
		System.out.print("Enter Directory of Robot: ");
		 dir = scan.next();
		        // Type in the name of the AT2 file without the .AT2 part
		System.out.print("Enter Robot Name: ");
		s = scan.next();
		     // Create an object of the File class
		directory = new File(dir);

		scan.close();
		                
		return directorySearch(flist,directory);
		}
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