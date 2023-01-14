package view;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.MessagingException;
import model.EmailSender;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class EmailSenderUIController implements Initializable {
    
    private static String _COURSE_ID;
    
    @FXML
    private TextField subjectTextField;
    
    @FXML
    private TextField toTextField;
    
    @FXML
    private ComboBox subjectComboBox;
    
    @FXML
    private TextArea messageTextArea;
    
    @FXML
    private Button sendButton;
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private void handleSendButtonAction(ActionEvent event){
        
        try {
            if(StudentMainMenuUIController.getCategory() == null){
                    new EmailSender(toTextField.getText()).sendNotificationsToStudents(String.valueOf(subjectComboBox.getValue()), messageTextArea.getText());
            }
            else{
                    new EmailSender(toTextField.getText()).sendNotificationsToStudents(subjectTextField.getText(), messageTextArea.getText());
            }
        } catch (MessagingException ex) {
                Logger.getLogger(EmailSenderUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        messagesLabel.setText("Email has been succesfully sent");
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(StudentMainMenuUIController.getCategory() != null){
            subjectTextField.setVisible(true);
            subjectComboBox.setVisible(false);
        }
        else{
            subjectTextField.setVisible(false);
            subjectComboBox.setVisible(true);
        }
        _COURSE_ID = CourseExplorerUIController.getCourseID();
        try {
            initSubjectComboBox();
        } catch (RemoteException ex) {
            Logger.getLogger(EmailSenderUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(EmailSenderUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void initSubjectComboBox() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        List<String> list = new ArrayList<>();
        
        ArrayList<String> sectionsId = server.getSectionsId(_COURSE_ID);
        ArrayList<String> sectionsNames = null;
        ObservableList<String> items = null;
        for(String x: sectionsId){
            sectionsNames = server.getSectionName(x);
             for(String y: sectionsNames){
                list.add(y);
             }
         }
        ObservableList obList = FXCollections.observableList(list);
        subjectComboBox.getItems().clear();
        subjectComboBox.setItems(obList);
                 
    }
}
