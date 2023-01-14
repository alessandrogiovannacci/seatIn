package model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 *
 * @author Ale
 */
public class ProxyServer {
    
    private SeatInServerInterface server;
    
    public ProxyServer()throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry();
        server = (SeatInServerInterface) registry.lookup("SeatInServerInterface");
    }
       
    //----------------------------------------------------------------------------------------
       
    public boolean login(String email, String password, String category) throws RemoteException, NotBoundException{
        
        boolean response = server.login(email, password, category);
        return response;
    }
    
    //----------------------------------------------------------------------------------------
    
    public boolean isProfileActivated(String email, String category) throws RemoteException, NotBoundException{
        boolean response = server.isProfileActivated(email, category);
        return response;
    }
    
    //----------------------------------------------------------------------------------------
    
    public boolean isActivationCodeValid(String activationCode, String email, String category) throws RemoteException, NotBoundException{
        boolean response = server.isActivationCodeValid(activationCode, email, category);
        return response;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void resetPassword(String email, String password, String category) throws RemoteException, NotBoundException{
        server.resetPassword(email, password, category);
    }
    
    //----------------------------------------------------------------------------------------
    
    public boolean isEmailValid(String email, String category) throws RemoteException, NotBoundException{
        boolean response = server.isEmailValid(email, category);
        return response;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void receiveNewPasswordAndActivationCode(String email, String category) throws RemoteException, NotBoundException{
        server.receiveNewPasswordAndActivationCode(email, category);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void activateProfile(String email, String category) throws RemoteException, NotBoundException{
        server.activateProfile(email, category);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void insertUser(User u) throws RemoteException, NotBoundException{
        server.insertUser(u);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void insertStudent(Student s) throws RemoteException, NotBoundException{
        server.insertStudent(s);
    }
    
    //----------------------------------------------------------------------------------------
    
    public String[] getUserData(String number) throws RemoteException, NotBoundException{
        String[] result = server.getUserData(number);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void updateName(String number, String name, String typeOfUser) throws RemoteException{
        server.updateName(number, name, typeOfUser);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void updateSurname(String number, String surname, String typeOfUser) throws RemoteException{
        server.updateSurname(number, surname, typeOfUser);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void updateDepartment(String number, String department) throws RemoteException{
        server.updateDepartment(number, department);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void updateEmail(String number, String email, String typeOfUser) throws RemoteException{
        server.updateEmail(number, email, typeOfUser);
    }
    
    //----------------------------------------------------------------------------------------
    
    public String[] getStudentData(String number) throws RemoteException{
        String[] result = server.getStudentData(number);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void updateDegreeCourse(String number, String degreeCourse) throws RemoteException{
        server.updateDegreeCourse(number, degreeCourse);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void updateCareerState(String number, String careerState) throws RemoteException{
        server.updateCareerState(number, careerState);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void insertCourse(Course c) throws RemoteException{
        server.insertCourse(c);
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getNumbers(String category) throws RemoteException{
        ArrayList<String> result = server.getNumbers(category);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getCoursesId() throws RemoteException{
        ArrayList<String> result = server.getCoursesId();
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void assignCourseToStudent(String number, String courseId) throws RemoteException{
        server.assignCourseToStudent(number, courseId);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void assignCourseToUser(String number, String courseId) throws RemoteException{
        server.assignCourseToUser(number, courseId);
    }
    
    //----------------------------------------------------------------------------------------
    
    public boolean isStudyPlanAlreadyRegistered(String number) throws RemoteException{
        return server.isStudyPlanAlreadyRegistered(number);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void registerStudyPlan(String number) throws RemoteException{
        server.registerStudyPlan(number);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void increaseLoggedUsersNumber() throws RemoteException{
        server.increaseLoggedUsersNumber();
    }
    
    //----------------------------------------------------------------------------------------
    
    public long getLoggedUsersNumber() throws RemoteException{
        return server.getLoggedUsersNumber();
    }

    //----------------------------------------------------------------------------------------
    
    public void decreaseLoggedUsersNumber() throws RemoteException{
        server.decreaseLoggedUsersNumber();
    }
    
    //----------------------------------------------------------------------------------------
    
    public long getAuthAttempts(String email, String category) throws RemoteException{
        long result = server.getAuthAttempts(email, category);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void increaseAuthAttempts(long attempts, String email, String category) throws RemoteException{
        server.increaseAuthAttempts(attempts, email, category);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void deactivateProfile(String email, String category) throws RemoteException{
        server.deactivateProfile(email, category);
    }
    
    //----------------------------------------------------------------------------------------
    
    public long getUsersVisualizingCoursesNumber() throws RemoteException{
        return server.getUsersVisualizingCoursesNumber();
    }
    
}
