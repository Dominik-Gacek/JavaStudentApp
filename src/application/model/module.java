package application.model;

import java.io.Serializable;

import application.control.badInputException;
import application.control.util;

public class module implements Serializable{

	private static final long serialVersionUID = 2476016884060778221L;
	
	private String name;
	private int id;
	private int grade;
	
	
	public module(String name, String id, String grade) throws badInputException{
		try {
		this.name = name;
		this.id = util.strInt(id);
		this.grade = util.strInt(grade);
		}catch(badInputException e) {
			throw e;
		}
		if(this.grade<0|this.grade>100) {
			throw new badInputException("grade must be between 0 & 100");
		}
		if(this.name.length()<1) {
			throw new badInputException("name cannot be empty");
		}
	}
	
	public double getGrade() {
		return (double)this.grade;
	}
	
	@Override
	public String toString(){ // string of the module
		return (this.name + " " + this.id + ": " + this.grade +"%");
	}

	public String generateInfo() {
		String x;
		if(this.grade>=40) {
			x = "passed";
		}else {
			x = "failed";
		}
		return String.format("Module Name: %s\nModule ID: %s\nGrade Received: %.1f%%\nThis means the student %s.", 
				this.name, this.id, this.getGrade(), x);
	}

	public String getName() {
		return this.name;
	}
	public int getId() {
		return this.id;
	}
	
}
