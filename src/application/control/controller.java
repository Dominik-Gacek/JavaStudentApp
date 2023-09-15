package application.control;

import java.util.ArrayList;

import application.model.Students;
import application.model.module;
import application.model.student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;


// holds all methods used by viewer to interact with students data
public class controller {
	final String filename = "save.txt";
	private int moduleSort;
	
	private final ObservableList<Object> studentView = FXCollections.observableArrayList();//???
	
	private final ObservableList<Object> moduleView = FXCollections.observableArrayList();//???
	
	private Students s = new Students(filename);//new instance of Students object which holds a student array
	
	public controller() {
//		this.load();
		this.moduleSort = 0;
		this.refreshView();
	}
	
	public void setDropdown(ComboBox<String> temp) {
		temp.getItems().clear();
			for(int i=0; i<s.getSize(); i++) {
				temp.getItems().add(new String(s.getName(i)));
			}	
	}
	
	public void setDropdown(ComboBox<String> temp, int i, int j) { 
		temp.getItems().clear();
		if(i>=0) {	
			ArrayList<module> m = getSortedModules(s.getStudent(i).getModuleList(), j);System.out.println(m);
			for(int k=0; k<m.size(); k++) {
				temp.getItems().add(new String(m.get(k).getName()));
			}
		}
		this.moduleSort = j;
	}
	
	public ArrayList<module> getSortedModules(ArrayList<module> i, int j) {
		switch(j) {
			case 1:// highest grade
				return util.sortList(i, (a, b)-> {return (a.getGrade()< b.getGrade());}) ;	
			case 2:// highest id
				return util.sortList(i, (a, b)-> {return (a.getId()< b.getId());}) ;
			case 3:// a to z
				return util.sortList(i, (a, b)-> {return (0<a.getName().compareTo(((module) b).getName()));});
			case 4:// lowest grade
				return util.sortList(i, (a, b)-> {return (a.getGrade()> b.getGrade());}) ;
			case 5:// lowest id
				return util.sortList(i, (a, b)-> {return (a.getId()> b.getId());}) ;
			case 6:// z to a
				return util.sortList(i, (a, b) -> { return (0>a.getName().compareTo(b.getName()));}) ;
			default:
				return i;
		}
	}
	
	
	

	public ObservableList<Object> getStudentView() {
		return studentView;
	}
	
	public ObservableList<Object> getModuleView() {
		return moduleView;
	}
	
	public void readStudent(String name, String id, String dob) throws badInputException{//setter for student in list
		try { 
			s.readStudent(name, id, dob);
		}catch(badInputException e) {
			throw e;
		}
	}
	
	public student remove(int i) {//removal of student at index i
		return s.remove(i);
	}
	
	public String listStudents() { //prints string with all student info
		return s.listStudents();
	}
	public int countStudents() {
		return s.getSize();
	}
	
	public void readModule(int student, String name, String id, String grade) throws badInputException {
		try {
			s.readModule(student, name, id, grade);
		}catch(badInputException e) {
			throw e;
		}
	}
	
	public void removeModule(int student, int module) {
		s.removeModule(student, module);
	}
	
	public void save() {//saves array to file
		s.save();
	}
	
	public void load() {//loads array from file
		s.load();
	}
	
	public void refreshView() {
		studentView.clear();
        for (int i=0; i < s.getSize(); i++) {
            studentView.add(s.getStudent(i));
        }
	}
	
	public void refreshModules(int i) {
		moduleView.clear();
        for (int j=0; j < s.getStudent(i).getModuleSize(); j++) {
            moduleView.add(s.getStudent(i).getModule(j));
        }
	}
	
	public void exit() {
		 System.exit(0);
	}

	public String printDetails(int selectedIndex) {
		if(selectedIndex>=0) {
			return s.getStudent(selectedIndex).generateInfo();
		}else {
			return "";
		}
	}

	public String printDetails(int selectedIndex, int selectedIndexModule) {
		if(selectedIndex>=0 & selectedIndexModule>=0) {
			ArrayList<module> m = getSortedModules(s.getStudent(selectedIndex).getModuleList(), this.moduleSort);
			return m.get(selectedIndexModule).generateInfo();
		}else {
			return "";
		}
	}
	
	public void setModuleSort(int i) {
		this.moduleSort = i;
	}
	public int getModuleSort() {
		return this.moduleSort;
	}
	
}
