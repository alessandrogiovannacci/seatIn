package view;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class PlatformStatisticsDisplayerUIController implements Initializable {

    @FXML
    private Label usersNumberLabel;
    
    @FXML
    private Label usersVisualizingCoursesContentsNumberLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProxyServer server = null;
        try {
            server = new ProxyServer();
            usersNumberLabel.setText("" + server.getLoggedUsersNumber());
            usersVisualizingCoursesContentsNumberLabel.setText("" + server.getUsersVisualizingCoursesNumber());
        } catch (RemoteException ex) {
            Logger.getLogger(PlatformStatisticsDisplayerUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(PlatformStatisticsDisplayerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
}
