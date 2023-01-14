package view;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
    private ComboBox categoryComboBox;
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws RemoteException, NotBoundException, IOException{
        
        String activationCode = activationCodeTextField.getText();
        String password = newPasswordField.getText();
        String category = String.valueOf(categoryComboBox.getValue());
        
        ProxyServer server = new ProxyServer();
        
        if(server.isActivationCodeValid(activationCode, FXMLoginUIController.getEmail(), category)){
            server.resetPassword(FXMLoginUIController.getEmail(), password, category);
            server.activateProfile(FXMLoginUIController.getEmail(), category);
            Parent root;
            if(category.equals("Student")){
                root = FXMLLoader.load(getClass().getResource("StudentMainMenuUI.fxml"));
            }
            else{
                root = FXMLLoader.load(getClass().getResource("ProfessorMainMenuUI.fxml"));
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("seatInUser");
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
        initCategoryComboBox();
    }   
    
    private void initCategoryComboBox(){
        List<String> list = new ArrayList<>();
        list.add("Professor");
        list.add("Student");
        ObservableList obList = FXCollections.observableList(list);
        categoryComboBox.getItems().clear();
        categoryComboBox.setItems(obList);
    }
    
}
