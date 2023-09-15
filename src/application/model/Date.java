package application.model;

import java.io.Serializable;
import java.time.Year;

import application.control.Month;
import application.control.badInputException;
import application.control.util;

public class Date implements Serializable, Comparable<Date>{

	private int CURRENT_YEAR = Year.now().getValue();
	
	private static final long serialVersionUID = -5873890170685571495L;
	
	private int day;
	private int month;
	private int year;

	
	public Date(int day, int month, int year) { //constructors for separate ints
		this.day = day;
		this.month = month;
		this.year = year;
	}
	public Date(int day, Month month, int year) { // constructors for ints + month
		this(day, Month.monthNum(month), year);
	}
	public Date(String s) throws badInputException { // constructor for single string value dd/mm/yy
		try{
			this.setBirthday(s);
		}catch(badInputException e) {		
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Date(java.util.Date date) {
		this(date.getDate() ,date.getMonth()+1, date.getYear()+1900);
	}
	
	@Override
	public String toString(){ //prints a string of the date
		return (this.day + " " + Month.monthNum(this.month) + " " + this.year);
	}
	
	
	
	
	public int getDay() {//getters
		return this.day;
	}
	public Month getMonth() {
		return Month.monthNum(this.month);
	}
	public int getYear() {
		return this.year;
	}
	
	
	public void setBirthday(String a) throws badInputException { //takes a string format dd/mm/yyyy and set 3 integer values
		int counter = a.length()-1;
		int ddmmyy = 2;
		String day = "", month = "", year = "";
		
	    	//year
		while(ddmmyy==2 && counter>=0) { //loops through every number until a "/" character is found 
			if (a.substring(counter, counter+1).equals("/")) {
				ddmmyy-=1; // if "/" is found the month loop is started
			}else{
				year = a.substring(counter,counter+1) + year; //if not found the character is added to year string
	    	}
			counter-=1;
		}
		if(util.strInt(year)<=0 || util.strInt(year)>CURRENT_YEAR) { // if the year string cannot be converted to an int between 0 and 2023
			throw new badInputException("invalid year!!!");
		}else {				
			this.year = util.strInt(year);// sets the year
		}
		
	    	//month
		while(ddmmyy==1 && counter>=0) {// same thing as was done for year
			if (a.substring(counter, counter+1).equals("/")) {
				ddmmyy-=1;
			}else{
				month = a.substring(counter,counter+1) + month;
			}
			counter-=1;
	    }
	    if(util.strInt(month)<=0 || util.strInt(month)>12) {
	    	throw new badInputException("Invalid month!!!");
	    }else {
	    	this.month = util.strInt(month);
	    }
	    
	    	//day
	    while(counter>=0) { //repeats the same as other 2 until the input string is empty
	    	day = a.substring(counter,counter+1) + day;
	    	counter-=1;
	    }
	    // checks if the date is valid for the month and year
	    if(util.strInt(day)<=0 || util.strInt(day)>Month.getDays(this.year, this.month)) { 
	    	throw new badInputException("Invalid day & month combination!!!");
	    }else {
	    	this.day = util.strInt(day);
	    }	
	}
	
	@Override
	public int compareTo(Date o) {
		if (o.getYear()>this.getYear()) {
			return -1;
		}
		if(o.getYear()<this.getYear()) {
			return 1;
		}
		if(Month.monthNum(o.getMonth())>this.month) {
			return -1;
		}
		if(Month.monthNum(o.getMonth())<this.month) {
			return 1;
		}
		if(o.getDay()>this.getDay()) {
			return -1;
		}
		if(o.getDay()<this.getDay()) {
			return 1;
		}
		return 0;
	}
	
	
}
