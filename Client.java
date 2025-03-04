import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try (BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in))){
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);

            while(true) {
                System.out.println("\nStudent Registration System\n");
                System.out.println("1) List all students");
                System.out.println("2) List all courses");
                System.out.println("3) List students who registered for a course");
                System.out.println("4) List courses a student has registered for");
                System.out.println("5) List courses a student has completed");
                System.out.println("6) Register a student for a course");
                System.out.println("x) Exit");
                System.out.println("\nEnter your choice and press return >> ");
                String sChoice = objReader.readLine().trim();

                if (sChoice.equals("1")) {
                    IActivity listStudents = (IActivity) registry.lookup("ListStudents");
                    Object result = listStudents.execute();
                    if (result instanceof List<?>) {
                        List<?> students = (List<?>) result;
                        for (Object student : students) {
                            if (student instanceof Student) {
                                System.out.println(((Student) student).getName());
                            }
                        }
                    } else {
                        System.out.println("Failed to retrieve student list.");
                    }
                }

                if (sChoice.equals("2")) {
                    System.out.print("Enter Student ID: ");
                    String studentId = objReader.readLine();
                    System.out.print("Enter Course ID: ");
                    String courseId = objReader.readLine();

                    IActivity registerStudent = (IActivity) registry.lookup("RegisterStudent");
                    boolean success = (boolean) registerStudent.execute();
                    System.out.println(success ? "Registration Successful!" : "Registration Failed!");
                }

                if (sChoice.equalsIgnoreCase("X")) {
                    System.out.println("Exiting...");
                    break;
                }
            }
        }
        catch (RemoteException e) {
            System.err.println("Remote exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
