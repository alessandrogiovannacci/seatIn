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
import model.DegreeCourse;
import model.ProxyServer;
import model.Student;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class StudentRegisterUIController implements Initializable {
    
    @FXML
    private TextField numberTextField;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField surnameTextField;
    
    @FXML
    private TextField emailTextField;
    
    @FXML
    private TextField registrationYearTextField;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private ComboBox dcComboBox;
    
    @FXML
    private ComboBox careerStateComboBox;
    
    @FXML
    private Label messagesLabel;
    
    //-----------------------------------------------------------------------------------------
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws IOException, RemoteException, NotBoundException{
        messagesLabel.setText("");
        if ( (numberTextField.getText() == null || numberTextField.getText().trim().isEmpty()) ||
             (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty()) ||
             (surnameTextField.getText() == null || surnameTextField.getText().trim().isEmpty()) ||
             (emailTextField.getText() == null || emailTextField.getText().trim().isEmpty()) ||
             (registrationYearTextField.getText() == null || registrationYearTextField.getText().trim().isEmpty()) ||
             (dcComboBox.getSelectionModel().isEmpty()) || (careerStateComboBox.getSelectionModel().isEmpty())) {
                messagesLabel.setText("All fields are required");
                return;
        }
        
        else{
            Student student = new Student(numberTextField.getText(), nameTextField.getText(), surnameTextField.getText(),
                                        String.valueOf(dcComboBox.getValue()), String.valueOf(careerStateComboBox.getValue()), 
                                        emailTextField.getText(), Integer.valueOf(registrationYearTextField.getText()));
            
            ProxyServer server = new ProxyServer();
            server.insertStudent(student);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    //-----------------------------------------------------------------------------------------
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDegreeCourseComboBox();
        initCareerStateComboBox();
    }    
    
    //-----------------------------------------------------------------------------------------
    
    private void initDegreeCourseComboBox(){
        List<String> list = new ArrayList<>();
        list.add(DegreeCourse.COMPUTERSCIENCE.toString());
        list.add(DegreeCourse.MATHEMATICS.toString());
        list.add(DegreeCourse.PHYSICS.toString());
        list.add(DegreeCourse.ASTROPHYSICS.toString());
        list.add(DegreeCourse.LAW.toString());
        list.add(DegreeCourse.MEDICINE.toString());
        list.add(DegreeCourse.CHEMISTRY.toString());
        ObservableList obList = FXCollections.observableList(list);
        dcComboBox.getItems().clear();
        dcComboBox.setItems(obList);
    }
    
    //-----------------------------------------------------------------------------------------
    
    private void initCareerStateComboBox(){
        List<String> list = new ArrayList<>();
        list.add("I");
        list.add("II");
        list.add("III");
        ObservableList obList = FXCollections.observableList(list);
        careerStateComboBox.getItems().clear();
        careerStateComboBox.setItems(obList);
    }
    
}
