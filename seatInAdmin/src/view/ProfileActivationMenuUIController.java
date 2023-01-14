package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class ProfileActivationMenuUIController implements Initializable {
    
    @FXML
    private PasswordField newPasswordField;
    
    @FXML
    private TextField activationCodeTextField;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Label warningMessagesLabel;
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws RemoteException, NotBoundException, IOException{
        
        String activationCode = activationCodeTextField.getText();
        String password = newPasswordField.getText();
        
        ProxyServer server = new ProxyServer();
        
        if(server.isActivationCodeValid(activationCode, FXMLoginUIController.getEmail(), "Administrator")){
            server.resetPassword(FXMLoginUIController.getEmail(), password, "Administrator");
            server.activateProfile(FXMLoginUIController.getEmail(), "Administrator");
            Parent root = FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
            //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\MainMenuUI.fxml").toURL();           
            //Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("seatInAdmin");
            stage.setResizable(false);
            stage.show();
            
        }
        else{
            warningMessagesLabel.setText("Invalid authentication code");
        }
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
