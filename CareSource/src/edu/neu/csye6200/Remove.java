package edu.neu.csye6200;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Remove {
	String file = ".\\main.csv";
	String org;	
	List<String> pharma = new ArrayList<>();
	void read() {
		try { 
			FileReader filereader = new FileReader(file); 
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord; 
		        
		        while ((nextRecord = csvReader.readNext()) != null) 
		        { 
		        	String data = String.join(",", nextRecord);
		        	pharma.add(data);
		        	
		        } 
		        csvReader.close();
	    } 
	    catch (Exception e) { e.printStackTrace(); } 
		}
	void write() {
		try { 
		    FileWriter outputfile = new FileWriter(file); 
		    CSVWriter writer = new CSVWriter(outputfile); 
		        
		        for(String x: pharma)
		        {	
		        	String[] data = x.split(",");
		        	
		        	writer.writeNext(data); 
		        	System.out.println("list updated");
		        }
		        writer.close(); 
		        
		    } 
		    catch (IOException e) { e.printStackTrace();} 
		    
	}
	
	String search(String name) {
		try {
			FileReader filereader = new FileReader(file); 
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord; 
			System.out.println("pharma"+pharma.toString());
			List<String[]>csvBody=csvReader.readAll();
			int i=0;
			boolean f=false;
			while(i<csvBody.size()) {
				nextRecord= csvBody.get(i);
				System.out.println("nextRecord"+nextRecord[0]);
				if(nextRecord[0].equals(name)) {
					f=true;
				String rval1= nextRecord[0]+","+nextRecord[1]+","+nextRecord[2]+","+nextRecord[3]+","+nextRecord[4]+","+nextRecord[5]+","+nextRecord[6]+","+nextRecord[7];
				org = rval1;//String.join(",", nextRecord)
				pharma.remove(i);
				write();
				csvReader.close();
				return org;
				}
				i++;
			}
			csvReader.close();
			if(f==false) {
				return "Patient not found";
			}
//			while ((nextRecord = csvReader.readNext()) != null){	
//				
//				
//				String[] s= nextRecord[0].split(",");
//				System.out.println("s[0]"+s[0]);
//				if(nextRecord[0].equals(name)){
////					String[] rval = nextRecord;
//					String rval1= nextRecord[0]+","+nextRecord[1]+","+nextRecord[2]+","+nextRecord[3]+","+nextRecord[4]+","+nextRecord[5]+","+nextRecord[6]+","+nextRecord[7];
//					org = rval1;//String.join(",", nextRecord)
//					int ind = pharma.indexOf(org);
//					System.out.println("ind"+ind);
//					pharma.remove(ind);
//					write();
//					return org;
////					break;
//					}
//				else {
//					return "Patient not found";
//				}
//			}
			
		
		}
		catch (Exception e){ 
			e.printStackTrace(); 
		}
		return "";
	}
	
	public static String demo(String name) {
		System.out.println("Name is: "+name);
		Remove r = new Remove();
		r.read();
		String s = r.search(name);
		System.out.println("Inside search: "+s);
		return s;
	}
}
	
	