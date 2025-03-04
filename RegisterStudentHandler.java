import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RegisterStudentHandler extends UnicastRemoteObject implements IActivity {
    private final DBInterface db;
    private final String sSID;
    private final String sCID;
    private final String sSection;

    protected RegisterStudentHandler(DBInterface db, String studentId, String courseId, String sectionNumber) throws RemoteException {
        super();
        this.db = db;
        this.sSID = studentId;
        this.sCID = courseId;
        this.sSection = sectionNumber;
    }

    @Override
    public Object execute() throws RemoteException {
        Student objStudent = this.db.getStudentRecord(sSID);
        Course objCourse = this.db.getCourseRecord(sCID, sSection);
        if (objStudent == null) {
            return "Invalid student ID";
        }
        if (objCourse == null) {
            return "Invalid course ID or course section";
        }

        // Maybe I would put the class checker here

        //Check if the given course conflicts with any of the courses the student has registered.
        ArrayList vCourse = objStudent.getRegisteredCourses();
        for (int i=0; i<vCourse.size(); i++) {
            if (((Course) vCourse.get(i)).conflicts(objCourse)) {
                return "Registration conflicts";
            }
        }

        // Request validated. Proceed to register.
        this.db.makeARegistration(sSID, sCID, sSection);
        if (this.db.numStudents(sCID, sSection) > 3){
            return "Overbooked!";
        }
        return "Successful!";
    }
}