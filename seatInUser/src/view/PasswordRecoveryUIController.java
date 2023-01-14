package view;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class PasswordRecoveryUIController implements Initializable {
    
    @FXML
    private Button submitButton;
    
    @FXML
    private TextField emailTextField;
    
    @FXML
    private Label messageLabel;
    
    @FXML
    private ComboBox categoryComboBox;
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws NotBoundException, RemoteException{
        String email = emailTextField.getText();
        String category = String.valueOf(categoryComboBox.getValue());
        messageLabel.setText("");
        
        ProxyServer server = new ProxyServer();
        
        if(!server.isEmailValid(email, category)){
            messageLabel.setText("Invalid email");
        }
        else{
            server.receiveNewPasswordAndActivationCode(email, category);
            messageLabel.setText("An email was sent to your email address. Use new password and activation code"
                    + " in order to login");
        }
        
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
