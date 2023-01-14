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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ProxyServer;



/**
 * FXML Controller class
 *
 * @author Ale
 */
public class FXMLoginUIController implements Initializable {
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button forgottenPasswordButton;
    
    @FXML
    private TextField emailTextField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label warningMessagesLabel;
    
    private static String _EMAIL;
    
    //--------------------------------------------------------------------------------------------
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws IOException, RemoteException, NotBoundException, Exception{
        
        warningMessagesLabel.setText("");
        if ( (emailTextField.getText() == null || emailTextField.getText().trim().isEmpty()) ||
             (passwordField.getText() == null || passwordField.getText().trim().isEmpty())) {
                warningMessagesLabel.setText("Email and/or password required");
                return;
        }
        
        String email = emailTextField.getText();
        String password = passwordField.getText();
        
        
        ProxyServer server = new ProxyServer();
 
        if(!server.login(email, password, "Administrator")){
            warningMessagesLabel.setText("Authentication failed");
            long authAttempts = server.getAuthAttempts(email, "Administrator");
            if(authAttempts < 9){
                server.increaseAuthAttempts(authAttempts+1, email, "Administrator");
            }
            else{
                server.deactivateProfile(email, "Administrator");
                warningMessagesLabel.setText(warningMessagesLabel.getText() + ". Login attempts exceeded. Profile has been blocked.");
            }
        }
        else{
            _EMAIL = email;
            if(server.isProfileActivated(email, "Administrator")){
                Parent root = FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
                //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\MainMenuUI.fxml").toURL();           
                //Parent root = FXMLLoader.load(url);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("seatInAdmin");
                stage.setResizable(false);
                stage.show();
                
                stage.setOnCloseRequest((WindowEvent event1) -> {
                    try {
                        server.decreaseLoggedUsersNumber();
                    } catch (RemoteException ex) {
                        Logger.getLogger(FXMLoginUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            else{
                Parent root = FXMLLoader.load(getClass().getResource("ProfileActivationMenuUI.fxml"));
                //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\ProfileActivationMenuUI.fxml").toURL();           
                //Parent root = FXMLLoader.load(url);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("seatInAdmin");
                stage.setResizable(false);
                stage.show();
            }
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
    }
    
    //*************************************************************************************************
    
    @FXML
    private void handleForgottenPasswordButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("PasswordRecoveryUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\PasswordRecoveryUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }
    
    //*************************************************************************************************
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //*************************************************************************************************
     
    public static String getEmail(){
        return _EMAIL;
    }
    
    
}
