package com.phoenyx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AT2Compile {
	public static void main(String[] args){
		ArrayList<ArrayList<String>> code = new ArrayList<ArrayList<String>>();
		ArrayList<String> labels = new ArrayList<String>();
		ArrayList<String> text = new ArrayList<String>();
		ArrayList<String> runFirst = new ArrayList<String>();
		ArrayList<String> rfInstructs = new ArrayList<String>();
		ArrayList<String> instructs = new ArrayList<String>();
		HashMap<String, ArrayList<String>> functions = new HashMap<String, ArrayList<String>>();
		HashMap<String, Integer> userVars = new HashMap<String, Integer>();
		
		String[] varText, config;
		String msg;
		String err = "";
		int index = -1;
		boolean isValid = false;
		
		//Read in AT2 file line by line, remove any comment or blank lines and turn the file into an array;
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Phoenyx\\Desktop\\DOSBox\\atrobots\\sniper.at2"))) {
			String line = "";
			
			
			while(line != null) {
				line = br.readLine();
				
				if(line == null) break;
				if(!(line.matches(";.+|;+|\s+;.+") || line.isBlank())) {
					text.add(line);
					//System.out.println(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Run any code that doesn't come after a function label first
		while(!text.get(0).matches("![a-zA-Z]+|:\\d+")) {
			if(text.get(0).startsWith("#def")) {
				varText = text.get(0).strip().split(" ");
				for(String var : varText) {
					if(!(var.equals("#def") || var.startsWith(";") || var.isBlank())) {
						System.out.println(var);
						userVars.put(var, 0);
					}
				}
			}else if(text.get(0).startsWith("#config")) {
				config = text.get(0).replaceAll(";.+|;+|\s+;.+", "").strip().split(" ");
				for(String c : config) {
					if(!(c.equals("#config"))) {
						System.out.println(c);
					}
				}
			}else if(text.get(0).startsWith("#msg")){
				msg = text.get(0);
				System.out.println(msg);
			}else if(!text.get(0).isBlank() && !text.get(0).startsWith("#")) {
				runFirst.add(text.get(0).strip());
				for(String r : runFirst) {
					System.out.println(r);
				}
			}else {
				String instruction = text.get(0).replaceAll("\s.+", "");
				err = "Robot is not valid! Reason: No instruction called "+instruction+"";
				System.out.println(err);
				break;
			}
			//System.out.println(text.get(0).startsWith("#def"));
			text.remove(0);
		}
		
		//Collect function labels and store them in an array
		//Collect any code that comes after a label and store it an array
		for(int i = 0; i < text.size(); i++) {
			if(text.get(i).matches("![a-zA-z]+|:\\d+")) {
				ArrayList<String> lines = new ArrayList<String>();
				code.add(lines);
				labels.add(text.get(i));
				index++;
			}else if(!code.isEmpty()){
				code.get(index).add(text.get(i).replaceAll(";.+|;+|\s+;.+", "").strip());
			}else {
				break;
			}
		}
		
		for(int i = 0; i < runFirst.size(); i++) {
			String[] rfLines = runFirst.get(i).strip().split(" ");
			for(String rf : rfLines) {
				if(!(rf.isBlank() || rf.matches("\\d+|-\\d+|\\d+.+|\\d+.+\\d+||@\\d+") || rf.contains(",") || rf.matches("ax|bx") || userVars.containsKey(rf))) {
					rfInstructs.add(rf);
				}
			}
		}
		
		for(int i = 0; i < rfInstructs.size(); i++) {
			switch(rfInstructs.get(i)) {
			case "nop":
			case "add":
			case "sub":
			case "inc":
			case "dec":
			case "shl":
			case "sal":
			case "shr":
			case "sar":
			case "rol":
			case "ror":
			case "neg":
			case "or":
			case "and":
			case "xor":
			case "not":
			case "mpy":
			case "div":
			case "mod":
			case "ret":
			case "call":
			case "gsb":
			case "jmp":
			case "goto":
			case "cmp":
			case "jls":
			case "jb":
			case "jgr":
			case "ja":
			case "jne":
			case "je":
			case "jeq":
			case "jge":
			case "jle":
			case "jz":
			case "jnz":
			case "jtl":
			case "xchg":
			case "swap":
			case "do":
			case "loop":
			case "test":
			case "mov":
			case "set":
			case "addr":
			case "get":
			case "put":
			case "int":
			case "ipo":
			case "in":
			case "opo":
			case "out":
			case "del":
			case "push":
			case "pop":
			case "loc":
			case "err":
				isValid = true;
				break;
				default:
					isValid = false;
					err = "isValid: "+isValid+"\nRobot is not valid: No instruction called "+rfInstructs.get(i)+"";
					System.out.println(err);
					break;
			}
			if(!isValid) break;
		}
		
		for(int i = 0; i < code.size(); i++) {
			for(int j = 0; j < code.get(i).size(); j++) {
				String[] lines = code.get(i).get(j).strip().split(" ");
				for(String l : lines) {
					if(!(l.isBlank() || l.matches("\\d+|-\\d+|\\d+.+|\\d+.+\\d+||@\\d+") || l.contains(",") || l.matches("ax|bx") || userVars.containsKey(l))) {
						instructs.add(l);
					}
				}
			}
		}
		
		for(int i = 0; i < instructs.size(); i++) {
			switch(instructs.get(i)) {
			case "nop":
			case "add":
			case "sub":
			case "inc":
			case "dec":
			case "shl":
			case "sal":
			case "shr":
			case "sar":
			case "rol":
			case "ror":
			case "neg":
			case "or":
			case "and":
			case "xor":
			case "not":
			case "mpy":
			case "div":
			case "mod":
			case "ret":
			case "call":
			case "gsb":
			case "jmp":
			case "goto":
			case "cmp":
			case "jls":
			case "jb":
			case "jgr":
			case "ja":
			case "jne":
			case "je":
			case "jeq":
			case "jge":
			case "jle":
			case "jz":
			case "jnz":
			case "jtl":
			case "xchg":
			case "swap":
			case "do":
			case "loop":
			case "test":
			case "mov":
			case "set":
			case "addr":
			case "get":
			case "put":
			case "int":
			case "ipo":
			case "in":
			case "opo":
			case "out":
			case "del":
			case "push":
			case "pop":
			case "loc":
			case "err":
				isValid = true;
				break;
				default:
					isValid = false;
					err = "isValid: "+isValid+"\nRobot is not valid: No instruction called "+instructs.get(i)+"";
					System.out.println(err);
					break;
			}
			if(!isValid) break;
		}
		
		//Compile the functions into a hash map
		if(!isValid) return;
		for(int i = 0; i < labels.size(); i++) {
			functions.put(labels.get(i), code.get(i));
		}
		
		//debugger code. only used for debugging any runtime errors.
		/*for(String test : text) {
			System.out.println(test);
		}*/
		
		/*System.out.println("labels: "+labels+"");
		System.out.println("code: "+code+"");*/
		for(Map.Entry<String, ArrayList<String>> entry : functions.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			System.out.println(""+key+" = "+value+"");
		}
		System.out.println("User variables: "+userVars+"");
		System.out.println("Number of labels: "+labels.size()+"");
		System.out.println("Number of code arrays: "+code.size()+"");
	}
}
