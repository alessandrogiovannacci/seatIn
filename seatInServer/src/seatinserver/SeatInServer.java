package seatinserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import model.Course;
import model.EmailSender;
import model.PlatformMonitor;
import model.Resource;
import sql.SqlConnectionManager;
import sql.SqlDMLManager;
import model.SeatInServerInterface;
import model.Section;
import model.Student;
import model.User;

/**
 *
 * @author Ale
 */
public class SeatInServer implements SeatInServerInterface{
    
    //fields
    private static String dbHost;
    private static String dbUsername;
    private static String dbPassword;
    private static Connection conn;
    private final PlatformMonitor monitor;
    
    //builder
    public SeatInServer(){
        monitor = new PlatformMonitor();
    }
    
    //--------------------------------------------------------------------------------------------
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws IOException, SQLException{
        requestDBCredentials();
        SqlConnectionManager sqlConnManager = new SqlConnectionManager(dbHost, dbUsername, dbPassword);
        conn = sqlConnManager.getConnection();
        SqlDMLManager sqlDMLManager = new SqlDMLManager();
        long numberOfAdmin = sqlDMLManager.viewAdminNumber(conn, SqlDMLManager.SelectQuery.NUMBER_OF_ADMIN.toString());
        
        if(numberOfAdmin == 0){
            System.out.println("No administrators found.");
            AdminRegister admin = new AdminRegister();
            PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.INSERT_USER.toString());
                stmt.setString(1, admin.getNumber());
                stmt.setString(2, admin.getName());
                stmt.setString(3, admin.getSurname());
                stmt.setString(4, admin.getRole());
                stmt.setString(5, admin.getDepartment());
                stmt.setString(6, admin.getEmail());
                stmt.setString(7, admin.getPassword());
                stmt.setString(8, admin.getActivationCode());
                stmt.setBoolean(9, admin.getProfileActivated());
                stmt.setInt(10, admin.getAuthAttempts());
                stmt.executeUpdate();
		
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
			stmt.close();
                }
            }
        }
        
        System.out.println("Use email and password in seatInAdmin in order to login.");
        
        //setting rmi
        SeatInServerInterface server = new SeatInServer();
        SeatInServerInterface stub = (SeatInServerInterface) UnicastRemoteObject.exportObject((SeatInServerInterface) server, 0);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("SeatInServerInterface", stub);
        
    }
    
    //--------------------------------------------------------------------------------------------
    
    private static void requestDBCredentials(){
        Scanner in = new Scanner(System.in);
        System.out.println("seatInServer");
        System.out.println();
        System.out.println("Getting access to database");
        System.out.print("Insert database host: ");
        dbHost = in.nextLine();
        System.out.print("Insert username: ");
        dbUsername = in.nextLine();
        System.out.print("Insert password: ");
        dbPassword = in.nextLine();
    }
    
    //--------------------------------------------------------------------------------------------
        
    @Override
    public boolean login(String email, String password, String category){
        SqlDMLManager dmlManager = new SqlDMLManager();
        String[] res = new String[2];
        try {
            if(!category.equals("Student")){
                res = dmlManager.login(conn, SqlDMLManager.SelectQuery.USER_LOGIN.toString(), email);
            }
            else{
                res = dmlManager.login(conn, SqlDMLManager.SelectQuery.STUDENT_LOGIN.toString(), email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(res[0].equals(email) && res[1].equals(password)){
            return true;
        }
        else{
            return false;
        }
    }
    
    //--------------------------------------------------------------------------------------------
    
    @Override
    public boolean isProfileActivated(String email, String category){
        SqlDMLManager dmlManager = new SqlDMLManager();
        boolean res = false;
        
        try {
            if(!category.equals("Student")){
                res = dmlManager.isProfileActivated(conn, SqlDMLManager.SelectQuery.USER_PROFILE_ACTIVATED.toString(), email);
            }
            else{
                res = dmlManager.isProfileActivated(conn, SqlDMLManager.SelectQuery.STUDENT_PROFILE_ACTIVATED.toString(), email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    //--------------------------------------------------------------------------------------------
    
    @Override
    public boolean isActivationCodeValid(String activationCode, String email, String category){
        SqlDMLManager dmlManager = new SqlDMLManager();
        String res = "";
        try {
            if(!category.equals("Student")){
                res = dmlManager.getActivationCode(conn, SqlDMLManager.SelectQuery.USER_ACTIVATIONCODE.toString(), email);
            }
            else{
                res = dmlManager.getActivationCode(conn, SqlDMLManager.SelectQuery.STUDENT_ACTIVATIONCODE.toString(), email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(res.equals(activationCode)){
            return true;
        }
        else{
            return false;
        }
    }
    
    //--------------------------------------------------------------------------------------------
    
    @Override
    public void resetPassword(String email, String password, String category){
        PreparedStatement stmt = null;
            try{
                if(!category.equals("Student")){
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.RESET_USER_PASSWORD.toString());
                }
                else{
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.RESET_STUDENT_PASSWORD.toString());
                }
                stmt.setString(1, password);
                stmt.setString(2, email);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //--------------------------------------------------------------------------------------------
    
    @Override
    public boolean isEmailValid(String email, String category){
       SqlDMLManager dmlManager = new SqlDMLManager();
        String res = "";
        try {
            if(!category.equals("Student")){
                res = dmlManager.getEmail(conn, SqlDMLManager.SelectQuery.USER_EMAIL.toString(), email);
            }
            else{
                res = dmlManager.getEmail(conn, SqlDMLManager.SelectQuery.STUDENT_EMAIL.toString(), email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(res.equals(email)){
            return true;
        }
        else{
            return false;
        } 
    }
    
    //--------------------------------------------------------------------------------------------
    
    @Override
    public void receiveNewPasswordAndActivationCode(String email, String category){
        PreparedStatement stmt = null;
        
        /*
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
        .useDigits(true)
        .useLower(true)
        .useUpper(true)
        .build();
        String password = passwordGenerator.generate(8);
        String activationCode = passwordGenerator.generate(8);
        */
        
        String password = "ccc";
        String activationCode = "ccc";
        try{
            if(!category.equals("Student")){
                stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.RESET_USER_PASSWORD.toString());
            }
            else{
                stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.RESET_STUDENT_PASSWORD.toString());
            }
             stmt.setString(1, password);
             stmt.setString(2, email);
             stmt.executeUpdate();
        }catch(SQLException ex){}
        finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
         }
         
         stmt = null;
            
         try{
             if(!category.equals("Student")){
                stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.RESET_USER_ACTIVATIONCODE.toString());
             }
             else{
                stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.RESET_STUDENT_ACTIVATIONCODE.toString());
             }
             stmt.setString(1, activationCode);
             stmt.setString(2, email);
             stmt.executeUpdate();
        }catch(SQLException ex){}
        finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
         }   
         
         try {
            new EmailSender(email, password, activationCode).sendPasswordRecoveryEmail();
        } catch (MessagingException ex) {
            Logger.getLogger(AdminRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //--------------------------------------------------------------------------------------------
    
    @Override
    public void activateProfile(String email, String category){
        PreparedStatement stmt = null;
        try{
            if(!category.equals("Student")){
                stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.ACTIVATE_USER_PROFILE.toString());
            }
            else{
                stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.ACTIVATE_STUDENT_PROFILE.toString());
            }
            stmt.setString(1, email);
            stmt.executeUpdate();
        }catch(SQLException ex){}
        finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
         }
    }
    
    //--------------------------------------------------------------------------------------------
    
    @Override
    public void insertStudent(Student s){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.INSERT_STUDENT.toString());
                stmt.setString(1, s.getNumber());
                stmt.setString(2, s.getName());
                stmt.setString(3, s.getSurname());
                stmt.setString(4, s.getDegreeCourse());
                stmt.setString(5, s.getCareerState());
                stmt.setString(6, s.getEmail());
                stmt.setString(7, s.getPassword());
                stmt.setInt(8, s.getRegistrationYear());
                stmt.setString(9, s.getActivationCode());
                stmt.setBoolean(10, s.getProfileActivated());
                stmt.setInt(11, s.getAuthAttempts());
                stmt.executeUpdate();
		
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
          
        try{
            new EmailSender(s.getEmail(), s.getPassword(), s.getActivationCode()).sendRegEmail();
        } catch (MessagingException ex) {
            Logger.getLogger(AdminRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //--------------------------------------------------------------------------------------------
    
    @Override
    public void insertUser(User u){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.INSERT_USER.toString());
                stmt.setString(1, u.getNumber());
                stmt.setString(2, u.getName());
                stmt.setString(3, u.getSurname());
                stmt.setString(4, u.getRole());
                stmt.setString(5, u.getDepartment());
                stmt.setString(6, u.getEmail());
                stmt.setString(7, u.getPassword());
                stmt.setString(8, u.getActivationCode());
                stmt.setBoolean(9, u.getProfileActivated());
                stmt.setInt(10, u.getAuthAttempts());
                stmt.executeUpdate();
		
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        try{
            new EmailSender(u.getEmail(), u.getPassword(), u.getActivationCode()).sendRegEmail();
        } catch (MessagingException ex) {
            Logger.getLogger(AdminRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //*******************************************************************************************************
    
    @Override
    public String[] getUserData(String number){
        SqlDMLManager dmlManager = new SqlDMLManager();
        String[] res = new String[4];
        
        try {
            res = dmlManager.getUserData(conn, SqlDMLManager.SelectQuery.USER_DATA.toString(), number);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //******************************************************************************************************
    
    @Override
    public void updateName(String number, String name, String typeOfUser){
            PreparedStatement stmt = null;
            try{
                if(typeOfUser.equals("User")){
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_USER_NAME.toString());
                }
                else{
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_STUDENT_NAME.toString());
                }
                stmt.setString(1, name);
                stmt.setString(2, number);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public void updateSurname(String number, String surname, String typeOfUser){
            PreparedStatement stmt = null;
            try{
                if(typeOfUser.equals("User")){
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_USER_SURNAME.toString());
                }
                else{
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_STUDENT_SURNAME.toString());
                }
                stmt.setString(1, surname);
                stmt.setString(2, number);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public void updateDepartment(String number, String department){
            PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_USER_DEPARTMENT.toString());
                stmt.setString(1, department);
                stmt.setString(2, number);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public void updateEmail(String number, String email, String typeOfUser){
            PreparedStatement stmt = null;
            try{
                if(typeOfUser.equals("User")){
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_USER_EMAIL.toString());
                }
                else{
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_STUDENT_EMAIL.toString());
                }
                stmt.setString(1, email);
                stmt.setString(2, number);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public String[] getStudentData(String number){
        SqlDMLManager dmlManager = new SqlDMLManager();
        String[] res = new String[5];
        
        try {
            res = dmlManager.getStudentData(conn, SqlDMLManager.SelectQuery.STUDENT_DATA.toString(), number);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public void updateDegreeCourse(String number, String degreeCourse){
            PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_STUDENT_DEGREECOURSE.toString());
                stmt.setString(1, degreeCourse);
                stmt.setString(2, number);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public void updateCareerState(String number, String careerState){
            PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_STUDENT_CAREERSTATE.toString());
                stmt.setString(1, careerState);
                stmt.setString(2, number);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public void insertCourse(Course c){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.INSERT_COURSE.toString());
                stmt.setString(1, c.getID());
                stmt.setString(2, c.getName());
                stmt.setInt(3, c.getActivationYear());
                stmt.setString(4, c.getDegreeCourse());
                stmt.setString(5, c.getDescription());
                stmt.executeUpdate();
		
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getNumbers(String category){
        
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
            if(category.equals("Student")){
                res = dmlManager.getStudentsNumbers(conn, SqlDMLManager.SelectQuery.STUDENTS_NUMBERS.toString());
            }
            else{
                res = dmlManager.getUsersNumbers(conn, SqlDMLManager.SelectQuery.USERS_NUMBERS.toString(), category);
            }
          
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return res;

    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getCoursesId(){
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
            res = dmlManager.getStudentsNumbers(conn, SqlDMLManager.SelectQuery.COURSES_ID.toString());
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public void assignCourseToStudent(String number, String courseId){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.ASSIGN_COURSE_TO_STUDENT.toString());
                stmt.setString(1, number);
                stmt.setString(2, courseId);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public void assignCourseToUser(String number, String courseId){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.ASSIGN_COURSE_TO_USER.toString());
                stmt.setString(1, number);
                stmt.setString(2, courseId);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public boolean isStudyPlanAlreadyRegistered(String number){
        SqlDMLManager dmlManager = new SqlDMLManager();
        boolean res = false;
        
        try {
            res = dmlManager.isStudyPlanAlreadyRegistered(conn, SqlDMLManager.SelectQuery.STUDYPLAN_ALREADY_REGISTERED.toString(), number);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public void registerStudyPlan(String number){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.REGISTER_STUDYPLAN.toString());
                stmt.setString(1, number);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public String getNumber(String email, String category){
        SqlDMLManager dmlManager = new SqlDMLManager();
        String res = null;
        
        try {
            if(!category.equals("Student")){
                res = dmlManager.getNumber(conn, SqlDMLManager.SelectQuery.USER_NUMBER.toString(), email);
            }
            else{
                res = dmlManager.getNumber(conn, SqlDMLManager.SelectQuery.STUDENT_NUMBER.toString(), email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getStudentStudyPlanCoursesId(String number){
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
             res = dmlManager.getStudentStudyPlanCoursesId(conn, SqlDMLManager.SelectQuery.STUDENT_COURSES_IN_STUDY_PLAN.toString(), number);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public String[] getCourseData(String courseId){
        SqlDMLManager dmlManager = new SqlDMLManager();
        String[] res = new String[2];
        
        try {
            res = dmlManager.getCourseData(conn, SqlDMLManager.SelectQuery.COURSE_DATA.toString(), courseId);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getProfessorsId(String courseId){
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
             res = dmlManager.getProfessorsId(conn, SqlDMLManager.SelectQuery.PROFESSORS_ID.toString(), courseId);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public boolean isUserSigned(String number, String courseId, String category) throws RemoteException{
        SqlDMLManager dmlManager = new SqlDMLManager();
        boolean res = false;
        int queryResult = 0;
        
        try {
            if(!category.equals("Student")){
                queryResult = dmlManager.isUserSigned(conn, SqlDMLManager.SelectQuery.IS_PROFESSOR_SIGNED_TO_COURSE.toString(), number, courseId);
            }
            else{
                queryResult = dmlManager.isUserSigned(conn, SqlDMLManager.SelectQuery.IS_STUDENT_SIGNED_TO_COURSE.toString(), number, courseId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(queryResult == 0)
            return false;
        else
            return true;
    }
    
    //*****************************************************************************************************
    
    @Override
    public void signUserToCourse(String number, String courseId){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.SIGN_STUDENT_TO_COURSE.toString());
                stmt.setString(1, number);
                stmt.setString(2, courseId);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        SqlDMLManager dmlManager = new SqlDMLManager();
        String email = "";
        try {
            email = dmlManager.getEmail(conn, SqlDMLManager.SelectQuery.STUDENT_EMAIL_GETBYNUMBER.toString(), number);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
            new EmailSender(email).sendCourseSubscriptionEmail(email);
        } catch (MessagingException ex) {
            Logger.getLogger(AdminRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getProfessorHandledCoursesId(String number){
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
             res = dmlManager.getProfessorHandledCoursesId(conn, SqlDMLManager.SelectQuery.PROFESSOR_HANDLED_COURSES.toString(), number);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public void addSection(Section s){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.INSERT_SECTION.toString());
                stmt.setString(1, s.getId());
                stmt.setString(2, s.getName());
                stmt.setString(3, s.getDescription());
                stmt.setString(4, s.getVisibility());
                stmt.setString(5, s.getUpsection());
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public void addSectionToCourse(String courseId, String sectionId){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.INSERT_SECTION_IN_COURSE.toString());
                stmt.setString(1, courseId);
                stmt.setString(2, sectionId);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public void addResource(Resource r){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.InsertQuery.INSERT_RESOURCE.toString());
                stmt.setString(1, r.getId());
                stmt.setString(2, r.getName());
                stmt.setString(3, r.getDescription());
                stmt.setString(4, r.getType());
                stmt.setString(5, r.getVisibility());
                stmt.setString(6, r.getSectionId());
                stmt.setString(7, r.getFolder());
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getFoldersName(String sectionId){
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
             res = dmlManager.getFoldersName(conn, SqlDMLManager.SelectQuery.FOLDERS_NAME.toString(), sectionId);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getSectionsId(String courseId){
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
             res = dmlManager.getSectionsId(conn, SqlDMLManager.SelectQuery.SECTIONS_ID.toString(), courseId);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public void deleteSection(String sectionId){
        PreparedStatement stmt = null;
            try{
                stmt= conn.prepareStatement(SqlDMLManager.DeleteQuery.DELETE_SECTION.toString());
                stmt.setString(1, sectionId);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getSectionName(String sectionId){
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
             res = dmlManager.getSectionName(conn, SqlDMLManager.SelectQuery.SECTION_NAME.toString(), sectionId);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public String[] getSectionData(String sectionId){
        SqlDMLManager dmlManager = new SqlDMLManager();
        String[] res = new String[2];
        
        try {
            res = dmlManager.getSectionData(conn, SqlDMLManager.SelectQuery.SECTION_DATA.toString(), sectionId);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getSubsectionData(String upsectionId){
       SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
             res = dmlManager.getSubsectionData(conn, SqlDMLManager.SelectQuery.SUBSECTION_DATA.toString(), upsectionId);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getResourcesData(String sectionId){
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
             res = dmlManager.getResourcesData(conn, SqlDMLManager.SelectQuery.RESOURCES_DATA.toString(), sectionId);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public void increaseLoggedUsersNumber(){
        this.monitor.increaseConnectedUsersNumber();
    }
    
    //*****************************************************************************************************
    
    @Override
    public void increaseUsersVisualizingCoursesNumber(){
        this.monitor.increaseUsersVisualizingCoursesNumber();
    }
    
    //*****************************************************************************************************
    
    @Override
    public long getLoggedUsersNumber(){
        return this.monitor.getConnectedUsersNumber();
    }
    
    //*****************************************************************************************************
    
    @Override
    public long getUsersVisualizingCoursesNumber(){
        return this.monitor.getUsersVisualizingCoursesContentsNumber();
    }
    
    //*****************************************************************************************************
    
    @Override
    public void decreaseUsersVisualizingCoursesNumber(){
        this.monitor.decreaseUsersVisualizingCoursesNumber();
    }
    
    //*****************************************************************************************************
    
    @Override
    public void decreaseLoggedUsersNumber(){
        this.monitor.decreaseConnectedUsersNumber();
    }
    
    //*****************************************************************************************************
    
    @Override
    public void increaseAuthAttempts(long attempts, String email, String category){
        PreparedStatement stmt = null;
            try{
                if(!category.equals("Student")){
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_USER_AUTHATTEMPTS.toString());
                }
                else{
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_STUDENT_AUTHATTEMPTS.toString());
                }
                stmt.setLong(1, attempts);
                stmt.setString(2, email);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public long getAuthAttempts(String email, String category){
        SqlDMLManager dmlManager = new SqlDMLManager();
        long res = 0;
        
        try {
            if(!category.equals("Student")){
                res = dmlManager.getAuthAttempts(conn, SqlDMLManager.SelectQuery.USER_AUTHATTEMPTS.toString(), email);
            }
            else{
                res = dmlManager.getAuthAttempts(conn, SqlDMLManager.SelectQuery.STUDENT_AUTHATTEMPTS.toString(), email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public void deactivateProfile(String email, String category){
        PreparedStatement stmt = null;
            try{
                if(!category.equals("Student")){
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.DEACTIVATE_USER_PROFILE.toString());
                }
                else{
                    stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.DEACTIVATE_STUDENT_PROFILE.toString());
                }
                stmt.setString(1, email);
                stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public void sendData(String filename, byte[] data, int len){
        File file = new File("C:\\Users\\Ale\\Documents\\NetBeansProjects\\seatInServer\\" + filename);
        FileOutputStream out = null;
        if(!file.exists()){
            try {
                file.createNewFile();
                out = new FileOutputStream(file, true);
                out.write(data, 0, len);
                out.flush();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //*****************************************************************************************************
    
    @Override
    public List<String> downloadFile(String filename){
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("C:\\Users\\Ale\\Desktop\\seatIn\\seatInServer\\" + filename));
        } catch (IOException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lines;
    }
    
    //*****************************************************************************************************
    
    @Override
    public void updateDownloadNumberAndDate(String resourceId, Date d){
        PreparedStatement stmt = null;
            try{
                
               stmt= conn.prepareStatement(SqlDMLManager.UpdateQuery.UPDATE_DOWNLOAD_NUMBER_AND_DATE.toString());
               stmt.setDate(1, d);
               stmt.setString(2, resourceId);
               stmt.executeUpdate();
            }
            catch(SQLException ex){
			
            }finally{
		if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
    //*****************************************************************************************************
    
    @Override
    public String getVisibility(String sectionId){
        SqlDMLManager dmlManager = new SqlDMLManager();
        String res = "";
        try {
             res = dmlManager.getSectionVisibility(conn, SqlDMLManager.SelectQuery.SECTION_VISIBILITY.toString(), sectionId);
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    //*****************************************************************************************************
    
    @Override
    public ArrayList<String> getUpsections(){
        SqlDMLManager dmlManager = new SqlDMLManager();
        ArrayList<String> res = new ArrayList();
        
        try {
             res = dmlManager.getUpsectionsId(conn, SqlDMLManager.SelectQuery.UPSECTION_ID.toString());
        } catch (SQLException ex) {
            Logger.getLogger(SeatInServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
}
