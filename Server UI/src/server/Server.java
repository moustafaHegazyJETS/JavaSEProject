 
package server;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Besho
 */
public class Server extends Application {
    Stage stage= new Stage();
    @Override
    public void start(Stage stage) throws Exception {
         Parent root = FXMLLoader.load(getClass().getResource("../serverlogin/serverFXML.fxml"));
        
        Scene scene = new Scene(root);
         stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

 

    public static void main(String[] args) {
        launch(args);
    }
    
}

 