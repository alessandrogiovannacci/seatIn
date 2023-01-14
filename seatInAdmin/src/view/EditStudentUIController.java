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
import model.DegreeCourse;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class EditStudentUIController implements Initializable {
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private Button doneButton;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label surnameLabel;
    
    @FXML
    private Label degreeCourseLabel;
    
    @FXML
    private Label careerStateLabel;
    
    @FXML
    private Label emailLabel;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField surnameTextField;
    
    @FXML
    private ComboBox degreeCourseComboBox;
    
    @FXML
    private ComboBox careerStateComboBox;
    
    @FXML
    private TextField emailTextField;
    
    @FXML
    private void handleDoneButtonAction(ActionEvent event) throws IOException, RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        boolean fieldsUpdated = false;
        
        if(nameTextField.getText() != null && !nameTextField.getText().trim().isEmpty()){
            server.updateName(EditChoiceUIController.getNumber(), nameTextField.getText(), "Student");
            fieldsUpdated = true;
        }
        
        if(surnameTextField.getText() != null && !surnameTextField.getText().trim().isEmpty()){
            server.updateSurname(EditChoiceUIController.getNumber(), surnameTextField.getText(), "Student");
            fieldsUpdated = true;
        }
        
        if(!degreeCourseComboBox.getSelectionModel().isEmpty()){
            server.updateDegreeCourse(EditChoiceUIController.getNumber(), String.valueOf(degreeCourseComboBox.getValue()));
            fieldsUpdated = true;
        }
        
        if(!careerStateComboBox.getSelectionModel().isEmpty()){
            server.updateCareerState(EditChoiceUIController.getNumber(), String.valueOf(careerStateComboBox.getValue()));
            fieldsUpdated = true;
        }
        
        if(emailTextField.getText() != null && !emailTextField.getText().trim().isEmpty()){
            server.updateEmail(EditChoiceUIController.getNumber(), emailTextField.getText(), "Student");
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
            initStudentLabels();
        } catch (RemoteException ex) {
            Logger.getLogger(EditStudentUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(EditStudentUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initDegreeCourseComboBox();
        initCareerStateComboBox();
    }

    private void initStudentLabels() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        String[] studentData = server.getStudentData(EditChoiceUIController.getNumber());
        nameLabel.setText(studentData[0]);
        surnameLabel.setText(studentData[1]);
        degreeCourseLabel.setText(studentData[2]);
        careerStateLabel.setText(studentData[3]);
        emailLabel.setText(studentData[4]);
    }
    
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
        degreeCourseComboBox.getItems().clear();
        degreeCourseComboBox.setItems(obList);
    }
    
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
