package view;

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
public class ProfessorMainMenuUIController implements Initializable {
    
    private static String _LOGGED_PROFESSOR_NUMBER;
    
    @FXML
    public void handleExploreMyCoursesButton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("CourseSelectorUI.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInUser");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    public void handleAnalyzePlatformStatisticsButton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("PlatformStatisticsDisplayerUI.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInUser");
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _LOGGED_PROFESSOR_NUMBER = FXMLoginUIController.getLoggedUserNumber();
        
        ProxyServer server = null;
        try {
            server = new ProxyServer();
            server.increaseLoggedUsersNumber();
        } catch (RemoteException ex) {
            Logger.getLogger(StudentMainMenuUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(StudentMainMenuUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
