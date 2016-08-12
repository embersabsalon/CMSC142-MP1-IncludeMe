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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author PP
 */
public class Sabsalon1 {
    static LinkedList<String> insidefile = new LinkedList<String>();
    static LinkedList<String> finaloutput = new LinkedList<String>();
    
    private static class wrongFileNameException extends Exception {

    private wrongFileNameException(String message){
     super(message);
    }
    }
    
    private static String readH(String filelocation) throws wrongFileNameException, FileNotFoundException, IOException{
        String ext = FilenameUtils.getExtension(filelocation);
        if("h".equals(ext) || "H".equals(ext)){
            System.out.println("Is right header, contents of "+filelocation);
            BufferedReader buff = null;
		try {
			String sCurrentLine;
			buff = new BufferedReader(new FileReader(filelocation));
			while ((sCurrentLine = buff.readLine()) != null) {
				System.out.println(sCurrentLine);
                                checkValidAndInclude(sCurrentLine);
                                finaloutput.add(sCurrentLine);
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
    
    
    
    private static boolean checkQuotations(String checkthis) throws wrongFileNameException, IOException{
        if (checkthis.isEmpty()){
            System.out.println("No contents read");
            return false;
        }
        else{
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(checkthis);
                while (m.find()) {
                    //System.out.println(m.group(1));
                    if(m.group(1).isEmpty())
                        return false;
                    else{
                    System.out.println("\nContents of "+m.group(1)+" : ");
                        System.out.println("Has right quotations!");
                    String haha = readH(m.group(1));
                    }
                }
        }
        return false;
        //here end
    }
    
    private static boolean checkRepeat(String checkthis){
        if(finaloutput.contains(checkthis)){
            return true;
        }
        return false;
    }
    
    private static void writeOutput() throws FileNotFoundException, UnsupportedEncodingException{
        System.out.println("\nWriting to file...(outputFile.out)");
        PrintWriter writer = new PrintWriter("outputFile.out", "UTF-8");
        finaloutput.stream().forEach((element) -> {
            writer.println(element);
        });
        writer.close();
        System.out.println("File write success!");
    }
    
    private static void checkValidAndInclude(String checkthis) throws wrongFileNameException, IOException{
        if (checkthis.isEmpty()){
            System.out.println("No contents read");
        }
        else{
                if(checkthis.contains("#include ")||checkthis.contains(" #include ")){
                    System.out.println("has include!");
                    if(checkRepeat(checkthis))
                        System.out.println("Repeated include call");
                    else
                    checkQuotations(checkthis);
            }
                else{
                    System.out.println("Is not an include call!");
                    if(checkRepeat(checkthis))
                        System.out.println("Repeated line detected");
                    //else
                        //finaloutput.add(checkthis);
                }
        }
        //here end
    }
    
    private static void clean(){
        for(int i=0;i<finaloutput.size();i++){
            if(finaloutput.get(i).contains("#include ")||finaloutput.get(i).contains(" #include ")){
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(finaloutput.get(i));
                while (m.find()) {
                    //System.out.println(m.group(1));
                    if(!m.group(1).isEmpty()){
                        finaloutput.remove(i);
                        i--;
                    }
                }
            }
            if("".equals(finaloutput.get(i)) && "".equals(finaloutput.get(i+1))){
                finaloutput.remove(i);
                i--;
            }
        }
    }
    
    private static void parse() throws wrongFileNameException, IOException{
        for(String element : insidefile){
            if(element.contains("#include ")||element.contains(" #include ")){
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(element);
                while (m.find()) {
                    //System.out.println(m.group(1));
                    if(!m.group(1).isEmpty()){
                        checkValidAndInclude(element);
                        finaloutput.add("");
                    }
                    else{
                        finaloutput.add(element);
                    }
                }
            }
            
            else{
             finaloutput.add(element);
            }
        }
        clean();
    }
    
    private static void printOutput(){
        System.out.println("\nFinal File:");
        finaloutput.stream().forEach((element) -> {
            System.out.println(element);
        });
    }
    
    public static void main(String[] args) throws wrongFileNameException, IOException {
        String haha = readFile("inputFile.cpp");
        System.out.println("checking...");
        parse();
        printOutput();
        writeOutput();
    }
    
}
