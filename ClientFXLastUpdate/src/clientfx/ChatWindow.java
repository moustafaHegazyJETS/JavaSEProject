/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientfx;

import common.ChatServerInt;
import common.Message;
import common.User;
import friendList.FriendListController;
 import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

 
public class ChatWindow {

    public FXMLDocumentController controller;
    User client;
    User reciever;
    ChatServerInt chRef;
     int idOfUser;
   static Message msg;
    FriendListController controllerPrev;
 
static int idOfPage=0;
    public ChatWindow(User _client, User _reciever, ChatServerInt _chRef,FriendListController f) {
            client=_client;
            chRef=_chRef;
            reciever=_reciever;
            controllerPrev=f;
            idOfPage++;
            
    }

    public void getChatWindow() {
        try {
            Stage stage=new Stage();
            stage.initStyle(StageStyle.DECORATED);
             stage.setOnCloseRequest(e->closeChat());
         
            FXMLLoader loader = new FXMLLoader();
            controller =new FXMLDocumentController(client,reciever,chRef,msg,idOfPage); 
            loader.setController(controller);
            Parent root = loader.load(getClass().getResource("FXMLDocument.fxml").openStream());            
            
            Scene scene = new Scene(root);
 
            
            stage.setScene(scene);
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeChat() {
        
 
            controllerPrev.addClosedChat(reciever);
        
        
        
    }
    public static void sendMessage (Message _msg)
    {
       msg=_msg; 
    }

}
