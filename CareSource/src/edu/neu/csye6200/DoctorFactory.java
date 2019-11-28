package edu.neu.csye6200;


import java.util.Arrays;
import java.sql.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Scanner;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DoctorFactory implements AbstractDoctorFactory {
	private static int cr;
	private static int nr;
	private static int oc;
	private static int gc;
	private static int cs;
	private static int sr;
	private static String url="jdbc:mysql://localhost:3306/doctors";
	private static String uname="Trivedh";
	private static String pass="Trivedh@174";
	enum Doctorenum{
		CARDIOLOGIST,NEUROLOGIST,ONCOLOGIST,GYNOCOLOGIST,CHILDSPECIALIST,SURGEON;
	}
	
	public DoctorFactory() {
		cr=readcount("Cardiologist");
		nr=readcount("Neurologist");
		oc=readcount("Oncologist");
		gc=readcount("Gynecologist");
		cs=readcount("ChildSpecialist");
		sr=readcount("Surgeon");
	}
	
	
	@Override
	public AbstractDoctor getObject(Doctorenum d1) {
		// TODO Auto-generated method stub
		switch(d1) {
		case CARDIOLOGIST:{ cr++;
		if(cr>12) {
			String val= onExit("Cardiologist");
			if(val==null)
			return null;
			return new Cardiologist(val);
			}
			writecount("Cardiologist",cr);
			//System.out.println(cr/4);
			return new Cardiologist(readData((cr-1)/4+1));}
		case NEUROLOGIST: { nr++;
		if(nr>24) {
			String val= onExit("Neurologist");
			if(val==null)
			return null;
			return new Neurologist(val);}
			writecount("Nuerologist",nr);
			return new Neurologist(readData((nr-1)/4+1));
		}
		case ONCOLOGIST: { oc++;
		if(oc>36) {
			String val= onExit("Oncologist");
			if(val==null)
			return null;
			return new Oncologist(val);}
			writecount("Oncologist",oc);
			return new Oncologist(readData((oc-1)/4+1));
		}
		case GYNOCOLOGIST: { gc++;
		if(gc>48) {
			String val= onExit("Gynecologist");
			if(val==null)
			return null;
			return new Gynocologist(val);
			}
			writecount("Gynecologist",gc);
			return new Gynocologist(readData((gc-1)/4+1));
		}
		case CHILDSPECIALIST: { cs++;
		if(cs>60) {
			String val= onExit("Childspecialist");
			if(val==null)
			return null;
			return new ChildSpecialist(val);}
			writecount("ChildSpecialist",cs);
			return new ChildSpecialist(readData((cs-1)/4+1));
		}
		case SURGEON: { sr++;
		if(sr>72) {
			String val= onExit("Surgeon");
			if(val==null)
			return null;
			return new Surgeon(val);}
			writecount("Surgeon",sr);
			return new Surgeon(readData((sr-1)/4+1));
		}
		
		}
		return null;
	}
	public String readData(int id) {
		try {
			String query= "select * from doctors_list where id="+String.valueOf(id);
			Connection c= DriverManager.getConnection(url,uname,pass);
			Statement st= c.createStatement();
			ResultSet rs=st.executeQuery(query);
			if(rs.next()) {
				String res= rs.getInt("id")+","+rs.getString("fname")+","+rs.getString("lname");
				c.close();
				st.close();
				rs.close();
				return res;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
	}
	public int readcount(String name) {
		String query="select count from doctors_count where dept_name="+"'"+name+"'";
		try {
		Connection c= DriverManager.getConnection(url, uname, pass);
		Statement st= c.createStatement();
		ResultSet rs=st.executeQuery(query);
		int i=0;
		if(rs.next())
			i=rs.getInt(1);
		//int i=rs.getInt("count");
		c.close();
		rs.close();
		return i;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
		
	}
	public String onExit(String dept){
			String query="select id,dept_name,fname,lname from released_docs where dept_name="+"'"+dept+"'";
			String delete="delete from released_docs where id=";
			try {
				Connection c= DriverManager.getConnection(url, uname, pass);
				Statement st= c.createStatement();
				ResultSet rs=st.executeQuery(query);
				int id=0;
				if(rs.next()) {
					id=rs.getInt("id");
					System.out.println("exception....");
					st.executeUpdate(delete+String.valueOf(id));
					}
				c.close();
				st.close();
				rs.close();
				return readData(id);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
	}
	public void upDateReleasedDocs(String id,String dept,String fn,String ln) {
		try {
			System.out.println("HELLO THERE");
			String query= "INSERT INTO RELEASED_DOCS VALUES("+String.valueOf(id)+","+"'"+dept+"'"+","+"'"+fn+"'"+","+"'"+ln+"'"+")";
			Connection c= DriverManager.getConnection(url, uname,pass);
			Statement st=c.createStatement();
			st.executeUpdate(query);
			st.close();
			c.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void writecount(String name,int count) {

		try {
			String query="update doctors_count set count="+String.valueOf(count)+" where dept_name="+"\""+name+"\""+";";
			Connection c= DriverManager.getConnection(url,uname,pass);
			Statement st= c.createStatement();
			st.executeUpdate(query);
			c.close();
			st.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
