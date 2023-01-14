package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ale
 */
public interface SeatInServerInterface extends Remote{
    
    public boolean login(String email, String password, String category) throws RemoteException;
    
    public boolean isProfileActivated(String email, String category) throws RemoteException;
    
    public boolean isActivationCodeValid(String activationCode, String email, String category) throws RemoteException;
    
    public void resetPassword(String email, String password, String category) throws RemoteException;
    
    public boolean isEmailValid(String email, String category) throws RemoteException;
    
    public void receiveNewPasswordAndActivationCode(String email, String category) throws RemoteException;
    
    public void activateProfile(String email, String category) throws RemoteException;
    
    public String getNumber(String email, String category) throws RemoteException;
    
    public ArrayList<String> getStudentStudyPlanCoursesId(String number) throws RemoteException;
    
    public String[] getCourseData(String courseId) throws RemoteException;
    
    public ArrayList<String> getProfessorsId(String courseId) throws RemoteException;
    
    public String[] getUserData(String number) throws RemoteException;
    
    public boolean isUserSigned(String number, String courseId, String category) throws RemoteException;
    
    public void signUserToCourse(String number, String courseId) throws RemoteException;
    
    public ArrayList<String> getProfessorHandledCoursesId(String number) throws RemoteException;
    
    public void addSection(Section s) throws RemoteException;
    
    public void addSectionToCourse(String courseId, String sectionId) throws RemoteException;
    
    public void addResource(Resource r) throws RemoteException;
    
    public ArrayList<String> getFoldersName(String sectionId) throws RemoteException;
    
    public ArrayList<String> getSectionsId(String courseId) throws RemoteException;
    
    public void deleteSection(String sectionId) throws RemoteException;
    
    public ArrayList<String> getSectionName(String sectionId) throws RemoteException;
    
    public String[] getSectionData(String sectionId) throws RemoteException;
    
    public ArrayList<String> getSubsectionData(String upsectionId) throws RemoteException;
    
    public ArrayList<String> getResourcesData(String sectionId) throws RemoteException;
    
    public void increaseLoggedUsersNumber() throws RemoteException;
    
    public void decreaseLoggedUsersNumber() throws RemoteException;
    
    public long getAuthAttempts(String email, String category) throws RemoteException;
    
    public void increaseAuthAttempts(long attempts, String email, String category) throws RemoteException;
    
    public void deactivateProfile(String email, String category) throws RemoteException;
    
    public void sendData(String filename, byte[] data, int len) throws RemoteException;
    
    public List<String> downloadFile(String filename) throws RemoteException;
    
    public void increaseUsersVisualizingCoursesNumber() throws RemoteException;
    
    public long getLoggedUsersNumber() throws RemoteException;
    
    public long getUsersVisualizingCoursesNumber() throws RemoteException;
    
    public void decreaseUsersVisualizingCoursesNumber() throws RemoteException;
    
    public void updateDownloadNumberAndDate(String resourceId, Date d) throws RemoteException;
    
    public String getVisibility(String sectionId) throws RemoteException;
    
    public ArrayList<String> getUpsections() throws RemoteException;
    
}