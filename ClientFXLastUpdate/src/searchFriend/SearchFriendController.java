/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchFriend;

import SignUpWindow.SignUpWinController;
import common.ChatServerInt;
import common.User;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author TECHNOLOGY CITY
 */
public class SearchFriendController implements Initializable , Serializable {

    @FXML
    TextField search;
    @FXML
    VBox friendsList;
    ChatServerInt chRef;
    ResultSet rs = null;
    ResultSet rs2 = null;

    Connection con = null;

    PreparedStatement ps = null;

    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=Chat";
    String user = "sa";
    String pass = "1234";
    Statement stmt;
    Statement stmt2;
    int idOfUser;
    

    SearchFriendController(int id, ChatServerInt friendchRef) {
        idOfUser = id;
        chRef=friendchRef;
         

    }

   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void searchFriend(Event ev) {
         ArrayList resultOfSearch = new ArrayList<User>();
        System.out.println("you typed " + search.getText());
        
        if (!search.getText().equals("") && search.getText().length() != 0) {//dh byshooof aly ana katbo fady wala la2 
            
            
            System.out.println(friendsList.getChildren().size());
            
            
            if (friendsList.getChildren().size() > 0) {
                System.out.println("removed");
                friendsList.getChildren().remove(0, friendsList.getChildren().size());//dh 3shan lma aktb tany yndf al2wlany 
            }
            
            
           
              
           
            //////////////////////////////////////////////////////////////////////////////////////
            try {
                  //            System.out.println(friendsList.getChildren().size());
               
               resultOfSearch=chRef.selectClientsFromDatabase(idOfUser,search.getText()); // de aly hatrg3ly resultset feha asamy alusers
                //aly ana 3amel behom search 
//                System.out.println("searchFriend.SearchFriendController.searchFriend()");
                
                for (int i = 0; i <  resultOfSearch.size(); i++) 
                    
                
 {
                    User u = (User) resultOfSearch.get(i);
                    String s = String.valueOf(u.getId());
                    Button addFriendbtn;
                    System.out.println("searchFriend");

//                            if(rs.getString("id").equals(rs2.getString("f.friendID"))){}
                    Label l = new Label();
                    l.setText(u.getFName());
                    l.setStyle("-fx-background-color:#99e6ff;-fx-border-radius: 15 15 15 15; -fx-background-radius: 10 10 10 10;-fx-padding:5 5 5 5;");
                    ImageView senderImage = new ImageView();
                    senderImage.setImage(new Image(getClass().getResourceAsStream("/images/images.jpg")));
                    senderImage.setFitWidth(20);
                    senderImage.setFitHeight(20);
                    Rectangle rect = new Rectangle(senderImage.getFitWidth(), senderImage.getFitHeight());

                    rect.setArcWidth(20);
                    rect.setArcHeight(20);
                    senderImage.setClip(rect);

                    addFriendbtn = new Button("ADD");
                    
                    addFriendbtn.setId(s);

                    addFriendbtn.setOnAction((event) -> {

                        try {

                            System.out.print(addFriendbtn.getId());
                           int status;
                            status=chRef.getStatus(addFriendbtn.getId());
                            //rs = stmt.executeQuery("select status from clients where id =" + addFriendbtn.getId() + "");
                            if (status==0) {
                                System.out.println("Offline");
                                chRef.addIntoFriendRequestTable(idOfUser, Integer.parseInt(addFriendbtn.getId()));
                            } else {
                                try {
                                    if(status == 1){
                                    System.out.println("online");
                                    chRef.addIntoFriendRequestTable(idOfUser, Integer.parseInt(addFriendbtn.getId()));
                                    
                                    }
                                    else
                                    {
                                        System.out.println("error in sending friend request");
                                    }
                                        
                                } catch (RemoteException ex) {
                                    Logger.getLogger(SearchFriendController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        } catch (RemoteException ex) {
                            Logger.getLogger(SearchFriendController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });

                    HBox hBox = new HBox();
                    hBox.getChildren().add(addFriendbtn);
                    hBox.getChildren().add(l);
                    hBox.getChildren().add(senderImage);
                    hBox.setAlignment(Pos.CENTER_RIGHT);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            friendsList.getChildren().add(hBox);
                            friendsList.setSpacing(10);

                        }
                    });
                
                }
                //////////////////////////////////////////////////////////////////////
//        System.out.println("email");
            } catch (Exception ex) {
                Logger.getLogger(SearchFriendController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("enter value valied"); // dh lw alsearch fady 
        }

    }

//    private void addIntoFriendRequestTable(int idOfUser, int id) {
//        try {
//            stmt.executeUpdate("insert into friendRequest values(" + idOfUser + "," + id + ",0)");
//        } catch (SQLException ex) {
//            System.out.println("you already seand request");
//
//        }
//
//    }

//    private void sendFriendRequestNow(int idOfUserSender, int friendID) throws RemoteException {
//        System.out.println("send friend request now");
//        System.out.println(idOfUserSender+"    "+friendID);
//        chRef.sendFriendRequest(idOfUserSender, friendID);
//    }

}

//                    addFriendbtn.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent e) {
//                            System.out.println("you click add");
//                            try {
//                               
//                            } catch (SQLException ex) {
//                                Logger.getLogger(SearchFriendController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            try {
//                                if (!rs2.next()) {
//                                    System.out.println("no data in rs2");
//                                } else {
//
//                                    try {
//                                       do
//                                        {
//                                            System.out.println(rs.getString("c.fname"));   
//                                        }while(rs.next());
////                                        if (rs.getString("id").equals(rs2.getString("f.friendID"))) {
////                                            System.out.println("ypu are already friends");
////                                        }
////                                        else {System.out.println("add friend");}
//                                    
//
//                                    } catch (SQLException ex) {
//                                        System.out.println("error in compare");
//
//                                    }
//                                };
//                            } catch (SQLException ex) {
//
//                            }
//                        }
//                    });
//code salma last 
// System.out.println(rs.getString("fname") + "kjhjhjhj");
//                    if (rs2.next()) {
//                        while(!rs2.getString("ff").equals(rs.getString("id"))) {
////                            if(rs.getString("id").equals(rs2.getString("f.friendID"))){}
//                            Label l = new Label();
//                            l.setText(rs.getString("fname"));
//                            l.setStyle("-fx-background-color:#99e6ff;-fx-border-radius: 15 15 15 15; -fx-background-radius: 10 10 10 10;-fx-padding:5 5 5 5;");
//                            ImageView senderImage = new ImageView();
//                            senderImage.setImage(new Image(getClass().getResourceAsStream("/images/images.jpg")));
//                            senderImage.setFitWidth(20);
//                            senderImage.setFitHeight(20);
//                            Rectangle rect = new Rectangle(senderImage.getFitWidth(), senderImage.getFitHeight());
//
//                            rect.setArcWidth(20);
//                            rect.setArcHeight(20);
//                            senderImage.setClip(rect);
//
//                            addFriendbtn = new Button("ADD");
//
//                            HBox hBox = new HBox();
//                            hBox.getChildren().add(addFriendbtn);
//                            hBox.getChildren().add(l);
//                            hBox.getChildren().add(senderImage);
//                            hBox.setAlignment(Pos.CENTER_RIGHT);
//
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    friendsList.getChildren().add(hBox);
//                                    friendsList.setSpacing(10);
//
//                                }
//                            });
//                        }
//                    }
