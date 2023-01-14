package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class MainMenuUIController implements Initializable {
    
    @FXML
    private void handleAssignCoursesButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("CoursesAllocatorUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\CoursesAllocatorUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void handleAnalyzePlatformStatisticsButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("PlatformStatisticsDisplayerUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\PlatformStatisticsDisplayerUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void handleAddCourseButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("CourseRegisterUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\CourseRegisterUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void handleEditButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("EditChoiceUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\EditChoiceUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void handleInsertStudentButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("StudentRegisterUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\StudentRegisterUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void handleInsertUserButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("UserRegisterUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\UserRegisterUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void handleSendEmailButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("EmailSenderUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\EmailSenderUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void handleExploreManagedCoursesButton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("CoursesExplorerUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\CoursesExplorerUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProxyServer server = null;
        try {
            server = new ProxyServer();
            server.increaseLoggedUsersNumber();
        } catch (RemoteException ex) {
            Logger.getLogger(PlatformStatisticsDisplayerUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(PlatformStatisticsDisplayerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    
}
