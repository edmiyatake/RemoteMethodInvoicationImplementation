import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/*
    interface for access the database methods
 */
public interface DBInterface extends Remote {
    List<Student> getAllStudentRecords() throws RemoteException;
    List<Course> getAllCourseRecords() throws RemoteException;
    Student getStudentRecord(String sSID) throws RemoteException;
    String getStudentName(String sSID) throws RemoteException;
    Course getCourseRecord(String sCID, String sSection) throws RemoteException;
    String getCourseName(String sCID) throws RemoteException;
    void makeARegistration(String sSID, String sCID, String sSection) throws RemoteException;
    int numStudents(String sCID, String sSection) throws RemoteException;
}