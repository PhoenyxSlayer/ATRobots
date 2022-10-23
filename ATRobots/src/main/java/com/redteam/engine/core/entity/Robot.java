package com.redteam.engine.core.entity;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Robot{
	private String s;
	private File file;
	private boolean fSelected = false;
	
	public boolean getFile(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text & AT2 files (.txt, .at2, .AT2)", "txt", "at2", "AT2");
		chooser.setFileFilter(filter);
		if(chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			String fileName = file.getName().substring(0, file.getName().indexOf('.'));
			System.out.println(file.toString());
			System.out.println(fileName);
			fSelected = true;
		}
		return fSelected;
	}
	
	public String referenceName() {
		return s;
	}
}