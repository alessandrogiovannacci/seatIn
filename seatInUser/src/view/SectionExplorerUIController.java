package view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ProxyServer;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class SectionExplorerUIController implements Initializable {
    
    private static String _SECTION_ID;
    private static boolean SUBSECTION = false;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label descriptionLabel;
    
    @FXML
    private Label subsectionsLabel;
    
    @FXML
    private Label noSubsectionsMessageLabel;
    
    @FXML
    private Button okButton;
    
    @FXML
    private TextField subsectionIdTextField;
    
    @FXML
    private Label insertSubsectionIdLabel;
    
    @FXML
    private Label sectionWarningMessagesLabel;
    
    @FXML
    private Label resourcesLabel;
    
    @FXML
    private Label noResourcesMessageLabel;
    
    @FXML
    private Button downloadButton;
    
    @FXML
    private Label insertFileIdLabel;
    
    @FXML
    private TextField fileIdTextField;
    
    @FXML
    private Label fileDownloadedMessage;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(!SUBSECTION){
            _SECTION_ID = CourseExplorerUIController.getSectionID();
        }
        else{
            _SECTION_ID = SectionExplorerUIController.getSectionId();
        }
        SUBSECTION = false;
        
        
        try {
            initSectionLabels();
        } catch (RemoteException ex) {
            Logger.getLogger(SectionExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(SectionExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            initSubsectionsLabel();
        } catch (RemoteException ex) {
            Logger.getLogger(SectionExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(SectionExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            initResourcesLabel();
        } catch (RemoteException ex) {
            Logger.getLogger(SectionExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(SectionExplorerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
              

    }    
    
    private void initSectionLabels() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        String[] sectionData = server.getSectionData(_SECTION_ID);
        nameLabel.setText(sectionData[0]);
        descriptionLabel.setText(sectionData[1]);
    }
    
    private void initSubsectionsLabel() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        ArrayList<String> sections;
        sections = server.getSubsectionData(_SECTION_ID);
        if(sections.isEmpty()){
            noSubsectionsMessageLabel.setText("There are no subsections to display");
            noSubsectionsMessageLabel.setVisible(true);
            return;
        }
        subsectionsLabel.setText("SECTION ID " + "\t" + "NAME" + "\n");
        int i = 0;
        for(String x: sections){
            subsectionsLabel.setText(subsectionsLabel.getText() + "" + x + "\t" + "\t" + "\t");
            i++;
            if(i % 2 == 0){
                subsectionsLabel.setText(subsectionsLabel.getText() + "\n");
            }      
        }
        okButton.setVisible(true);
        subsectionIdTextField.setVisible(true);
        insertSubsectionIdLabel.setVisible(true);
    }
    
    @FXML
    private void handleOkButtonAction(ActionEvent event) throws IOException{
        sectionWarningMessagesLabel.setText("");
        if(subsectionIdTextField.getText() == null || subsectionIdTextField.getText().trim().isEmpty()){
            sectionWarningMessagesLabel.setText("Insert section ID");
            return;
        }
        else{
            _SECTION_ID = subsectionIdTextField.getText();
            SUBSECTION = true;
            Parent root = FXMLLoader.load(getClass().getResource("SectionExplorerUI.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("seatInUser");
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    public static String getSectionId(){
        return _SECTION_ID;
    }
    
    private void initResourcesLabel() throws RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        ArrayList<String> resources;
        resources = server.getResourcesData(_SECTION_ID);
        
        if(resources.isEmpty()){
            noResourcesMessageLabel.setText("There are no resources to display");
            noResourcesMessageLabel.setVisible(true);
            return;
        }
        resourcesLabel.setText("RESOURCE ID " + "\t" + "\t" + "NAME" + "\t" + "\t" + "\t" + "\t" + "\t" + "TYPE" + "\n");
        int i = 0; 
        for(String x: resources){
            resourcesLabel.setText(resourcesLabel.getText() + "" + x + "\t" + "\t" + "\t" + "\t");
            i++;
            if(i % 3 == 0){
                resourcesLabel.setText(resourcesLabel.getText() + "\n");
            }      
        }
        insertFileIdLabel.setVisible(true);
        fileIdTextField.setVisible(true);
        downloadButton.setVisible(true);
        
    }
    
    //TODO
    @FXML
    private void handleDownloadButtonAction(ActionEvent event) throws IOException, RemoteException, NotBoundException{
        ProxyServer server = new ProxyServer();
        List<String> res = server.downloadFile(fileIdTextField.getText());
        
        OutputStream outputStream = null;
        outputStream = new FileOutputStream(new File("C:\\Users\\Ale\\Documents\\NetBeansProjects\\seatInUser\\" + fileIdTextField.getText()));
        for(String str: res){
            outputStream.write(str.getBytes(), 0, str.length());
        }
        outputStream.close();
        fileDownloadedMessage.setText("Download has been successfully completed");
        
        java.util.Date now = new java.util.Date();
        
        java.sql.Date d = new Date(now.getTime());
        server.updateDownloadNumberAndDate(fileIdTextField.getText(), d);
        
    }
}
