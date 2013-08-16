package jtextviewer;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class IOStream {	

	private ArrayList <CurrentFilePath> cfpList = new ArrayList <CurrentFilePath>();
	private String path;

	public void open(Component parent, JFileChooser jfc, JTextArea textArea, JFrame frame){			
		FileInputStream fis = null;
		path = jfc.getSelectedFile().getAbsolutePath();
		
		try{			
			fis = new FileInputStream(path);
			String str = "";
			int b = 0;
			while (b != -1){
				b = fis.read();
				str += (char) b;				
			}			
			fis.close();
			textArea.setText(str);					
		}catch(IOException ex){
			JOptionPane.showMessageDialog(parent, "Cannot open current document. IO Error.","IO Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void saveAs(Component parent, JFileChooser jfc, JTextArea textArea, boolean displayAfterSave){
		int i = 0;
		try {					
			FileOutputStream fos = new FileOutputStream(jfc.getSelectedFile().getAbsolutePath());			
			while (i != textArea.getText().length()){
				fos.write(textArea.getText().charAt(i));
				i++;
			}
			fos.close();
		}catch (IOException ex){
			JOptionPane.showMessageDialog(parent, "Cannot save current document. IO Error.","IO Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void save(Component parent, JFileChooser jfc, JTextArea textArea){
		int i = 0;
		try {					
			FileOutputStream fos = new FileOutputStream(jfc.getSelectedFile().getAbsolutePath());			
			while (i != textArea.getText().length()){
				fos.write(textArea.getText().charAt(i));
				i++;
			}
			fos.close();
		}catch (IOException ex){
			JOptionPane.showMessageDialog(parent, "Cannot save current document. IO Error.","IO Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
