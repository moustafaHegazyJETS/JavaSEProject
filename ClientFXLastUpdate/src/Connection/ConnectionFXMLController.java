 
package Connection;

import Util.Checks;
import clientfx.loginWindow.LoginWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

  
public class ConnectionFXMLController implements Initializable {
    @FXML
     TextField Ip_txtf ;       
    double xOffset;
    double yOffset;
     String s ;
      
            
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
      public void exitBtnClicked(MouseEvent event) {
        System.out.println("exit clicked");
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    public void minimizeBtn(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.setIconified(true);
        System.out.println("minimize");
    }
    @FXML
    public void getIp(KeyEvent e){
        switch (e.getCode()) {
                    case ENTER:   
                    s =Ip_txtf.getText();
                 if (Checks.checkIP(s)) 
                 {
              
                     LoginWindow login = new LoginWindow(s);
                      login.openWindow();
                     ((Node) (e.getSource())).getScene().getWindow().hide();
                    
                 }else{
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setHeaderText("Wrong !");
                alert.setContentText("invalid Ip Address ");
                alert.show();
                 }
                    
        }
    }
}
