import java.rmi.Remote;
import java.rmi.RemoteException;

/*
    first step is to define an interface that all other methods can implement
 */

public interface IActivity extends Remote {
    Object execute() throws RemoteException;
}