package application.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import application.control.badInputException;

public class Students {

private ArrayList<student> students;
private String filename;
private ArrayList<Integer> studentIDs;

	public Students(String s) {
		this.filename = s;
		this.students = new ArrayList<student>();
		this.studentIDs = new ArrayList<>();
	}
	
	public void readStudent(String name, String id, String dob) throws badInputException {//setter for student in list
		try {
			student temp = new student(name, id, dob);
			if(isUniqueId(temp.getId())) {
				students.add(temp);
				studentIDs.add(temp.getId());
			}else throw new badInputException("student ID already in use");
		}catch(badInputException e) {
			//students.add(new student("name", "123", "12/12/12"));
			throw e;
		}
	}
	public int getSize() {//getters
		return students.size();
	}
	public student getStudent(int i){
		return students.get(i);
	}
	public String getName(int i) {
		return getStudent(i).getName();
	}
	
	public student remove(int i) {//removal of student at index i
		return students.remove(i);
	}
	
	public boolean isUniqueId(int i) {// check if given number is unique from previous ids
		for(int x : studentIDs) {
			if(x == i) return false;
		}
		return true;
	}
	
	public String listStudents() { //prints string with all student info
		String a = "";
		for(int i=0; i<students.size(); i++) {
			a += (students.get(i) + "\n");
		}
		return a;
	}
	//read in a module to a student
	public void readModule(int student, String name, String id, String grade) throws badInputException {
		try {
			if(students.size()<student)throw new badInputException("Select a student!!!");
			getStudent(student).readModule(name, id, grade);
		}catch(badInputException e) {
			throw e;
		}
	}
	
	public void removeModule(int student, int module) {
		try {
			getStudent(student).removeModule(module);
		}catch(Exception e) {
			
		}
	}
	
	public void save() {//saves array to file
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(students);
			objectOut.close();
			fileOut.close();
		}catch(Exception e){	
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load() {//loads array from file
		try{
	        FileInputStream fileIn = new FileInputStream(filename);
	        ObjectInputStream objectIn = new ObjectInputStream(fileIn);	        
	        // program only saves one object to file so shouldn't be possible to break
	        students = (ArrayList<student>)objectIn.readObject();
	        objectIn.close();
	        fileIn.close();
		}catch(Exception e){
	            students = new ArrayList<student>();
	        }
	}
}
