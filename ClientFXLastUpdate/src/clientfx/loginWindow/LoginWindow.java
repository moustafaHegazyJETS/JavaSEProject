/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientfx.loginWindow;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author salma hasan
 */
public class LoginWindow {
    
    String IpAddress;
    private LoginWindowFXMLController loginWCtrl;
    
    public LoginWindow(String s) {
        IpAddress = s;
    }

    public LoginWindow() {
        
    }

    public void openWindow() {
        try {
            Stage stage = new Stage();
            FXMLLoader LoginWindowLoader = new FXMLLoader();
            loginWCtrl = new LoginWindowFXMLController(stage, IpAddress);
            LoginWindowLoader.setController(loginWCtrl);
            Parent fWindow = LoginWindowLoader.load(getClass().getResource("LoginWindowFXML.fxml").openStream());
            
            stage.setScene(new Scene(fWindow));
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
//---------------------
//            FXMLLoader LoginWindowLoader = new FXMLLoader();
//            loginWCtrl = new LoginWindowFXMLController(stage,IpAddress);
//            LoginWindowLoader.setController(loginWCtrl);
//            try{
//            Parent fWindow = LoginWindowLoader.load(getClass().getResource("LoginWindowFXML.fxml").openStream());
//          
//            stage.setScene(new Scene(fWindow));
//            
//            }
//            catch(Exception e)
//            {
//                System.out.println("error can't get fxml");
//            }
//            
//            //fWindowController = fWindowLoader.getController();
//
//            
////            stage.initStyle(StageStyle.UNDECORATED);
//            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
