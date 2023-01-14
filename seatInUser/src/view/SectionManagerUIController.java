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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ProxyServer;
import model.Section;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class SectionManagerUIController implements Initializable {
    
    private static String _COURSE_ID;
    private static String _SECTION_ID;
    
    @FXML
    private TextField idTextField;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextArea descriptionTextArea;
    
    @FXML
    private ComboBox visibilityComboBox;
    
    @FXML
    private CheckBox subsectionCheckBox;
    
    @FXML
    private ComboBox upSectionComboBox;
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private Button okButton;
    
    @FXML
    private Label upsectionLabel;
    
    @FXML
    private void handleAddResourceButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ResourceManagerUI.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("seatInUser");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void handleOkButtonAction(ActionEvent event) throws RemoteException, NotBoundException{
        messagesLabel.setText("");
        ProxyServer server = new ProxyServer();
        if((idTextField.getText() == null || idTextField.getText().trim().isEmpty()) ||
           (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty()) ){
                messagesLabel.setText("Fields with * required");
                return;
        }
        else{
            _SECTION_ID = idTextField.getText();
            String description  = "";
            String upsection = "";
            if(descriptionTextArea.getText().trim().isEmpty()){
                description = null;
            }
            else{
                description = descriptionTextArea.getText();
            }
            
            if(upSectionComboBox.getSelectionModel().isEmpty()){
                upsection = null;
            }
            else{
                upsection = String.valueOf(upSectionComboBox.getValue());
                
                if(visibilityComboBox.getValue().toString().equals("private")){
                    //TODO
                    String upsectionVisibility = server.getVisibility(upsection);
                    
                    if(upsectionVisibility.equals("public")){
                        messagesLabel.setText("Cannot insert private section into a public one");
                        return;
                    }
                }
            }
            
            Section s = new Section(idTextField.getText(), nameTextField.getText(), description, String.valueOf(visibilityComboBox.getValue()), upsection);
            server.addSection(s);
            server.addSectionToCourse(_COURSE_ID, idTextField.getText());
            messagesLabel.setText("Section has been successfully added!");
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _COURSE_ID = CourseExplorerUIController.getCourseID();
        initVisibilityComboBox();
        visibilityComboBox.getSelectionModel().selectFirst();
        
        subsectionCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    initUpsectionComboBox();
                } catch (RemoteException ex) {
                    Logger.getLogger(SectionManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(SectionManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
    
    private void initVisibilityComboBox(){
        List<String> list = new ArrayList<>();
        list.add("public");
        list.add("private");
        ObservableList obList = FXCollections.observableList(list);
        visibilityComboBox.getItems().clear();
        visibilityComboBox.setItems(obList);
    }
    
    public static String getSectionId(){
        return _SECTION_ID;
    }
    
    private void initUpsectionComboBox() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        ArrayList<String> upsections;
        upsections = server.getUpsections();
        ObservableList obList = FXCollections.observableList(upsections);
        upSectionComboBox.getItems().clear();
        upSectionComboBox.setItems(obList);
    }
    
    
}
