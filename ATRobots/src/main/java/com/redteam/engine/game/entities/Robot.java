package com.redteam.engine.game.entities;
import java.io.File;
import java.util.Scanner;

public class Robot{
	
	private String s;
	private File directory;

	@SuppressWarnings("unused")
	public boolean getFile(){
		Scanner scan = new Scanner(System.in);		
		System.out.print("Is the Robot you want to run in the src directory?(yes/no): ");
		String y;
		y = scan.nextLine();
		if(y.equalsIgnoreCase("yes")) {		
			System.out.println("Looking for Robot inside of src...");
								
		System.out.print("Enter Name of Robot: ");
		s = scan.next();
		
		directory = new File("src/main/resources/AT2/");

		}
		else{
		System.out.print("Enter Directory of Robot: ");
			String dir = scan.next();
		        // Type in the name of the AT2 file without the .AT2 part
		System.out.print("Enter Robot Name: ");
		s = scan.next();
		     // Create an object of the File class
		directory = new File(dir);

		}
		scan.close();
		return directorySearch();
	}
		
	private boolean directorySearch() {
		// store all names with same name
		String[] fList = directory.list();
        if (fList == null) {
        	System.out.println("Empty directory.");
        	s = null;
        }
        else {
      // Linear search in the array
			for (String filename : fList) {
				if (filename.equalsIgnoreCase(s + ".AT2")) {
					System.out.println(filename + " found!");
					return true;
				}
			}
        }
		boolean found = false;
		if (!found) {
        	System.err.println("COULD NOT FIND FILE " + s + ".AT2");
        	System.err.println("Check directory.");
        }
        return false;
	}

	@SuppressWarnings("unused")
	public String referenceName() {
		return s;
	}
}