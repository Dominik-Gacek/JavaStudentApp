package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.control.badInputException;
import application.control.controller;
import application.model.Date;
import application.model.Students;
import application.model.module;
import application.model.student;

class addStudentTests {
	
	static String f = "filename.txt";
	Students s;
	
//items to test are integrity checks (on name or student id)
	
	@BeforeEach
	public void setup() {
		s = new Students(f);
	}
	
	@Test
	void addStudentTest() {//checks that valid student gets added
		assertEquals(0, s.getSize());
		try {
			s.readStudent("name", "103", "01/01/2001");
		} catch (badInputException e) {
			fail();
		}
		assertEquals(1, s.getSize());
	}
	
	@Test
	void getStudentAttributesTest() {
		try {
			s.readStudent("name", "103", "12/12/12");
		} catch (badInputException e) {
			fail();
		}
	    student m = s.getStudent(0);
		assertEquals(m.getName(), "name");
		assertEquals(m.getId(), 103);
		if(m.getDob().compareTo(new Date(12, 12, 12))!=0) {
			fail();
		}
	}
	
	@Test
	void invalidStudentIDTest() {
		assertThrows(badInputException.class, ()-> s.readStudent("name", "a103", "12/02/2002"));
		assertEquals(0, s.getSize());
	}
	@Test
	void duplicateStudentIDTest() {
		try {
			s.readStudent("name", "103", "01/01/2001");
		} catch (badInputException e) {
		}
		assertThrows(badInputException.class, ()-> s.readStudent("names", "103", "12/02/2002"));
		assertEquals(1, s.getSize());
	}
	
	@Test
	void invalidStudentNameTest() {
		assertThrows(badInputException.class, ()-> s.readStudent("", "103", "12/02/2002"));
		assertEquals(0, s.getSize());
	}
	
	@Test
	void invalidStudentDoBTest() {
		assertThrows(badInputException.class, ()-> s.readStudent("name", "103", "12/13/2002"));
		assertEquals(0, s.getSize());
	}
	
	@Test
	void invalidStudentDoBCharacterTest() {
		assertThrows(badInputException.class, ()-> s.readStudent("name", "103", "12/january/2002"));
		assertEquals(0, s.getSize());
	}
	
	@Test
	void invalidStudentDoBFormatTest() {
		assertThrows(badInputException.class, ()-> s.readStudent("name", "103", "1202/2002"));
		assertEquals(0, s.getSize());
	}
}


class addModuleTests {
	
	student s;
	
//items to test are integrity checks (on name or student id)
	
	@BeforeEach
	public void setup() {
		try {
			s = new student("name", "103", "01/01/2001");
		} catch (badInputException e) {
		}
	}
	
	@Test
	void addStudentTest() {
		assertEquals(0, s.getModuleSize());
		try {
			s.readModule("name", "103", "100");
		} catch (badInputException e) {
			fail();
		}
		assertEquals(1, s.getModuleSize());
	}
	
	@Test
	void getModuleAttributesTest() {
		try {
			s.readModule("art", "103", "11");
		} catch (badInputException e) {
			fail();
		}
	    module m = s.getModule(0);
		assertEquals(m.getName(), "art");
		assertEquals(m.getId(), 103);
		assertEquals(m.getGrade(), 11);
	}
	
	@Test
	void invalidModuleIDTest() {
		assertThrows(badInputException.class, ()-> s.readModule("name", "a103", "99"));
		assertEquals(0, s.getModuleSize());
	}
	
	@Test
	void duplicatemoduleIDTest() {
		try {
			s.readModule("name", "103", "0");
		} catch (badInputException e) {
		}
		assertThrows(badInputException.class, ()-> s.readModule("names", "103", "100"));
		assertEquals(1, s.getModuleSize());
	}
	
	@Test
	void invalidmoduleNameTest() {
		assertThrows(badInputException.class, ()-> s.readModule("", "103", "57"));
		assertEquals(0, s.getModuleSize());
	}
	
	@Test
	void lowGradeTest() {
		assertThrows(badInputException.class, ()-> s.readModule("name", "103", "-1"));
		assertEquals(0, s.getModuleSize());
	}
	
	@Test
	void highGradeTest() {
		assertThrows(badInputException.class, ()-> s.readModule("name", "103", "101"));
		assertEquals(0, s.getModuleSize());
	}
	
}

class sortingTest {
	
	 //the sorting of lists of students 
	ArrayList<module> m;
	controller c;
	
	@BeforeEach
	public void setup() {
		try {
			c = new controller();
			student s = new student("name", "103", "01/01/2001");	//original  name  id  grade	rname rid rgrade	
			s.readModule("art", "103", "11");						//m.get(0)  1     3   4		3	  1   0	
			s.readModule("architecture", "305", "12");				//m.get(1)  0     2   3		4	  2   1
			s.readModule("computing", "1003", "17");				//m.get(2)  2     1   2		2	  3   2
			s.readModule("science", "2007", "77");					//m.get(3)  4     0   0		0	  4	  4
			s.readModule("maths", "32", "69");						//m.get(4)  3     4   1		1	  0   3
			m = s.getModuleList();
		} catch (badInputException e) {
		}
	}
	
	@Test
	void nameSortTest() {
		ArrayList<module> sort = c.getSortedModules(m, 3);
		assertEquals(m.get(0), sort.get(1));
		assertEquals(m.get(1), sort.get(0));
		assertEquals(m.get(2), sort.get(2));
		assertEquals(m.get(3), sort.get(4));
		assertEquals(m.get(4), sort.get(3));
	}
	
	@Test
	void idSortTest() {
		ArrayList<module> sort = c.getSortedModules(m, 2);
		assertEquals(m.get(0), sort.get(3));
		assertEquals(m.get(1), sort.get(2));
		assertEquals(m.get(2), sort.get(1));
		assertEquals(m.get(3), sort.get(0));
		assertEquals(m.get(4), sort.get(4));
	}
	
	@Test
	void gradeSortTest() {
		ArrayList<module> sort = c.getSortedModules(m, 1);
		assertEquals(m.get(0), sort.get(4));
		assertEquals(m.get(1), sort.get(3));
		assertEquals(m.get(2), sort.get(2));
		assertEquals(m.get(3), sort.get(0));
		assertEquals(m.get(4), sort.get(1));
	}
	
	@Test
	void reverseNameSortTest() {
		ArrayList<module> sort = c.getSortedModules(m, 6);
		assertEquals(m.get(0), sort.get(3));
		assertEquals(m.get(1), sort.get(4));
		assertEquals(m.get(2), sort.get(2));
		assertEquals(m.get(3), sort.get(0));
		assertEquals(m.get(4), sort.get(1));
	}
	
	@Test
	void reverseIdSortTest() {
		ArrayList<module> sort = c.getSortedModules(m, 5);
		assertEquals(m.get(0), sort.get(1));
		assertEquals(m.get(1), sort.get(2));
		assertEquals(m.get(2), sort.get(3));
		assertEquals(m.get(3), sort.get(4));
		assertEquals(m.get(4), sort.get(0));
	}
	
	@Test
	void revesrGradeSortTest() {
		ArrayList<module> sort = c.getSortedModules(m, 4);
		assertEquals(m.get(0), sort.get(0));
		assertEquals(m.get(1), sort.get(1));
		assertEquals(m.get(2), sort.get(2));
		assertEquals(m.get(3), sort.get(4));
		assertEquals(m.get(4), sort.get(3));
	}
}