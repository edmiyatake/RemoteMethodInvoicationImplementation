import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try (BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in))){
            // Connect to the RMI registry (on localhost, port 1099 for DBInterface)
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Look up the DBInterface object
            DBInterface db = (DBInterface) registry.lookup("DatabaseService");

            Registry handlerRegistry = LocateRegistry.getRegistry("localhost", 1100);

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
                    IActivity listStudents = (IActivity) handlerRegistry.lookup("ListStudents");
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

                if (sChoice.equals("2")) {  // Assuming "2" corresponds to the option for listing courses
                    IActivity listCourses = (IActivity) handlerRegistry.lookup("ListCourses");
                    Object result = listCourses.execute();
                    if (result instanceof List<?>) {
                        List<?> courses = (List<?>) result;
                        for (Object course : courses) {
                            if (course instanceof Course) {
                                System.out.println(((Course) course).getName());
                            }
                        }
                    } else {
                        System.out.println("Failed to retrieve course list.");
                    }
                }

                if (sChoice.equals("3")) {  // List all students who registered for a course
                    // Get the course ID and section from the user
                    System.out.print("Enter Course ID: ");
                    String courseId = objReader.readLine();
                    System.out.print("Enter Section Number: ");
                    String sectionNumber = objReader.readLine();

                    // Lookup the ListStudentsRegistered handler
                    IActivity listStudentsRegistered = (IActivity) handlerRegistry.lookup("StudentsRegistered");

                    // Set the courseId and sectionNumber in the handler
                    // Create a new handler with parameters
                    IActivity listStudentsHandler = new ListStudentsRegisteredHandler(db, courseId, sectionNumber);

                    // Execute and get the result
                    String result = (String) listStudentsHandler.execute();

                    // Print the result
                    System.out.println(result);
                }

                if (sChoice.equals("4")) {
                    // Prompt user for student ID
                    System.out.print("Enter Student ID: ");
                    String studentId = objReader.readLine().trim();

                    // Lookup the ListCoursesRegistered handler in the registry
                    IActivity listCoursesRegisteredHandler = (IActivity) handlerRegistry.lookup("CoursesRegistered");

                    // Pass the student ID to the handler when executing
                    listCoursesRegisteredHandler = new ListCoursesRegisteredHandler(db, studentId); // Pass actual DBInterface here

                    // Execute the handler to get the list of courses the student is registered for
                    String result = (String) listCoursesRegisteredHandler.execute();

                    // Print the result (course list or error message)
                    System.out.println(result);
                }

                if (sChoice.equals("5")) {
                    // Prompt user for student ID
                    System.out.print("Enter Student ID: ");
                    String studentId = objReader.readLine().trim();

                    // Lookup the ListCoursesCompleted handler in the registry
                    IActivity listCoursesCompletedHandler = (IActivity) handlerRegistry.lookup("CoursesCompleted");

                    // Pass the student ID to the handler when executing
                    listCoursesCompletedHandler = new ListCoursesCompletedHandler(db, studentId); // Pass actual DBInterface here

                    // Execute the handler to get the list of completed courses
                    String result = (String) listCoursesCompletedHandler.execute();

                    // Print the result (course list or error message)
                    System.out.println(result);
                }

                if (sChoice.equals("6")) {
                    System.out.print("Enter Student ID: ");
                    String studentId = objReader.readLine();
                    System.out.print("Enter Course ID: ");
                    String courseId = objReader.readLine();
                    System.out.print("Enter Section ID: ");
                    String sectionId = objReader.readLine();

                    // both components do not actually have to stop the registrations, just need to set a warning
                    // Call CourseConflict check component
                    CourseConflictHandler conflictHandler = new CourseConflictHandler(db, studentId, courseId, sectionId);
                    String conflictResult = (String) conflictHandler.execute();
                    System.out.println(conflictResult);

                    // Call Overbook check component
                    OverbookHandler overbookHandler = new OverbookHandler(db, courseId, sectionId);
                    String overbookResult = (String) overbookHandler.execute();
                    System.out.println(overbookResult);

                    IActivity registerStudentHandler = (IActivity) handlerRegistry.lookup("RegisterStudent");
                    String registrationResult = (String) registerStudentHandler.execute();
                    System.out.println("Student registered successfully!");

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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void logUserAction(Registry handlerRegistry, String actionMessage) {
        try {
            // Access LogHandler to log the action
            IActivity logHandler = (IActivity) handlerRegistry.lookup("LogComponent");
            LogHandler handler = new LogHandler(actionMessage);
            handler.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
