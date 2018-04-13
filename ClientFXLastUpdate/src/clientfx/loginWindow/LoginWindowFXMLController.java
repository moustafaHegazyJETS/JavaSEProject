/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientfx.loginWindow;

import SignUpWindow.SignUp;
import SignUpWindow.SignUpWinController;
import Util.Checks;
import static Util.Checks.checkStringEmpty;
import static Util.Checks.checkUserName;
import clientfx.*;
import com.sun.jndi.dns.DnsContextFactory;
import common.ChatServerInt;
import common.User;
import friendList.FriendList;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author salma hasan
 */
public class LoginWindowFXMLController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Label signUpLabel;
    @FXML
    TextField passwordField;

    double xOffset;
    double yOffset;

    Stage stage;
    String IpAddress;
    Registry reg;
    ChatServerInt chRef;

    /**
     * Initializes the controller class.
     *
     * @param s
     */
    public LoginWindowFXMLController(Stage s,String _ip) {
        stage = s;
        IpAddress=_ip ;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            reg = LocateRegistry.getRegistry(IpAddress,29000);
            chRef = (ChatServerInt) reg.lookup("ChatService");

        } catch (RemoteException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, "server is closed right now login later");
            a.showAndWait();
            Platform.exit();
            
        } catch (NotBoundException ex) {
             Alert a = new Alert(Alert.AlertType.ERROR, "service not found");
                         a.showAndWait();
            Platform.exit();

        }

    }

    public void saveName(MouseEvent e) //hegazy 
    {

        try {
            // dh codealdatabase aly f alserver 
            if (!checkUserName(nameField.getText())) {
                System.out.println("error in username");
            } else {
                if (!checkStringEmpty(passwordField.getText())) {
                    System.out.println("you should enter password");
                } else {
                    if (!checkUserName(passwordField.getText())) {
                        System.out.println("password error ");

                    } else {
                        User client = chRef.login(nameField.getText(), passwordField.getText());
                        if (client == null) {
                            System.out.println("no such account");
                        } else {
                            FriendList friendListwindow = new FriendList(client, stage, chRef);
                            friendListwindow.openWindow();
                        }

                    }
                }

            }

        } catch (RemoteException ex) {
            Logger.getLogger(LoginWindowFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exitBtnClicked(MouseEvent event) {
        System.out.println("exit clicked");
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    public void minimizeBtn(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.setIconified(true);
        //stage.setIconified(false);
        System.out.println("minimize");
    }

    public void getStagePosition(MouseEvent evt) {
        xOffset = ((Node) (evt.getSource())).getScene().getWindow().getX() - evt.getScreenX();
        yOffset = ((Node) (evt.getSource())).getScene().getWindow().getY() - evt.getScreenY();
    }

    public void dragWindow(MouseEvent evt) {
        ((Node) (evt.getSource())).getScene().getWindow().setX(evt.getScreenX() + xOffset);
        ((Node) (evt.getSource())).getScene().getWindow().setY(evt.getScreenY() + yOffset);
    }

    public void makeTextBold() {
        signUpLabel.setStyle("-fx-font-weight: bold ;-fx-cursor:hand");

    }

    public void returnTextStyle() {
        signUpLabel.setStyle("-fx-font-weight: regular;-fx-cursor:pointer");
    }

    public void goToSignUp() {
        SignUp signUpWin = new SignUp(stage, chRef);
        signUpWin.openSignUpWin();
    }
}
