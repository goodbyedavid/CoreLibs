package com.rovicorp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class QuickTester {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("/Users/akupinski/projects/rv-dsg-repo/comcast-dpi-batch/apollo_srcs.lst"));
		String line;
		FileWriter fw = new FileWriter(new File("/Users/akupinski/_apollo_srcs.csv"));
		while ((line = br.readLine()) != null) {
		   String newLine=RoviFileUtils.getFirstToken(line);
		   if(newLine!=null){
			   System.out.println(newLine);
			   fw.write(newLine);
			   fw.write("\n");
		   }
		}
		br.close();
        fw.close();
	}

}
