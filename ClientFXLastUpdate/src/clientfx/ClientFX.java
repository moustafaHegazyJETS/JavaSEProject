/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientfx;

import Connection.ConnectionIp;
import SignUpWindow.SignUp;
import clientfx.loginWindow.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author TECHNOLOGY CITY
 */
 
public class ClientFX extends Application {
    Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
         
        stage = primaryStage;
        stage.initStyle(StageStyle.DECORATED);
        ConnectionIp conn = new ConnectionIp();
        conn.showIp();
        
//        SignUp signUpW = new SignUp(stage);
//        signUpW.openSignUpWin();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}

