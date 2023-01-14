package view;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class DeleteSectionManagerUIController implements Initializable {
    
    private static String _COURSE_ID;
    
    @FXML
    private ComboBox sectionIdComboBox;
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private Label warningLabel;
    
    @FXML
    private void handleOkButtonAction(ActionEvent event) throws RemoteException, NotBoundException{
        warningLabel.setText("");
        if(sectionIdComboBox.getSelectionModel().isEmpty()){
            warningLabel.setText("Section ID required");
            return;
        }
        else{
            ProxyServer server = new ProxyServer();
            server.deleteSection(sectionIdComboBox.getSelectionModel().getSelectedItem().toString());
            warningLabel.setText("Section and subsections/resources related has been successfully deleted");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _COURSE_ID = CourseExplorerUIController.getCourseID();
        try {
            initSectionIdComboBox();
        } catch (RemoteException ex) {
            Logger.getLogger(DeleteSectionManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(DeleteSectionManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initSectionIdComboBox() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        ArrayList<String> sectionsId;
        sectionsId = server.getSectionsId(_COURSE_ID);
        ObservableList obList = FXCollections.observableList(sectionsId);
        sectionIdComboBox.getItems().clear();
        sectionIdComboBox.setItems(obList);
    }
    
}
