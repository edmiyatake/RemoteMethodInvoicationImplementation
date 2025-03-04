import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;

public class ListStudentsHandler extends UnicastRemoteObject implements IActivity {
    private final DBInterface db;

    protected ListStudentsHandler(DBInterface db) throws RemoteException {
        super();
        this.db = db;
    }

    @Override
    public Object execute() {
        try {
            List<Student> students = db.getAllStudentRecords();
            return students;
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
