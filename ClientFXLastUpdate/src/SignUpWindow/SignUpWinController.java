/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignUpWindow;

import static Util.Checks.checkEmail;
import static Util.Checks.checkName;
import static Util.Checks.checkStringLength;
import static Util.Checks.checkUserName;
import clientfx.loginWindow.LoginWindow;
import common.ChatServerInt;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author salma hasan
 */
public class SignUpWinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    // data base connection 
    //
    Stage stage;
    double xOffset;
    double yOffset;

    @FXML
    Label loginLabel;
    @FXML
    DatePicker date;
    @FXML
    TextField email;
    @FXML
    TextField user_name;
    @FXML
    TextField fname;
    @FXML
    TextField password;
    @FXML
    MenuButton country;
    @FXML
    RadioButton male;
    @FXML
    RadioButton femal;
    ChatServerInt chRef;
    LoginWindow loginWin;

    public SignUpWinController(Stage s, ChatServerInt _chRef) {
        stage = s;
        chRef = _chRef;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        loginLabel.setStyle("-fx-font-weight: bold ;-fx-cursor:hand");

    }

    public void returnTextStyle() {
        loginLabel.setStyle("-fx-font-weight: regular;-fx-cursor:pointer");
    }

    public void goToLogin(MouseEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();   
        LoginWindow loginWin = new LoginWindow();
        loginWin.openWindow();
    }

    public void signUpBtnClicked(Event event) {
        int gender = 1;
        if (male.isSelected()) {//0 is for male
            gender = 0;
        } else {
            gender = 1;
        }

         try {
            Boolean added;

            if (!checkEmail(email.getText())) {
                System.out.println("wrong email ");
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information dialog");
                                alert.setHeaderText("invalid Email");
                                alert.show();

            } else {
                if (!checkUserName(user_name.getText())) {
                    System.out.println("wrong username ");
                      Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information dialog");
                                alert.setHeaderText("invalid username");
                                alert.show();
                } else {
                    if (!checkName(fname.getText())) {
                        System.out.println("wrong username");
                          Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information dialog");
                                alert.setHeaderText("invalid name");
                                alert.show();
                    } else {
                        if (!checkStringLength(password.getText(), 5, 20)) {
                            System.out.println("too short||Too long");
                             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information dialog");
                                alert.setHeaderText("pass word is too short||Too long");
                                alert.show();
                        } else {

                            added = chRef.signUp(user_name.getText(), password.getText(), email.getText(), fname.getText(), gender, date.getValue());
                            if (added) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information dialog");
                                alert.setHeaderText("done! ");
                                alert.setContentText("you added new user");
                                alert.showAndWait();
                                loginWin = new LoginWindow();
                                    
                                loginWin.openWindow();
                                ((Node) (event.getSource())).getScene().getWindow().hide();
                                

                            } else {
                                System.out.println("duplicate username choose another one");
                                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information dialog");
                                alert.setHeaderText("duplicate username choose another on");
                                alert.show();
                            }

                        }
                    }
                }
            }

        } catch (RemoteException ex) {
             System.out.println("can't add user ");
        }
    }

}
