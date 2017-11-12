package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IStudent;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAdmin {

    private IAdmin admin;
   
    @Before
    // This will run before every test
    public void setup() {
        this.admin = new Admin();
       
    }

    // Null name of class
    // can't be null string
    @Test
    public void invalidNameNull() {
    		try {
    			this.admin.createClass(null, 2017, "Instructor", 15);
    		}
    		catch(NullPointerException e) {
    			//System.out.println(e.getClass());
    			assertEquals(NullPointerException.class, e.getClass());
    		} 
    }
    
    // Null name of instructor
    // can't be null string
    @Test
    public void instructorNull() {
    		try {
    			this.admin.createClass("ECS20", 2017, null, 15);
    		}
    		catch(NullPointerException e) {
    			//System.out.println(e.getClass());
    			assertEquals(NullPointerException.class, e.getClass());
    		} 
    }
    
    // InValid name of class
    // can't be empty string
    @Test
    public void invalidName() {
        this.admin.createClass("", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("", 2017));
    }
    
    
   // Present year class
    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    
    // Past year class
    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
 // Future year class
    @Test
    public void testMakeClass3() {
        this.admin.createClass("Test", 2018, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2018));
    }
    
    
 // className/year pair must be unique   
    @Test
    public void uniquePair() {
    		this.admin.createClass("Test1", 2017, "Instructor", 15);
    		this.admin.createClass("Test1", 2017, "Instructor", 15);
    		assertTrue(this.admin.classExists("Test1", 2017));
    }
    
    // InValid name of instructor
    // can't be empty string
    @Test
    public void invalidInstructorName() {
        this.admin.createClass("ECS", 2017, "", 15);
        assertFalse(this.admin.classExists("ECS", 2017));
    }
    
 // instructor teaches more than 2 classes in same year
    @Test
    public void testSameProfessorSameYear() {
        this.admin.createClass("Test1", 2017, "Nav", 15);
        this.admin.createClass("Test2", 2017, "Nav", 15);
        this.admin.createClass("Test3", 2017, "Nav", 15);
        assertNull(this.admin.getClassInstructor("Test3", 2017));
//        assertFalse(this.admin.classExists("Test3",2017));
    }
    
  // instructor teaches more than 2 classes in different years year
    @Test
    public void testSameProfessorDifferentYear() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        this.admin.createClass("Test3", 2018, "Instructor", 15);
        assertTrue(this.admin.classExists("Test1", 2017));
        assertTrue(this.admin.classExists("Test2", 2017));
        assertTrue(this.admin.classExists("Test3",2018));
    }
    
    // InValid capacity
    // can't be 0
    @Test
    public void invalidCapacity() {
        this.admin.createClass("ECS", 2017, "Sean", 0);
        assertFalse(this.admin.classExists("ECS", 2017));
    }
    
    // InValid capacity
    // can't be negative
    @Test
    public void invalidCapacityNeg() {
        this.admin.createClass("ECS", 2017, "Sean", -10);
        assertFalse(this.admin.classExists("ECS", 2017));
    }
    
    // Change capacity to positive number greater than current capacity
    @Test
    public void changeCapacityPos() {
        this.admin.createClass("ECS", 2017, "Sean", 10);
//        int before =  this.admin.getClassCapacity("ECS", 2017);
        this.admin.changeCapacity("ECS", 2017, 100);
        int after =  this.admin.getClassCapacity("ECS", 2017);
        //System.out.println(2<3);
        assertTrue(after == 100);
    }
    
    // Change capacity to positive number equal to the current number of enrollees
    @Test
    public void changeCapacityPosEqualToCurrentEnrolless() {
        this.admin.createClass("ECS", 2017, "Sean", 10);
//        int before =  this.admin.getClassCapacity("ECS", 2017);
        new Student().registerForClass("Student", "ECS", 2017);
        new Student().registerForClass("Student2", "ECS", 2017);
        new Student().registerForClass("Student3", "ECS", 2017);
        this.admin.changeCapacity("ECS", 2017, 3);
        int after =  this.admin.getClassCapacity("ECS", 2017);
        //System.out.println(2<3);
        assertTrue(after == 3);
    }
    
    
            
 // Change capacity to positive number less than current enrollees
    @Test
    public void changeCapacityLessThanEnrollees() {
        this.admin.createClass("ECS", 2017, "Sean", 3);
        new Student().registerForClass("Student", "ECS", 2017);
        new Student().registerForClass("Student2", "ECS", 2017);
        new Student().registerForClass("Student3", "ECS", 2017);
        this.admin.changeCapacity("ECS", 2017, 2);
        int after =  this.admin.getClassCapacity("ECS", 2017);
//        System.out.println(after);
        assertTrue(after == 3);
        
    }
    
 // Change capacity to negative number
    @Test
    public void changeCapacityNeg() {
        this.admin.createClass("ECS", 2017, "Sean", 10);
        int before =  this.admin.getClassCapacity("ECS", 2017);
        this.admin.changeCapacity("ECS", 2017, -100);
        int after =  this.admin.getClassCapacity("ECS", 2017);
        assertTrue(before==after);  
    }
 // Change capacity to 0
    @Test
    public void changeCapacityZero() {
        this.admin.createClass("ECS", 2017, "Sean", 10);
        int before =  this.admin.getClassCapacity("ECS", 2017);
        this.admin.changeCapacity("ECS", 2017, 0);
        int after =  this.admin.getClassCapacity("ECS", 2017);
        assertTrue(before==after);  
    }
    
 // Change capacity of non-existent class
    @Test
    public void changeCapacityClass() {
        this.admin.createClass("ECS", 2017, "Sean", 10);
        this.admin.changeCapacity("EC", 2017, 100);
        int after =  this.admin.getClassCapacity("EC", 2017);
        assertTrue(-1 == after);
   
        this.admin.changeCapacity("ECS", 2013, 100);
        int afterr =  this.admin.getClassCapacity("ECS", 2013);
        assertTrue(-1 == afterr);  
    }
   
}