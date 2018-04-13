/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverlogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServerFXMLController implements Initializable {
     @FXML     
     TextField server_txtf ;
      @FXML        
     TextField serverp_txtf ;
    ServerLogin s;
    double xOffset;
    double yOffset;

    public void exitBtnClicked(MouseEvent event) {
        System.out.println("exit clicked");
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    public void minimizeBtn(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.setIconified(true);
        System.out.println("minimize");
    }
    public void login_server(MouseEvent e) throws IOException {
        if (server_txtf.getText().equals("admin")&&serverp_txtf.getText().equals("admin") ){
        s = new ServerLogin();
        s.show();
        ((Node) (e.getSource())).getScene().getWindow().hide();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR, "invalid user name and password ");
            a.initStyle(StageStyle.UNDECORATED);
            a.show();
        }
    }
  public void setOnserverKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
                    case ENTER:
        if (server_txtf.getText().equals("admin")&&serverp_txtf.getText().equals("admin") ){
        s = new ServerLogin();
            try {
                s.show();
            } catch (IOException ex) {
                Logger.getLogger(ServerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        ((Node) (e.getSource())).getScene().getWindow().hide();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR, "invalid user name and password ");
            a.initStyle(StageStyle.UNDECORATED);
            a.show();
        }
        }
  }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void getStagePosition(MouseEvent evt) {
        xOffset = ((Node) (evt.getSource())).getScene().getWindow().getX() - evt.getScreenX();
        yOffset = ((Node) (evt.getSource())).getScene().getWindow().getY() - evt.getScreenY();
    }

    public void dragWindow(MouseEvent evt) {
        ((Node) (evt.getSource())).getScene().getWindow().setX(evt.getScreenX() + xOffset);
        ((Node) (evt.getSource())).getScene().getWindow().setY(evt.getScreenY() + yOffset);
    }

}
