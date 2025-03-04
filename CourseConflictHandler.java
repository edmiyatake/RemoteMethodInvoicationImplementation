import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class CourseConflictHandler extends UnicastRemoteObject implements IActivity {
    private final DBInterface db;
    private final String sSID;
    private final String sCID;
    private final String sSection;


    protected CourseConflictHandler(DBInterface db, String studentId, String courseId, String courseSection) throws RemoteException {
        super();
        this.db = db;
        this.sSID = studentId;
        this.sCID = courseId;
        this.sSection = courseSection;
    }

    @Override
    public Object execute() {
        try {
            // Get the student and course records.
            Student objStudent = this.db.getStudentRecord(sSID);
            Course objCourse = this.db.getCourseRecord(sCID, sSection);
            if (objStudent == null) {
                return "Invalid student ID";
            }
            if (objCourse == null) {
                return "Invalid course ID or course section";
            }

            ArrayList vCourse = objStudent.getRegisteredCourses();

            for (int i=0; i<vCourse.size(); i++) {
                if (((Course) vCourse.get(i)).conflicts(objCourse)) {
                    return "**REGISTRATION CONFLICTS**";
                }
            }
            return "**NO CONFLICTS**";
        }
        catch (RemoteException e1) {
            System.err.println("RemoteException occurred: " + e1.getMessage());
            e1.printStackTrace();
        }
        catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}