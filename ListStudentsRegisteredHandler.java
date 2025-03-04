import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;

public class ListStudentsRegisteredHandler extends UnicastRemoteObject implements IActivity {
    private final DBInterface db;
    private final String sCID;
    private final String sSection;


    protected ListStudentsRegisteredHandler(DBInterface db, String courseId, String sectionNumber) throws RemoteException {
        super();
        this.db = db;
        this.sCID = courseId;
        this.sSection = sectionNumber;
    }

    @Override
    public Object execute() {
        try {
//          List<Student> students = db.getAllStudentRecords(); return students;
            Course objCourse = db.getCourseRecord(sCID,sSection);
            if (objCourse == null) {
                return "Invalid course ID or course section";
            }
            List<Student> vStudent = objCourse.getRegisteredStudents();

            // Construct a list of student information and return it.
            String sReturn = "";
            for (int i=0; i<vStudent.size(); i++) {
                sReturn += (i == 0 ? "" : "\n") + ((Student) vStudent.get(i)).toString();
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