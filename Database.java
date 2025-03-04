import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Database extends UnicastRemoteObject implements DBInterface {
    protected List<Student> vStudent = new ArrayList<>();
    protected List<Course> vCourse = new ArrayList<>();

    protected Database(String sStudentFileName, String sCourseFileName) throws Exception {
        BufferedReader objStudentFile = new BufferedReader(new FileReader(sStudentFileName));
        BufferedReader objCourseFile = new BufferedReader(new FileReader(sCourseFileName));

        this.vStudent = new ArrayList<>();
        this.vCourse = new ArrayList<>();

        while (objStudentFile.ready()) {
            this.vStudent.add(new Student(objStudentFile.readLine()));
        }
        while (objCourseFile.ready()) {
            this.vCourse.add(new Course(objCourseFile.readLine()));
        }

        objStudentFile.close();
        objCourseFile.close();
    }


    @Override
    public List<Student> getAllStudentRecords() {
        // ListStudentsHandler.java
        return vStudent;
    }

    @Override
    public List<Course> getAllCourseRecords() {
        // ListCoursesHandler.java
        return vCourse;
    }

    @Override
    public Student getStudentRecord(String sSID) {
        for (int i=0; i<this.vStudent.size(); i++) {
            Student objStudent = (Student) this.vStudent.get(i);
            if (objStudent.match(sSID)) {
                return objStudent;
            }
        }

        // Return null if not found.
        return null;
    }

    @Override
    public String getStudentName(String sSID) {
        // Lookup and return the matching student name if found.
        for (int i=0; i<this.vStudent.size(); i++) {
            Student objStudent = (Student) this.vStudent.get(i);
            if (objStudent.match(sSID)) {
                return objStudent.getName();
            }
        }

        // Return null if not found.
        return null;
    }

    @Override
    public Course getCourseRecord(String sCID, String sSection) {
        // Lookup and return the matching course record if found.
        for (int i=0; i<this.vCourse.size(); i++) {
            Course objCourse = (Course) this.vCourse.get(i);
            if (objCourse.match(sCID, sSection)) {
                return objCourse;
            }
        }

        // Return null if not found.
        return null;
    }

    @Override
    public String getCourseName(String sCID) {
        // Lookup and return the matching course name if found.
        for (int i=0; i<this.vCourse.size(); i++) {
            Course objCourse = (Course) this.vCourse.get(i);
            if (objCourse.match(sCID)) {
                return objCourse.getName();
            }
        }

        // Return null if not found.
        return null;
    }

    @Override
    public int numStudents(String sCID, String sSection) {
        Course objCourse = this.getCourseRecord(sCID, sSection);
        return objCourse.getRegisteredStudents().size();
    }

    @Override
    public void makeARegistration(String sSID, String sCID, String sSection) {
        // Find the student record and the course record.
        Student objStudent = this.getStudentRecord(sSID);
        Course  objCourse  = this.getCourseRecord(sCID, sSection);

        // Make a registration.
        if (objStudent != null && objCourse != null) {
            objStudent.registerCourse(objCourse);
            objCourse.registerStudent(objStudent);
        }
    }

    public static void main(String[] args) {
        String studentFileName, courseFileName;
        // Check the number of parameters.
        if (args.length == 2) {
            studentFileName = args[0];
            courseFileName = args[1];
        }
        else {
            studentFileName = "Students.txt";
            courseFileName = "Courses.txt";
        }

        // Check if input files exists.
        if (new File(studentFileName).exists() == false) {
            System.err.println("Could not find " + studentFileName);
            System.exit(1);
        }
        if (new File(courseFileName).exists() == false) {
            System.err.println("Could not find " + courseFileName);
            System.exit(1);
        }


        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            Database db = new Database(studentFileName, courseFileName);
            registry.rebind("DatabaseService", db);
            System.out.println("Database server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
