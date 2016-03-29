package com.rovicorp.delta.processing;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CompareFiles {

    public void compare(String file1,String file2) {
        
        //For Windows
        runSystemCommand("fc /N \""+file1+"\" \""+file2+"\"");
        
        //For UNIX / LINUX
        //runSystemCommand("diff "+file1+" "+file2);
    }
    
    public void runSystemCommand(String command) {
        
        try {
            Process proc = Runtime.getRuntime().exec(command);
           
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            
            String strLn = "";
            
            // reading output stream of the command
            while ((strLn = inputStream.readLine()) != null) {
                System.out.println(strLn);
            }
            
            // reading errors of the command (if any)
            while ((strLn = errorStream.readLine()) != null) {
                System.out.println(strLn);
            }
           
        } catch (Exception e) {
           
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    
        CompareFiles cf = new CompareFiles();
        
        cf.compare("C:\\file1.txt","C:\\file2.txt");
    }

}