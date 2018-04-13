/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registeration;

import common.ChatServerInt;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import server.ChatServerImpl;
import serverlogin.ServerLogin;
import server.ChatServerImpl;

public class RegisterFXMLController implements Initializable {

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
    double xOffset;
    double yOffset;
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=Chat";
    String user = "sa";
    String pass = "1234";
    ResultSet rs = null;

    Connection con = null;
    Statement stmt3;

    public void getStagePosition(MouseEvent evt) {
        xOffset = ((Node) (evt.getSource())).getScene().getWindow().getX() - evt.getScreenX();
        yOffset = ((Node) (evt.getSource())).getScene().getWindow().getY() - evt.getScreenY();
    }

    public void dragWindow(MouseEvent evt) {
        ((Node) (evt.getSource())).getScene().getWindow().setX(evt.getScreenX() + xOffset);
        ((Node) (evt.getSource())).getScene().getWindow().setY(evt.getScreenY() + yOffset);
    }

    public void signUpBtnClicked(MouseEvent event) {
//        try {

            int gender = 1;
            if (male.isSelected()) {//0 is for male
                gender = 0;
            } else {
                gender = 1;
            }

            Boolean added;

            added = signUp(user_name.getText(), password.getText(), email.getText(), fname.getText(), gender, date.getValue());
            if (added) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText("done! ");
                alert.setContentText("you added new user");
                Optional<ButtonType>result = alert.showAndWait();
                if(result.get()==ButtonType.OK)
                {
                   
                     ((Node) (event.getSource())).getScene().getWindow().hide();
                }else{
                     ((Node) (event.getSource())).getScene().getWindow().hide();
                }
               

            }
//        }
    }

    public void exitBtnClicked(MouseEvent event) {
        System.out.println("exit clicked");
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    public void minimizeBtn(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.setIconified(true);
        System.out.println("minimize");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private Boolean signUp(String user_name   , String password, String email, String fname, int gender, LocalDate date) {
        
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            System.out.println("can't connect to driver");
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            System.out.println("can't connect to database");
        }

        if ((!user_name.equals(null) && user_name.length() != 0)
                && (!password.equals(null) && password.length() != 0)
                && (!email.equals(null) && email.length() != 0)
                && (!fname.equals(null) && fname.length() != 0) //                && (date.getValue().lengthOfYear() == 0)
                ) {

            try {
                Statement stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt3.executeQuery("select user_name from clients where user_name like '" + user_name + "'");

                try { 
                    if (rs.next()) {
                        System.out.println("wrong user name");
                        rs.close();
                    } else {
                        rs = stmt3.executeQuery("select email from clients where user_name like '" + email + "'");
                        rs.next();
                        if (rs.next()) {
                            System.out.println("wrong email");
                            rs.close();
                        } else {
                            stmt3.executeUpdate("insert into Clients (user_name,password,fname,gender,DOB,status,email) values ('"
                                    + user_name + "','" + password + "','" + fname + "'," + gender + ",'" + date + "',0,'" + email
                                    + "')");

                            return true;

                        }

                    }
                } catch (SQLException ex) {
                    System.out.println("email error");
                }
            } catch (SQLException ex) {
                System.out.println("stmt search user name error");
            }

        } else {
            System.out.println("make sure all fields is not null");
        }

        return false;


    }

}
