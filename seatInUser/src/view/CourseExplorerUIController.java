package view;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class CourseExplorerUIController implements Initializable {
    
    private static String _COURSE_ID;
    private static String _USER_LOGGED_NUMBER;
    private static String _USER_CATEGORY;
    private static String _SECTION_ID;
    
    @FXML
    private TextField sectionIdTextField;
    
    @FXML
    private Label insertSectionIdLabel;
    
    @FXML
    private Button okButton;
    
    @FXML
    private Label sectionsLabel;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label professorsLabel;
    
    @FXML
    private Label descriptionLabel;
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private Label sectionWarningMessagesLabel;
    
    @FXML
    private Button signInButton;
    
    @FXML
    private Button manageCourseButton;
    
    @FXML
    private Label courseResourcesLabel;
    
    @FXML
    private AnchorPane courseAnchorPane;
    
    @FXML
    private void handleManageCourseButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("CourseManagerUI.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInUser");
        stage.setResizable(false);
        stage.show();
            
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    private void handleSignInButtonAction(ActionEvent event) throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        server.signUserToCourse(_USER_LOGGED_NUMBER, _COURSE_ID);
        
        //reopening same window, user is now signed to this course
        Parent root = null; 
        try {
            root = FXMLLoader.load(getClass().getResource("CourseExplorerUI.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(CourseExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInUser");
        stage.setResizable(false);
        stage.show();
            
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProxyServer server = null;
        
        try {
            server = new ProxyServer();
            server.increaseUsersVisualizingCoursesNumber();
        } catch (RemoteException ex) {
            Logger.getLogger(PlatformStatisticsDisplayerUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(PlatformStatisticsDisplayerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        _COURSE_ID = CourseSelectorUIController.getCourseId();
        _USER_LOGGED_NUMBER = CourseSelectorUIController.getLoggedUserNumber();
        _USER_CATEGORY = CourseSelectorUIController.getUserCategory();
        try {
            initCourseFields();
        } catch (RemoteException ex) {
            Logger.getLogger(CourseExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(CourseExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            server = new ProxyServer();
        } catch (RemoteException ex) {
            Logger.getLogger(CourseExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(CourseExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
             if(!server.isUserSigned(_USER_LOGGED_NUMBER, _COURSE_ID, _USER_CATEGORY)){
                    messagesLabel.setText("You are not signed to this course. Would you like to sign in?");
                    signInButton.setVisible(true);
            }
             else{
                 if(_USER_CATEGORY.equals("Professor")){
                     manageCourseButton.setVisible(true);
                 }
                 insertSectionIdLabel.setVisible(true);
                 courseResourcesLabel.setVisible(true);
                 sectionIdTextField.setVisible(true);
                 okButton.setVisible(true);
                 sectionWarningMessagesLabel.setVisible(true);
                 ArrayList<String> sectionsId = server.getSectionsId(_COURSE_ID);
                 
                 ArrayList<String> sectionsNames = null;
                 ObservableList<String> items = null;
                 for(String x: sectionsId){
                     sectionsNames = server.getSectionName(x);
                     for(String y: sectionsNames){
                         sectionsLabel.setText(sectionsLabel.getText() + "-SECTION ID: " + x + "\t" + "NAME: " + y + "\n" + "\n");
                     }
                 }
                 
           
             }
            } catch (RemoteException ex) {
                Logger.getLogger(CourseExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }    
    
    //----------------------------------------------------------------------------------------------------------------
    
    private void initCourseFields() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        String[] courseData;
        courseData = server.getCourseData(_COURSE_ID);
        nameLabel.setText(courseData[0]);
        descriptionLabel.setText(courseData[1]);
        ArrayList<String> professorsId = server.getProfessorsId(_COURSE_ID);
        String[] professorData;
        
        professorsLabel.setText("");
        for(String x: professorsId){
            professorData = server.getUserData(x);
            professorsLabel.setText(professorsLabel.getText() + "" + professorData[0] + " " + professorData[1] + "\n");
        }    
    }
    
    public static String getCourseID(){
        return _COURSE_ID;
    }
    
    public static String getSectionID(){
        return _SECTION_ID;
    }
    
    @FXML
    private void handleOkButtonAction(ActionEvent event) throws IOException{
        sectionWarningMessagesLabel.setText("");
        if(sectionIdTextField.getText() == null || sectionIdTextField.getText().trim().isEmpty()){
            sectionWarningMessagesLabel.setText("Insert section ID");
            return;
        }
        else{
            _SECTION_ID = sectionIdTextField.getText();
            Parent root = FXMLLoader.load(getClass().getResource("SectionExplorerUI.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("seatInUser");
            stage.setResizable(false);
            stage.show();
        }
    }
}
