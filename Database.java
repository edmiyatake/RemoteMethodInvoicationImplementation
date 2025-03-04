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
        return vStudent;
    }

    @Override
    public boolean makeARegistration(String sSID, String sCID, String sSection) {
        return true;
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
