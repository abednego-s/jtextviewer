package jtextviewer;

import javax.swing.filechooser.FileFilter;
import java.io.File;

class Filter extends FileFilter{
	
	public boolean accept(File f){
		if (f.isDirectory()){
			return true;
		}
		String ext = null;
		String filename = f.getName();
		int dot = filename.lastIndexOf('.');
		if(dot > 0 && dot < filename.length()-1){
			ext = filename.substring(dot+1);
		}
		String extension = ext;
		if (extension != null){
			if (extension.equalsIgnoreCase("txt")){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public String getDescription (){
		return "(*.txt) Text File";
	}
}
