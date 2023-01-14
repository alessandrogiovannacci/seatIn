package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ale
 */
public class SeatInAdmin extends Application {
    
    @Override
    public void start(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLoginUI.fxml"));
        //URL url = new File("C:\\Users\\Ale\\Desktop\\seatInAdmin\\src\\view\\FXMLoginUI.fxml").toURL();           
        //Parent root = FXMLLoader.load(url);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("seatInAdmin");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
