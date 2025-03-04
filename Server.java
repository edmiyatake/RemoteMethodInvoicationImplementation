import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // we want to access the database
            Registry dbRegistry = LocateRegistry.getRegistry("localhost", 1099);
            DBInterface db = (DBInterface) dbRegistry.lookup("DatabaseService");

            // create a new registry for the new methods
            Registry handlerRegistry = LocateRegistry.createRegistry(1100);

            // bind the new methods to the handler registry
            handlerRegistry.rebind("ListStudents", new ListStudentsHandler(db));
            handlerRegistry.rebind("RegisterStudent", new RegisterStudentHandler(db, "student123", "course456", "section789"));

            // to check if the server is running correctly
            System.out.println("Server is running...");
        }
        catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
