package com.nmi.write;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WriteNMIData {
	 static String value="";
	
		private static final Pattern p = Pattern.compile("<NMI>(.*?)<\\/NMI>");
		private static final Pattern p1 = Pattern.compile("<NMI checksum=\\\"\\d\\\">(.*?)<\\/NMI>");
		private static final Pattern p2 = Pattern.compile("200,(.*?),");
		
		
		public static void main(String[] args) {
			
			System.out.println("Started Writing NMI Data");
			String filepath=null;
			//System.out.println(System.getProperty("user.dir"));
			//try (InputStream input = new FileInputStream("H:\\SeleniumWorkSpace\\WriteNMIDataToFile\\src\\file.properties")) {
			try (InputStream input = new FileInputStream(System.getProperty("user.dir")+"\\file.properties")) {
	            Properties prop = new Properties();

	            // load a properties file
	            prop.load(input);
	           
	            filepath=prop.getProperty("filepath");
	            // get the property value and print it out
	           
	            File file = new File(filepath);
	    		String[] fileList = file.list();
	    		
	    		 System.out.println("Files Count in folder: "+(fileList.length));
	    		 //List<String> list=new ArrayList<String>(); //For Duplicates
	    		 Set<String> list=new HashSet<String>(); //For Unique
	    		for(String name:fileList){
	    			if(!name.contains("output")) {
	    			String filename=filepath+"\\"+name;
	    			String contents = new String(Files.readAllBytes(Paths.get(filename)));
	    			 Matcher m = p.matcher(contents);
	    			 Matcher m1 = p1.matcher(contents);
	    			 Matcher m2 = p2.matcher(contents);
	    			
	    			 while (m.find()) {
	    				 value=m.group(1);
	    				 list.add(value);
	    			 }
	    			
	    			 while (m1.find()) {
	    				 value=m1.group(1);
	    				 list.add(value);
	    			 }
	    			 while (m2.find()) {
	    				 value=m2.group(1);
	    				 if(value.length()==10)
	    				 list.add(value);
	    				 
	    			 }
	    			 
	    			    
	               
	    		}
	    			
    			  
	    		}
	    		 String outputdir=filepath+"\\output";
    			 File directory = new File(outputdir);
    			    if (! directory.exists()){
    			        directory.mkdir();
    			    }
    			 System.out.println("NMI Numbers: "+list.size());
       			 //System.out.println(list);
    			 
       			 FileWriter writer = new FileWriter(outputdir+"\\NMINumbers.csv");
    			 String collect = list.stream().collect(Collectors.joining("\n"));
    			    writer.write(collect);
    			    writer.close();
	    		
	    		 System.out.println("Writing NMI Data done");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		}

	}


