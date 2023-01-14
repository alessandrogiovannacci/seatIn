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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class CoursesAllocatorUIController implements Initializable {
    
    //fields
    @FXML
    private Label messagesLabel;
    
    @FXML
    private ComboBox categoryComboBox;
    
    @FXML
    private ComboBox idComboBox;
    
    @FXML
    private ComboBox courseIdComboBox;
    
    @FXML
    private Button submitButton;
    
    ObservableList<String> list = FXCollections.observableArrayList("");
    
    //methods
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCategoryComboBox();
        
        
        categoryComboBox.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                updateComboBox(newValue);
            }
        });

    }
    
    //----------------------------------------------------------------------------------
    
    private void updateComboBox(String newValue){
        switch(newValue){
            case ("Student"):{
                try {
                    initIdComboBox();
                } catch (RemoteException ex) {
                    Logger.getLogger(CoursesAllocatorUIController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(CoursesAllocatorUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            
            case ("Administrator"):{    
                try {
                    initIdComboBox();
                } catch (RemoteException ex) {
                    Logger.getLogger(CoursesAllocatorUIController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(CoursesAllocatorUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break; 
            
            case ("Professor"):{
                try {
                    initIdComboBox();
                } catch (RemoteException ex) {
                    Logger.getLogger(CoursesAllocatorUIController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(CoursesAllocatorUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
                
        }
        
        try {
            initCoursesIdComboBox();
        } catch (RemoteException ex) {
            Logger.getLogger(CoursesAllocatorUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(CoursesAllocatorUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //----------------------------------------------------------------------------------
    
    private void initCategoryComboBox(){
        List<String> list = new ArrayList<>();
        list.add("Administrator");
        list.add("Professor");
        list.add("Student");
        ObservableList obList = FXCollections.observableList(list);
        categoryComboBox.getItems().clear();
        categoryComboBox.setItems(obList);
    }
    
    //----------------------------------------------------------------------------------
    
    private void initIdComboBox() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        ArrayList<String> numbers;
        numbers = server.getNumbers(categoryComboBox.getValue().toString());
        ObservableList obList = FXCollections.observableList(numbers);
        idComboBox.getItems().clear();
        idComboBox.setItems(obList);
    }
    
    //----------------------------------------------------------------------------------
    
    private void initCoursesIdComboBox() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        ArrayList<String> coursesId;
        coursesId = server.getCoursesId();
        ObservableList obList = FXCollections.observableList(coursesId);
        courseIdComboBox.getItems().clear();
        courseIdComboBox.setItems(obList);
        
    }
    
    //----------------------------------------------------------------------------------
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws IOException, RemoteException, NotBoundException{
        messagesLabel.setText("");
        if ( 
             (courseIdComboBox.getSelectionModel().isEmpty()) || (idComboBox.getSelectionModel().isEmpty()) ||
              categoryComboBox.getSelectionModel().isEmpty()) {
                messagesLabel.setText("All fields are required");
                return;
        }
        
        else{
            ProxyServer server = new ProxyServer();
            if(categoryComboBox.getValue().toString().equals("Student")){
                if(!server.isStudyPlanAlreadyRegistered(String.valueOf(idComboBox.getValue()))){
                    server.registerStudyPlan(String.valueOf(idComboBox.getValue()));
                }
                
                server.assignCourseToStudent(String.valueOf(idComboBox.getValue()), String.valueOf(courseIdComboBox.getValue()));
            }
                
            else{
                server.assignCourseToUser(String.valueOf(idComboBox.getValue()), String.valueOf(courseIdComboBox.getValue()));
            }
            
            messagesLabel.setText("Course successfully assigned!");
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    
}
