/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp1.include.me;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author PP
 */
public class Sabsalon1 {
    static LinkedList<String> insidefile = new LinkedList<String>();
    
    private static class wrongFileNameException extends Exception {

    private wrongFileNameException(String message){
     super(message);
    }
    }
    
    private static String readH(String filelocation) throws wrongFileNameException, FileNotFoundException, IOException{
        String ext = FilenameUtils.getExtension(filelocation);
        if("h".equals(ext) || "H".equals(ext)){
            BufferedReader buff = null;
		try {
			String sCurrentLine;
			buff = new BufferedReader(new FileReader(filelocation));
			while ((sCurrentLine = buff.readLine()) != null) {
				System.out.println(sCurrentLine);
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

        else{
            throw new wrongFileNameException("Is not a valid header (must be a .h file)");
        }
        
        return "unfinished";
    }
    
    private static String readFile(String filelocation) throws wrongFileNameException, FileNotFoundException, IOException{
        String ext = FilenameUtils.getExtension(filelocation);
        if("c".equals(ext) || "C".equals(ext) || "cpp".equals(ext)){
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

        else{
            throw new wrongFileNameException("Is not a valid file (must be a c/cpp file)");
        }
        
        return "unfinished";
    }
    
    private static void checkQuotations() throws wrongFileNameException, IOException{
        if (insidefile.isEmpty()){
            System.out.println("No contents read");
        }
        else{
            System.out.println("Printing inside quotations");
            for(String element : insidefile){
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(element);
                while (m.find()) {
                    //System.out.println(m.group(1));
                    System.out.println("\nContents of "+m.group(1)+" : ");
                    String haha = readH(m.group(1));
                }
            }
        }
        //here end
    }
    
    private static void checkInclude(){
        if (insidefile.isEmpty()){
            System.out.println("No contents read");
        }
        else{
            System.out.println("Printing lines with include call");
            for(String element : insidefile){
                if(element.contains("#include ")||element.contains(" #include ")){
                    System.out.println(element);
                }
            }
        }
        //here end
    }
    
    public static void main(String[] args) throws wrongFileNameException, IOException {
        String haha = readFile("inputFile.cpp");
        checkInclude();
        checkQuotations();
    }
    
}
