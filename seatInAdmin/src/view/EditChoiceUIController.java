package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class EditChoiceUIController implements Initializable {
    
    private static String number;
    
    @FXML
    private ComboBox categoryComboBox;
    
    @FXML
    private TextField numberTextField;
    
    @FXML
    private Button doneButton;
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private void handleDoneButtonAction(ActionEvent event) throws IOException{
        messagesLabel.setText("");
        
        if(categoryComboBox.getSelectionModel().isEmpty() || (numberTextField.getText() == null || numberTextField.getText().trim().isEmpty())){
            messagesLabel.setText("Insert both category and number");
            return;
        }
        
        number = numberTextField.getText();
        
        if(categoryComboBox.getSelectionModel().getSelectedItem().toString().equals("Student")){
            Parent root = FXMLLoader.load(getClass().getResource("EditStudentUI.fxml"));
            //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\EditStudentUI.fxml").toURL();           
            //Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("seatInAdmin");
            stage.setResizable(false);
            stage.show();
        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("EditUserUI.fxml"));
            //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\EditUserUI.fxml").toURL();           
            //Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("seatInAdmin");
            stage.setResizable(false);
            stage.show();
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
        list.add("Administrator");
        list.add("Professor");
        list.add("Student");
        ObservableList obList = FXCollections.observableList(list);
        categoryComboBox.getItems().clear();
        categoryComboBox.setItems(obList);
    }
    
    public static String getNumber(){
        return number;
    }
}
