import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ListCoursesCompletedHandler extends UnicastRemoteObject implements IActivity {
    private final DBInterface db;
    private final String sSID;


    protected ListCoursesCompletedHandler(DBInterface db, String studentId) throws RemoteException {
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
            ArrayList vCourseID = objStudent.getCompletedCourses();

            // Construct a list of course information and return it.
            String sReturn = "";
            for (int i=0; i<vCourseID.size(); i++) {
                String sCID = (String) vCourseID.get(i);
                String sName = this.db.getCourseName(sCID);
                sReturn += (i == 0 ? "" : "\n") + sCID + " " + (sName == null ? "Unknown" : sName);
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