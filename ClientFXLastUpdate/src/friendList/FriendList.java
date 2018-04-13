/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendList;

import clientfx.ChatWindow;
import clientfx.FXMLDocumentController;
import clientfx.loginWindow.LoginWindow;
import common.ChatServerInt;
import common.User;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author TECHNOLOGY CITY
 */
public class FriendList {
    User client;
    Stage stage;
    ChatServerInt chRef;
    FriendListController controller;

    
    public FriendList() {
        
    }

    public FriendList(User _client, Stage _stage, ChatServerInt _chRef) {
        client=_client;
        stage=_stage;
        chRef=_chRef;
        
    }

    public void openWindow() {
         FXMLLoader loader = new FXMLLoader();
            controller =new FriendListController(client,chRef,stage);
            loader.setController(controller);
                
        try {
            Parent root= loader.load(getClass().getResource("friendList.fxml").openStream());
            Scene scene = new Scene(root);
           
        System.out.println("name from chat window : " + client.getFName());
//            scene.getStylesheets().add(getClass().getResource("ClientWindowStyle.css").toString());
            stage.setOnCloseRequest(e->closeChat());
            stage.setScene(scene);
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FriendList.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    }
    private void closeChat() {
        try {
            // hna kman mmkn n3ml save lldata
//            chRef.
System.out.println("closing");
            chRef.goOffline(client);
           Platform.exit();
            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
        
        
    }

     public void signOut()
        {
            LoginWindow lw=new LoginWindow();
//        
        lw.openWindow();
        }
    
    
    
    
    
    
    
    
}
