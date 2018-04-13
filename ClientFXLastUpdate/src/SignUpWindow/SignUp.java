/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignUpWindow;

import clientfx.loginWindow.LoginWindow;
import clientfx.loginWindow.LoginWindowFXMLController;
import common.ChatServerInt;
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
public class SignUp {

    private SignUpWinController signUpCtrl;
    Stage stage;
    ChatServerInt chRef;

    public SignUp(Stage s, ChatServerInt _chRef) {
        stage = s;
        chRef = _chRef;
        System.out.println("Sign up window const");
    }

    public void openSignUpWin() {
        try {

            FXMLLoader signUpWindowLoader = new FXMLLoader();
            signUpCtrl = new SignUpWinController(stage, chRef);
            signUpWindowLoader.setController(signUpCtrl);
            Parent fWindow = signUpWindowLoader.load(getClass().getResource("SignUpWin.fxml").openStream());

            stage.setScene(new Scene(fWindow));
//            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
