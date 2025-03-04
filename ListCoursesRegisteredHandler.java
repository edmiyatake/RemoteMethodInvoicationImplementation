import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;

public class ListCoursesRegisteredHandler extends UnicastRemoteObject implements IActivity {
    private final DBInterface db;
    private final String sSID;


    protected ListCoursesRegisteredHandler(DBInterface db, String studentId) throws RemoteException {
        super();
        this.db = db;
        this.sSID = studentId;
    }

    @Override
    public Object execute() {
        try {
            Student objStudent = this.db.getStudentRecord(sSID);
            if (objStudent == null) {
                return "Invalid student ID";
            }
            List<Course> vCourse = objStudent.getRegisteredCourses();

            // Construct a list of course information and return it.
            String sReturn = "";
            for (int i=0; i<vCourse.size(); i++) {
                sReturn += (i == 0 ? "" : "\n") + ((Course) vCourse.get(i)).toString();
            }
            return sReturn;
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