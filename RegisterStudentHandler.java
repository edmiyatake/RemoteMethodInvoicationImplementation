import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
        return db.makeARegistration(sSID, sCID,sSection);
    }
}