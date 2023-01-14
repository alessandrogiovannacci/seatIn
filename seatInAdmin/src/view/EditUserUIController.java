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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Department;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class EditUserUIController implements Initializable {
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private Button doneButton;
    
    @FXML
    private ComboBox depComboBox;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label surnameLabel;
    
    @FXML
    private Label departmentLabel;
    
    @FXML
    private Label emailLabel;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField surnameTextField;
    
    @FXML
    private TextField emailTextField;
    
    @FXML
    private void handleDoneButtonAction(ActionEvent event) throws IOException, RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        boolean fieldsUpdated = false;
        
        if(nameTextField.getText() != null && !nameTextField.getText().trim().isEmpty()){
            server.updateName(EditChoiceUIController.getNumber(), nameTextField.getText(), "User");
            fieldsUpdated = true;
        }
        
        if(surnameTextField.getText() != null && !surnameTextField.getText().trim().isEmpty()){
            server.updateSurname(EditChoiceUIController.getNumber(), surnameTextField.getText(), "User");
            fieldsUpdated = true;
        }
        
        if(!depComboBox.getSelectionModel().isEmpty()){
            server.updateDepartment(EditChoiceUIController.getNumber(), String.valueOf(depComboBox.getValue()));
            fieldsUpdated = true;
        }
        
        if(emailTextField.getText() != null && !emailTextField.getText().trim().isEmpty()){
            server.updateEmail(EditChoiceUIController.getNumber(), emailTextField.getText(), "User");
            fieldsUpdated = true;
        }
        
        if(fieldsUpdated){
            messagesLabel.setText("Operation successfully completed");
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initUserLabels();
        } catch (RemoteException ex) {
            Logger.getLogger(EditUserUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(EditUserUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initDepComboBox();
        
    }

    private void initUserLabels() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        String[] userData = server.getUserData(EditChoiceUIController.getNumber());
        nameLabel.setText(userData[0]);
        surnameLabel.setText(userData[1]);
        departmentLabel.setText(userData[2]);
        emailLabel.setText(userData[3]);
    }
    
    private void initDepComboBox(){
        List<String> list = new ArrayList<>();
        list.add(Department.THEORICAL_SCIENCES.toString());
        list.add(Department.ENGINEERING.toString());
        list.add(Department.CHEMICAL_SCIENCES.toString());
        list.add(Department.MEDICAL_SCIENCES.toString());
        list.add(Department.ECONOMY.toString());
        ObservableList obList = FXCollections.observableList(list);
        depComboBox.getItems().clear();
        depComboBox.setItems(obList);
    }
    
}
