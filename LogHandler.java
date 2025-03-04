import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class LogHandler extends UnicastRemoteObject implements IActivity {
    private static final String LOG_FILE = "SYSTEM.log";
    private PrintWriter writer;
    private String message;

    protected LogHandler(String param) throws RemoteException {
        super();
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE, true));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Object execute() {
        try {
            String logMessage = "LOG: " + (message != null ? message : "No Data");

            System.out.println(logMessage);
            writer.println(logMessage);
            writer.flush();

            return "Logged: " + message;
        }
        catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
