package model;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    
    ///----------------------------------------------------------------------------------------
    
    public void activateProfile(String email, String category) throws RemoteException, NotBoundException{
        server.activateProfile(email, category);
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
    
    public String getNumber(String email, String category) throws RemoteException, NotBoundException{
        String res = server.getNumber(email, category);
        return res;
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getStudentStudyPlanCoursesId(String number) throws RemoteException, NotBoundException{
        ArrayList<String> result = server.getStudentStudyPlanCoursesId(number);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public String[] getCourseData(String courseId) throws RemoteException, NotBoundException{
        String[] result = server.getCourseData(courseId);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getProfessorsId(String courseId) throws RemoteException, NotBoundException{
        ArrayList<String> result = server.getProfessorsId(courseId);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public String[] getUserData(String number) throws RemoteException, NotBoundException{
        String[] result = server.getUserData(number);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public boolean isUserSigned(String number, String courseId, String category) throws RemoteException{
        boolean response = server.isUserSigned(number, courseId, category);
        return response;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void signUserToCourse(String number, String courseId) throws RemoteException, NotBoundException{
        server.signUserToCourse(number, courseId);
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getProfessorHandledCoursesId(String number) throws RemoteException, NotBoundException{
        ArrayList<String> result = server.getProfessorHandledCoursesId(number);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void addSection(Section s) throws RemoteException, NotBoundException{
        server.addSection(s);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void addSectionToCourse(String courseId, String sectionId) throws RemoteException{
        server.addSectionToCourse(courseId, sectionId);
    }
    
    //----------------------------------------------------------------------------------------
    
    public void addResource(Resource r) throws RemoteException{
        server.addResource(r);
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getFoldersName(String sectionId) throws RemoteException{
        ArrayList<String> result = server.getFoldersName(sectionId);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getSectionsId(String courseId) throws RemoteException{
        ArrayList<String> result = server.getSectionsId(courseId);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void deleteSection(String sectionId) throws RemoteException{
        server.deleteSection(sectionId);
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getSectionName(String sectionId) throws RemoteException{
        ArrayList<String> result = server.getSectionName(sectionId);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public String[] getSectionData(String sectionId) throws RemoteException{
        String[] result = server.getSectionData(sectionId);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getSubsectionData(String upsectionId) throws RemoteException{
        ArrayList<String> result = server.getSubsectionData(upsectionId);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public ArrayList<String> getResourcesData(String sectionId) throws RemoteException{
        ArrayList<String> result = server.getResourcesData(sectionId);
        return result;
    }
    
    //----------------------------------------------------------------------------------------
    
    public void increaseLoggedUsersNumber() throws RemoteException{
        server.increaseLoggedUsersNumber();
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
    
    public void sendData(String filename, byte[] data, int len) throws RemoteException{
        server.sendData(filename, data, len);
    }
    
    //----------------------------------------------------------------------------------------
   
    public List<String> downloadFile(String filename) throws RemoteException, IOException{
        List<String> res = server.downloadFile(filename);
        return res;
    }
    
    //----------------------------------------------------------------------------------------
    
    public long getLoggedUsersNumber() throws RemoteException{
        return server.getLoggedUsersNumber();
    }
    
    //----------------------------------------------------------------------------------------
    
    public void increaseUsersVisualizingCoursesNumber() throws RemoteException{
        server.increaseUsersVisualizingCoursesNumber();
    }
    
    //----------------------------------------------------------------------------------------
    
    public long getUsersVisualizingCoursesNumber() throws RemoteException{
        return server.getUsersVisualizingCoursesNumber();
    }
    
    //----------------------------------------------------------------------------------------
    
    public void decreaseUsersVisualizingCoursesNumber() throws RemoteException{
        server.decreaseUsersVisualizingCoursesNumber();
    }
    
    //----------------------------------------------------------------------------------------
    
    public void updateDownloadNumberAndDate(String resourceId, Date d) throws RemoteException{
        server.updateDownloadNumberAndDate(resourceId, d);
    }
    
    //----------------------------------------------------------------------------------------
    
    public String getVisibility(String sectionId) throws RemoteException{
        String res = server.getVisibility(sectionId);
        return res;
    }
    
    
    
    public ArrayList<String> getUpsections() throws RemoteException{
        ArrayList<String> result = server.getUpsections();
        return result;
    }
    
}
