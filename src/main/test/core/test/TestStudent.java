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

public class TestStudent {
	
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
	 
	// Register student {@code studentName} for class {@code className} in year {@code year},
    // provided this class exists and has not met its enrollment capacity.
	@Test
	public void registerForValidClass(){
		this.admin.createClass("Test", 2017, "Instructor", 10);
		this.student.registerForClass("Student","Test", 2017);
		assertTrue(this.student.isRegisteredFor("Student","Test", 2017));
	}
	
	// DO NOT Register student {@code studentName} for class {@code className} in year {@code year},
    // provided this class DOES NOT exists and has not met its enrollment capacity.
	@Test
	public void registerForInValidClass(){
		this.student.registerForClass("Student","Test", 2017);
		assertTrue(this.student.isRegisteredFor("Student","Test", 2017));
	}
	
	// DO NOT Register student {@code studentName} for class {@code className} in year {@code year},
    // provided this class exists and has met its enrollment capacity.
	@Test
	public void registerInFullEnrolled(){
		this.admin.createClass("Test", 2017, "Instructor", 1);
		this.student.registerForClass("Student1","Test", 2017);
		this.student.registerForClass("Student2","Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student2","Test", 2017));
	}
	
	// Register student {@code studentName} for class {@code className} in year {@code year},
    // provided this class exists and has not met its enrollment capacity.
	@Test
	public void registerInNotFullyEnrolled(){
		this.admin.createClass("Test", 2017, "Instructor", 3);
		this.student.registerForClass("Student1","Test", 2017);
		this.student.registerForClass("Student2","Test", 2017);
		this.student.registerForClass("Student3","Test", 2017);
		assertTrue(this.student.isRegisteredFor("Student1","Test", 2017));
		assertTrue(this.student.isRegisteredFor("Student2","Test", 2017));
		assertTrue(this.student.isRegisteredFor("Student3","Test", 2017));
	}
	
	
	// Drop class {@code className} in year {@code year} for student {@code studentName},
    // provided the student is registered and the class has not ended.
	@Test
	public void DropClassValid() {
		this.admin.createClass("Test", 2017, "Instructor", 3);
	    this.student.registerForClass("Student","Test",2017);
	    this.student.dropClass("Student", "Test", 2017);
	    assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	// Drop class {@code className} in year {@code year} for student {@code studentName},
    // provided the student is NOT registered and the class has not ended.
	@Test
	public void DropClassStudentNotRegistered() {
		this.admin.createClass("Test", 2017, "Instructor", 3);
	    this.student.registerForClass("Student","Test",2017);
	    this.student.dropClass("Student", "Test1", 2017);
	    assertTrue(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	// Drop class {@code className} in year {@code year} for student {@code studentName},
    // provided the student is registered and the class has ended.
	@Test
	public void DropClassStudentClassEnded() {
		this.admin.createClass("Test", 2016, "Instructor", 3);
	    this.student.registerForClass("Student","Test",2016);
	    this.student.dropClass("Student", "Test", 2016);
	    assertTrue(this.student.isRegisteredFor("Student", "Class", 2016));
	}
	
	// Submit {@code studentName}'s homework solution {@code answerString} for homework {@code homeworkName} of class {@code className},
    // provided homework exists, student is registered and the class is taught in the current year
	@Test
    public void submitHW(){
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw");
        this.student.submitHomework("Student", "hw", "Solution", "Test", 2017);
        assertTrue(this.student.hasSubmitted("Student", "hw", "Test", 2017));
    }
	
	// Submit {@code studentName}'s homework solution {@code answerString} for homework {@code homeworkName} of class {@code className},
    // provided homework DOES NOT exists, student is registered and the class is taught in the current year
	@Test
    public void submitHWInvalidHW(){
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
//        this.instructor.addHomework("Instructor", "Test", 2017, "hw");
        this.student.submitHomework("Student", "hw", "Solution", "Test", 2017);
        assertFalse(this.student.hasSubmitted("Student", "hw", "Test", 2017));
    }
	
	// Submit {@code studentName}'s homework solution {@code answerString} for homework {@code homeworkName} of class {@code className},
    // provided homework exists, student is NOT registered and the class is taught in the current year
	@Test
    public void submitHWInvalidStudent(){
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw");
        this.student.submitHomework("Student1", "hw", "Solution", "Test", 2017);
        assertFalse(this.student.hasSubmitted("Student1", "hw", "Test", 2017));
    }
	
	// Submit {@code studentName}'s homework solution {@code answerString} for homework {@code homeworkName} of class {@code className},
    // provided homework exists, student is registered and the class is NOT taught in the current year (Future)
	@Test
    public void submitHWInvalidClassYearFuture(){
        this.admin.createClass("Test", 2018, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2018);
        this.instructor.addHomework("Instructor", "Test", 2018, "hw");
        this.student.submitHomework("Student", "hw", "Solution", "Test", 2018);
        assertFalse(this.student.hasSubmitted("Student", "hw", "Test", 2018));
    }
	
	// Submit {@code studentName}'s homework solution {@code answerString} for homework {@code homeworkName} of class {@code className},
    // provided homework exists, student is registered and the class is NOT taught in the current year (Past)
	@Test
    public void submitHWInvalidClassYearPast(){
        this.admin.createClass("Test", 2016, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2016);
        this.instructor.addHomework("Instructor", "Test", 2016, "hw");
        this.student.submitHomework("Student", "hw", "Solution", "Test", 2016);
        assertFalse(this.student.hasSubmitted("Student", "hw", "Test", 2016));
    }

}
