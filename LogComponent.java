import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class LogComponent extends CommandEventHandler{
    private static final String LOG_FILE = "SYSTEM.log";
    private PrintWriter writer;

    public LogComponent(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE, true));
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    protected String execute(String param) {
        String logMessage = "LOG: " + (param != null ? param : "No Data");

        System.out.println(logMessage);
        writer.println(logMessage);
        writer.flush();

        return "Logged: " + param;
    }
}
