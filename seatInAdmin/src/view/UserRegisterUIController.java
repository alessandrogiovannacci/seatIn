/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Department;
import model.ProxyServer;
import model.User;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class UserRegisterUIController implements Initializable {
    
    @FXML
    private TextField numberTextField;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField surnameTextField;
    
    @FXML
    private ComboBox roleComboBox;
    
    @FXML
    private ComboBox depComboBox;
    
    @FXML
    private TextField emailTextField;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Label messagesLabel;
    
    //******************************************************************************************
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws IOException, RemoteException, NotBoundException{
        messagesLabel.setText("");
        if ( (numberTextField.getText() == null || numberTextField.getText().trim().isEmpty()) ||
             (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty()) ||
             (surnameTextField.getText() == null || surnameTextField.getText().trim().isEmpty()) ||
             (roleComboBox.getSelectionModel().isEmpty()) || (depComboBox.getSelectionModel().isEmpty()) ||
             (emailTextField.getText() == null || emailTextField.getText().trim().isEmpty())) {
                messagesLabel.setText("All fields are required");
                return;
        }
        
        else{
            User user = new User(numberTextField.getText(), nameTextField.getText(), surnameTextField.getText(),
                                String.valueOf(roleComboBox.getValue()), String.valueOf(depComboBox.getValue()), emailTextField.getText());
            ProxyServer server = new ProxyServer();
            server.insertUser(user);
            ((Node)(event.getSource())).getScene().getWindow().hide();        
        }
        
    }
    
    //******************************************************************************************
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initRoleComboBox();
        initDepComboBox();
    }
    
    //******************************************************************************************
    
    private void initRoleComboBox(){
        List<String> list = new ArrayList<>();
        list.add("Administrator");
        list.add("Professor");
        ObservableList obList = FXCollections.observableList(list);
        roleComboBox.getItems().clear();
        roleComboBox.setItems(obList);
    }
    
    //******************************************************************************************
    
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
