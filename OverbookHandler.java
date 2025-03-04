import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;

public class OverbookHandler extends UnicastRemoteObject implements IActivity {
    private final DBInterface db;
    private final String sCID;
    private final String sSection;


    protected OverbookHandler(DBInterface db, String courseId, String sectionNumber) throws RemoteException {
        super();
        this.db = db;
        this.sCID = courseId;
        this.sSection = sectionNumber;
    }

    @Override
    public Object execute() {
        try {
//          List<Student> students = db.getAllStudentRecords(); return students;
            int currStudents = db.numStudents(sCID,sSection);
            int OVERBOOK_CAPACITY = 3;
            if (currStudents < OVERBOOK_CAPACITY) {
                return "Overbooked!";
            }
            else {
                return "No Problem!";
            }


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