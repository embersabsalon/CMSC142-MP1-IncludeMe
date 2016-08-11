/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp1.include.me;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author PP
 */
public class Sabsalon1 {
    LinkedList<String> insidefile = new LinkedList<String>();
    
    private class wrongFileNameException extends Exception {

    private wrongFileNameException(String message){
     super(message);
    }
    }
    
    private String readFile(String filelocation) throws wrongFileNameException, FileNotFoundException, IOException{
        String ext = FilenameUtils.getExtension(filelocation);
        if(!"c".equals(ext) || !"C".equals(ext)){
            throw new wrongFileNameException("Is not a valid file (must be a c file)");
        }
        else{
            BufferedReader buff = null;
		try {
			String sCurrentLine;
			buff = new BufferedReader(new FileReader(filelocation));
			while ((sCurrentLine = buff.readLine()) != null) {
				System.out.println(sCurrentLine);
                                insidefile.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buff != null)buff.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
        }
        
        return "unfinished";
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
  
