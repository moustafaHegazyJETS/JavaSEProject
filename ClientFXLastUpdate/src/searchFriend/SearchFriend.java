/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchFriend;

import SignUpWindow.SignUpWinController;
import common.ChatServerInt;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author TECHNOLOGY CITY
 */

public class SearchFriend {
    private SearchFriendController searchController;
    
     Stage stage;
     int idOfUser;
      ChatServerInt friendchRef;
    public SearchFriend (int id, ChatServerInt chRef) //hegazy edite
    {
        idOfUser=id;
        friendchRef=chRef;
        
    }

    
    public void openAddPage() throws IOException {
        Stage s = new Stage();
         FXMLLoader searchWindowLoader = new FXMLLoader();
         searchController= new SearchFriendController(idOfUser,friendchRef);
          searchWindowLoader.setController(searchController);
          Parent fWindow = searchWindowLoader.load(getClass().getResource("SearchFriend.fxml").openStream());
          s.setScene(new Scene(fWindow));
          //C:\\Users\\TECHNOLOGY CITY\\Documents\\NetBeansProjects\\ClientFX\\build\\classes\\searchFriend\\
          s.show();
          
         
    }
}
