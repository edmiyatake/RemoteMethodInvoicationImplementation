import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/*
    interface for access the database methods
 */
public interface DBInterface extends Remote {
    List<Student> getAllStudentRecords() throws RemoteException;
    boolean makeARegistration(String sSID, String sCID, String sSection) throws RemoteException;
}