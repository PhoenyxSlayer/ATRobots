package com.phoenyx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TextParsing {
	public static void main(String[] args){
		ArrayList<ArrayList<String>> code = new ArrayList<ArrayList<String>>();
		ArrayList<String> labels = new ArrayList<String>();
		HashMap<String, ArrayList<String>> functions = new HashMap<String, ArrayList<String>>();
		ArrayList<String> text = new ArrayList<String>();
		ArrayList<String> global = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Phoenyx\\Desktop\\DOSBox\\atrobots\\PEASHOOT.AT2"))) {
			String line = "";
			
			
			while(line != null) {
				line = br.readLine();
				
				if(line == null) break;
				if(!(line.matches(";.+|;+|\s+;.+") || line.isBlank())) {
					text.add(line);
				}
				/*if(line.matches("![a-zA-z]+")) {
					lineNum = 0;
					labels.add(line);
					//System.out.println("Labels: "+labels+"");
				}
				if(line.matches("\s+.+") && !(line.matches(";\s*.+|\s+;\s*.+"))){
					ArrayList<String> lines = new ArrayList<String>();
					//System.out.println(line.stripLeading());
					code.add(lines);
					code.get(lineNum).add(line.stripLeading().stripTrailing());
					lineNum++;
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*for(int i = 0; i < labels.size(); i++) {
			functions.put(labels.get(i), code.get(i));
		}*/
		
		for(int i = 0; i < text.size(); i++) {
			if(text.get(i).matches("![a-zA-z]+")) {
				ArrayList<String> lines = new ArrayList<String>();
				code.add(lines);
				labels.add(text.get(i));
			}
		}
		for(String test : text) {
			System.out.println(test);
		}
		System.out.println("global lines: "+global+"");
		System.out.println("labels: "+labels+"");
		System.out.println("code: "+code+"");
		/*code.get(1).add("hello");
		System.out.println(code.get(1));*/
		//System.out.println("functions: "+functions+"");
		System.out.println("Number of labels: "+labels.size()+"");
		System.out.println("Number of code arrays: "+code.size()+"");
	}
}
