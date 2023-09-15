package application.model;
import java.io.Serializable;
import java.util.ArrayList;

import application.control.badInputException;
import application.control.util;


public class student implements Serializable{

	private static final long serialVersionUID = -4118117866565037164L;

	private final String name;
	private final int studentId;
	private final Date dob;
	
	private ArrayList<module> modules;
	private ArrayList<Integer> moduleIDs;
	    
	public student(String s, String id, String dob) throws badInputException
	{	
		try {
			this.name = s;
			this.dob = new Date(dob);
			this.studentId = checkID(id);
			this.modules = new ArrayList<module>();
			this.moduleIDs = new ArrayList<>();
		}catch(badInputException e) {
			throw e;
		}
		if(this.name.length()<1) {
			throw new badInputException("name cannot be empty");
		}
	}
    
    public int checkID(String a) throws badInputException{// sets studentid from the string
    	if(util.strInt(a)>0) {
    		return util.strInt(a);
    	}else {
    		throw new badInputException("invalid ID entered");
    	}
    }
    public int getId() {
    	return this.studentId;
    }
    
    public Date getDob() {
    	return this.dob;
    }
    public void readModule(String name, String id, String grade) throws badInputException{//setter for student in list
		try {
			module temp = new module(name, id, grade);
			if(isUniqueId(temp.getId())) {
				modules.add(temp);
				moduleIDs.add(temp.getId());
			}else throw new badInputException("duplicate module ID");
		}catch (badInputException e){
			throw e;
		}	
	}
    
    private boolean isUniqueId(int i) {
    	for(int x : moduleIDs) {
			if(x == i) return false;
		}
		return true;
	}

	public void addModule(module m) {
    	modules.add(m);
    }
    
    public void removeModule(int i) {
    	modules.remove(i);
    }
    
    public int getModuleSize() {//getters
		return modules.size();
	}
    public module getModule(int i) {
		return modules.get(i);
	}
    public ArrayList<module> getModuleList(){
    	ArrayList<module> m = new ArrayList<>();
    	for(int i= 0; modules.size()> i; i++) {
			m.add(modules.get(i));
		}
    	return m;
    }
    public String getName() {
    	return this.name;
    }
    @Override
	public String toString(){ // string of the module
		return (this.name + ", " + this.studentId + ", " + this.dob);
	}

	public String generateInfo() {
		double x = 0;
		for(module i: modules) {
			x += i.getGrade();
		}
		if(this.getModuleSize()>0) {
			x = x/this.getModuleSize();
		}else {
			x = 0;
		}
		return String.format("Displaying info for student named %s\nStudent ID: %s\nDate of Birth: %s\n"
				+ "The student has taken %d modules\n with an average of %.1f%% ", 
				this.name, this.studentId, this.dob, this.getModuleSize(), x);
		
	}
}
