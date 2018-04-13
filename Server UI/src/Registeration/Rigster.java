/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registeration;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Besho
 */
public class Rigster {
    Stage primaryStage = new Stage() ;
   
    public void show() throws IOException {
 
        Parent root = FXMLLoader.load(getClass().getResource("../Registeration/RegisterFXML.fxml"));     
           Scene scene2 = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene2);
        primaryStage.initModality(Modality.APPLICATION_MODAL); //
                primaryStage.show();
    }
}
