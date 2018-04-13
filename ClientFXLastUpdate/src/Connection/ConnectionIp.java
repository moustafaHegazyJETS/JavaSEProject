/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author TECHNOLOGY CITY
 */
public class ConnectionIp {
    Stage primaryStage = new Stage() ;
 
    public void showIp()throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("ConnectionFXML.fxml"));     
           Scene scene2 = new Scene(root);
        primaryStage.setScene(scene2);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.initModality(Modality.APPLICATION_MODAL); //
                primaryStage.show();
     }
}
