package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.ProxyServer;
import model.Resource;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class ResourceManagerUIController implements Initializable {
    
    private static String _SECTION_ID;
    
    @FXML
    private TextField idTextField;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextArea descriptionTextArea;
    
    @FXML
    private ComboBox typeComboBox;
    
    @FXML
    private ComboBox visibilityComboBox;
    
    @FXML
    private ComboBox folderComboBox;
    
    @FXML
    private Label messagesLabel;
    
    //------------------------------------------------------------------------------------------------------------
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _SECTION_ID = SectionManagerUIController.getSectionId();
        initVisibilityComboBox();
        visibilityComboBox.getSelectionModel().selectFirst();
        initTypeComboBox();
        
        typeComboBox.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                updateComboBox(newValue);
            }
        });
    }
    
    //------------------------------------------------------------------------------------------------------------
    
    private void updateComboBox(String newValue){
        switch(newValue){
            case("file"):{
            try {
                initFolderComboBox();
            } catch (RemoteException ex) {
                Logger.getLogger(ResourceManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(ResourceManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            break;
        
        }
    }
    
    //------------------------------------------------------------------------------------------------------------
    
    private void initFolderComboBox() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        ArrayList<String> foldersNames;
        foldersNames = server.getFoldersName(_SECTION_ID);
        ObservableList obList = FXCollections.observableList(foldersNames);
        folderComboBox.getItems().clear();
        folderComboBox.setItems(obList);
    }
    
    //------------------------------------------------------------------------------------------------------------
    
    private void initVisibilityComboBox(){
        List<String> list = new ArrayList<>();
        list.add("public");
        list.add("private");
        ObservableList obList = FXCollections.observableList(list);
        visibilityComboBox.getItems().clear();
        visibilityComboBox.setItems(obList);
    }
    
    //------------------------------------------------------------------------------------------------------------
    
    private void initTypeComboBox(){
        List<String> list = new ArrayList<>();
        list.add("file");
        list.add("folder");
        ObservableList obList = FXCollections.observableList(list);
        typeComboBox.getItems().clear();
        typeComboBox.setItems(obList);
    }
    
    //------------------------------------------------------------------------------------------------------------
    
    @FXML
    private void handleOkButtonAction(ActionEvent event) throws RemoteException, NotBoundException, FileNotFoundException, IOException{
        messagesLabel.setText("");
        if((idTextField.getText() == null || idTextField.getText().trim().isEmpty()) ||
           (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty()) ||
           (typeComboBox.getSelectionModel().isEmpty()) || (typeComboBox.getSelectionModel().isEmpty())){
                messagesLabel.setText("Fields with * required");
                return;
        }
        else{
            String description  = "";
            String folder = "";
            if(descriptionTextArea.getText().trim().isEmpty()){
                description = null;
            }
            else{
                description = descriptionTextArea.getText();
            }
            if(folderComboBox.getSelectionModel().isEmpty()){
                folder = null;
            }
            else{
                folder = String.valueOf(folderComboBox.getValue());
            }
            ProxyServer server = new ProxyServer();
            Resource r = new Resource(idTextField.getText(), nameTextField.getText(), description, String.valueOf(typeComboBox.getValue()), String.valueOf(visibilityComboBox.getValue()), _SECTION_ID, folder);
            server.addResource(r);
            
            if(typeComboBox.getValue().equals("file")){
                File fileIn = new File("C:\\Users\\Ale\\Desktop\\seatIn\\seatInUser\\" + nameTextField.getText());
                FileInputStream in =  new FileInputStream(fileIn);
                byte[] data = new byte[1024*1024];
                int length = in.read(data);
                while(length > 0){
                    server.sendData(fileIn.getName(), data, length);
                    length = in.read(data);
                }
                
            }
            
            
            messagesLabel.setText("Resource has been successfully added!");
        }
    }
    
    
}
