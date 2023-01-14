package view;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class CourseSelectorUIController implements Initializable {
    
    private static String _LOGGED_USER_NUMBER;
    private static String _COURSE_ID;
    private static String _USER_CATEGORY;
    
    @FXML
    private Button goButton;
    
    @FXML
    private ComboBox courseIdComboBox;
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private void handleGoButtonAction(ActionEvent event) throws IOException, RemoteException, NotBoundException{
        messagesLabel.setText("");
        if((courseIdComboBox.getSelectionModel().isEmpty()) || (courseIdComboBox.getSelectionModel().isEmpty())){
            messagesLabel.setText("No course selected");
            return;
        }
        else{
            _COURSE_ID = String.valueOf(courseIdComboBox.getValue());
            Parent root = FXMLLoader.load(getClass().getResource("CourseExplorerUI.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("seatInUser");
            stage.setResizable(false);
            stage.show();
            
            ProxyServer server = new ProxyServer();
            stage.setOnCloseRequest((WindowEvent event1) -> {
                    try {
                        server.decreaseUsersVisualizingCoursesNumber();
                    } catch (RemoteException ex) {
                        Logger.getLogger(FXMLoginUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            });
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _USER_CATEGORY = FXMLoginUIController.getUserCategory();
        if(_USER_CATEGORY.equals("Student")){
            titleLabel.setText("Select a course of your study plan");
        }
        else{
            titleLabel.setText("Select a course that you manage");
        }
        _LOGGED_USER_NUMBER = FXMLoginUIController.getLoggedUserNumber();
        try {
            initCourseIdComboBox();
        } catch (RemoteException ex) {
            Logger.getLogger(CourseSelectorUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(CourseSelectorUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initCourseIdComboBox() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        ArrayList<String> coursesId;
        if(_USER_CATEGORY.equals("Student")){
            coursesId = server.getStudentStudyPlanCoursesId(_LOGGED_USER_NUMBER);
            ObservableList obList = FXCollections.observableList(coursesId);
            courseIdComboBox.getItems().clear();
            courseIdComboBox.setItems(obList);
        }
        else{
            coursesId = server.getProfessorHandledCoursesId(_LOGGED_USER_NUMBER);
            ObservableList obList = FXCollections.observableList(coursesId);
            courseIdComboBox.getItems().clear();
            courseIdComboBox.setItems(obList);
        }
        
        
    }
    
    public static String getCourseId(){
        return _COURSE_ID;
    }
    
    public static String getLoggedUserNumber(){
        return _LOGGED_USER_NUMBER;
    }
    
    public static String getUserCategory(){
        return _USER_CATEGORY;
    }
    
}
