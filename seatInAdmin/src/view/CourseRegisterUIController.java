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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Course;
import model.CourseName;
import model.DegreeCourse;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class CourseRegisterUIController implements Initializable {
    
    @FXML
    private TextField idTextField;
    
    @FXML
    private ComboBox nameComboBox;
    
    @FXML
    private TextField activationYearTextField;
    
    @FXML
    private ComboBox dcComboBox;
    
    @FXML
    private TextArea descriptionTextArea;
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws RemoteException, NotBoundException{
        messagesLabel.setText("");
        if ( (idTextField.getText() == null || idTextField.getText().trim().isEmpty()) ||
             (nameComboBox.getSelectionModel().isEmpty()) ||
             (activationYearTextField.getText() == null || activationYearTextField.getText().trim().isEmpty()) ||
             (descriptionTextArea.getText() == null || descriptionTextArea.getText().trim().isEmpty())  ||
             (dcComboBox.getSelectionModel().isEmpty())) {
                messagesLabel.setText("All fields are required");
                return;
        }
        
        else{
            Course course = new Course(idTextField.getText(), String.valueOf(nameComboBox.getValue()), Integer.valueOf(activationYearTextField.getText()), 
                                        String.valueOf(dcComboBox.getValue()), descriptionTextArea.getText());
            
            ProxyServer server = new ProxyServer();
            server.insertCourse(course);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDegreeCourseComboBox();
        initCourseNameComboBox();
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
        dcComboBox.getItems().clear();
        dcComboBox.setItems(obList.sorted());
    }
    
    private void initCourseNameComboBox(){
        List<String> list = new ArrayList<>();
        list.add(CourseName.PROGRAMMING.toString());
        list.add(CourseName.ANALYSIS_I.toString());
        list.add(CourseName.ANALYSIS_II.toString());
        list.add(CourseName.OPERATING_SYSTEMS.toString());
        list.add(CourseName.DATABASES.toString());
        list.add(CourseName.COMPUTER_NETWORKS.toString());
        list.add(CourseName.ALGEBRA_AND_GEOMETRY.toString());
        list.add(CourseName.PHYSICS_I.toString());
        list.add(CourseName.PHYSICS_II.toString());
        list.add(CourseName.NATURAL_SCIENCES.toString());
        list.add(CourseName.ANALYSIS.toString());
        list.add(CourseName.DATA_ANALYSIS.toString());
        list.add(CourseName.COSMOLOGY.toString());
        list.add(CourseName.PARTICLE_PHYSICS.toString());
        list.add(CourseName.CIVIL_LAW.toString());
        list.add(CourseName.CRIMINAL_LAW.toString());
        list.add(CourseName.ADMINISTRATIVE_LAW.toString());
        list.add(CourseName.HISTORY.toString());
        list.add(CourseName.INTERNATIONAL_LAW.toString());
        list.add(CourseName.HUMAN_ANATOMY.toString());
        list.add(CourseName.MEDICINE_HISTORY.toString());
        list.add(CourseName.LEGAL_MEDICINE.toString());
        list.add(CourseName.MICROBIOLOGY.toString());
        list.add(CourseName.PSICOLOGY.toString());
        list.add(CourseName.BIOCHEMISTRY.toString());
        list.add(CourseName.ANALYTICAL_CHEMISTRY.toString());
        ObservableList obList = FXCollections.observableList(list);
        nameComboBox.getItems().clear();
        nameComboBox.setItems(obList.sorted());
    }
    
}
