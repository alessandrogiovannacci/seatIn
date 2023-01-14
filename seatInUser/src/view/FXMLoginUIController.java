package view;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
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
    
    @FXML
    private ComboBox categoryComboBox;
    
    private static String _EMAIL;
    
    private static String _LOGGED_USER_NUMBER;
    
    private static String _USER_CATEGORY;
    //--------------------------------------------------------------------------------------------
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws IOException, RemoteException, NotBoundException, Exception{
        
        warningMessagesLabel.setText("");
        if ( (emailTextField.getText() == null || emailTextField.getText().trim().isEmpty()) ||
             (passwordField.getText() == null || passwordField.getText().trim().isEmpty()) ||
             (categoryComboBox.getSelectionModel().isEmpty()) || (categoryComboBox.getSelectionModel().isEmpty())) {
                warningMessagesLabel.setText("Email and/or password and/or category required");
                return;
        }
        
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String category = String.valueOf(categoryComboBox.getValue());
        
        
        ProxyServer server = new ProxyServer();
 
        if(!server.login(email, password, category)){
            warningMessagesLabel.setText("Authentication failed");
            _USER_CATEGORY = String.valueOf(categoryComboBox.getValue());
            
            long authAttempts = server.getAuthAttempts(email, category);
            if(authAttempts < 9){
                server.increaseAuthAttempts(authAttempts+1, email, category);
            }
            else{
                server.deactivateProfile(email, category);
                warningMessagesLabel.setText(warningMessagesLabel.getText() + ". Login attempts exceeded. Profile has been blocked.");
            }
            
        }
        else{
            _EMAIL = email;
            _LOGGED_USER_NUMBER = server.getNumber(email, category);
            _USER_CATEGORY = String.valueOf(categoryComboBox.getValue());
            if(server.isProfileActivated(email, category)){
                Parent root;
                if(category.equals("Student")){
                   root  = FXMLLoader.load(getClass().getResource("StudentMainMenuUI.fxml"));
                }
                else{
                   root  = FXMLLoader.load(getClass().getResource("ProfessorMainMenuUI.fxml")); 
                }
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("seatInUser");
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
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("seatInUser");
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
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInUser");
        stage.setResizable(false);
        stage.show();
    }
    
    //*************************************************************************************************
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCategoryComboBox();
    }    
    
    //*************************************************************************************************
     
    public static String getEmail(){
        return _EMAIL;
    }
    
    //*************************************************************************************************
    
    public static String getLoggedUserNumber(){
        return _LOGGED_USER_NUMBER;
    }
    
    //*************************************************************************************************
    
    private void initCategoryComboBox(){
        List<String> list = new ArrayList<>();
        list.add("Professor");
        list.add("Student");
        ObservableList obList = FXCollections.observableList(list);
        categoryComboBox.getItems().clear();
        categoryComboBox.setItems(obList);
    }
    
    //*************************************************************************************************
    
    public static String getUserCategory(){
        return _USER_CATEGORY;
    }
    
}
