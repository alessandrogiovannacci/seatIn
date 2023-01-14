package view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.MessagingException;
import model.EmailSender;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class EmailSenderUIController implements Initializable {
    
    /*
    @FXML
    private ChoiceBox toChoiceBox;
    */
    
    @FXML
    private TextField toTextField;
    
    @FXML
    private TextField subjectTextField;
    
    @FXML
    private TextArea messageTextArea;
    
    @FXML
    private Button sendButton;
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private void handleSendButtonAction(ActionEvent event){
        messagesLabel.setText("");
        if ((subjectTextField.getText() == null || subjectTextField.getText().trim().isEmpty())) {
                messagesLabel.setText("All fields are required");
                return;
        }
        else{
            try {
                new EmailSender(toTextField.getText()).sendNotificationsToStudents(subjectTextField.getText(), messageTextArea.getText());
            } catch (MessagingException ex) {
                Logger.getLogger(EmailSenderUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            messagesLabel.setText("Email has been succesfully sent");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
