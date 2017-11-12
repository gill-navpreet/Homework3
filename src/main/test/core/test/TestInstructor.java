package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IStudent;
import core.api.impl.Student;
import core.api.IInstructor;
import core.api.impl.Instructor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestInstructor {
	
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	 @Before
	 // This will run before every test
	 public void setup() {
		 this.instructor = new Instructor();
		 this.admin = new Admin();
		 this.student = new Student();
	 }
	 
	 // Test that HW is added to class when the instructor is assigned to class
	 @Test
	 public void AddHWValid() {
		 this.admin.createClass("Test", 2017, "Instructor", 50);
		 instructor.addHomework("Instructor", "Test", 2017, "Homework");
		 assertTrue(instructor.homeworkExists("Test", 2017, "Homework"));
	 }
	 
	// Test that HW is not added to class when the instructor is not assigned to class
	 @Test
	 public void AddHWInValid() {
		 this.admin.createClass("Test", 2017, "Instructor", 50);
		 instructor.addHomework("Instructor1", "Test", 2017, "Homework");
		 assertFalse(instructor.homeworkExists("Test", 2017, "Homework"));
	 } 
	 
	// Test that HW is not added to non existent class 
	@Test
	public void AddHWNonExistentClass() {
		 instructor.addHomework("Instructor1", "Test", 2017, "Homework");
		 assertFalse(instructor.homeworkExists("Test", 2017, "Homework"));
	 }
	
	// Test that Grade can be assigned to student for homework in class provided this 
	// instructor has been assigned to this class, the homework has been assigned and
	// the student has submitted this homework.
    
	@Test
	public void AssignGradeValid() {
		 this.admin.createClass("Test", 2017, "Instructor", 50);
		 instructor.addHomework("Instructor", "Test", 2017, "Homework");
		 student.registerForClass("Student", "Test", 2017);
		 student.submitHomework("Student", "Homework", "Solution", "Test", 2017);
		 instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", 100);
		 assertEquals(new Integer(100), instructor.getGrade("Test", 2017, "Homework", "Student"));
	 }
	
	// Test that Grade can NOT be assigned to student for homework in class provided this 
	// instructor has NOT been assigned to this class, the homework has been assigned and
	// the student has submitted this homework.
	@Test
	public void AssignGradeInValidInstNotAssigned() {
		 this.admin.createClass("Test", 2017, "Instructor", 50);
		 instructor.addHomework("Instructor", "Test", 2017, "Homework");
		 student.registerForClass("Student", "Test", 2017);
		 student.submitHomework("Student", "Homework", "Solution", "Test", 2017);
		 instructor.assignGrade("Inst", "Test", 2017, "Homework", "Student", 100);
		 assertNull(instructor.getGrade("Test", 2017, "Homework", "Student"));
	 }
	

	// Test that Grade can NOT be assigned to student for homework in class provided this 
	// instructor has been assigned to this class, the homework has been assigned and
	// the student has NOT submitted this homework.
	@Test
	public void AssignGradeInValidHWNotSubmit() {
		 this.admin.createClass("Test", 2017, "Instructor", 50);
		 instructor.addHomework("Instructor", "Test", 2017, "Homework");
		 student.registerForClass("Student", "Test", 2017);
		 instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", 100);
		 assertNull(instructor.getGrade("Test", 2017, "Homework", "Student"));
	 }
	
	// Test that Grade can NOT be assigned to student for homework in class provided this 
	// instructor has been assigned to this class, the homework has NOT been assigned and
	// the student has submitted this homework.
	@Test
	public void AssignGradeInValidHWNotAssigned() {
		this.admin.createClass("Test", 2017, "Instructor", 50);
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Solution", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", 100);
		assertNull(instructor.getGrade("Test", 2017, "Homework", "Student"));
	}
	
	// Can't assign a negative grade
	@Test
	public void AssignGradeNegative() {
		 this.admin.createClass("Test", 2017, "Instructor", 50);
		 instructor.addHomework("Instructor", "Test", 2017, "Homework");
		 student.registerForClass("Student", "Test", 2017);
		 student.submitHomework("Student", "Homework", "Solution", "Test", 2017);
		 instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", -100);
//		 System.out.println(instructor.getGrade("Test", 2017, "Homework", "Student"));
		 assertNull(instructor.getGrade("Test", 2017, "Homework", "Student"));
	 }	 
}
