package common;



import com.healthmarketscience.rmiio.RemoteInputStream;
import java.io.InputStream;
import java.rmi.*;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public interface ChatServerInt extends Remote
{
	void tellOthers(String cName , Message msg,int id)throws RemoteException;
	int register(ClientInt cliebntRef,int id)throws RemoteException;
	void unRegister(ClientInt cliebntRef)throws RemoteException;

   //to send friend for online
    public void sendFriendRequest(int idOfUserSender, int friendID)  throws RemoteException;

    public void tellOnly(User client, Message text, User reciever)throws RemoteException; 

    void servertellOthers(String text) throws RemoteException;//besho

    public User login(String userName, String password) throws RemoteException;

    public void setLoginStatus(User client , ClientInt currentClient) throws RemoteException;

    public Boolean signUp(String user_name, String password, String email, String fname, int gender, LocalDate date)throws RemoteException;

    public ArrayList selectClientsFromDatabase(int idOfUser, String friendEmail) throws RemoteException;

    public int getStatus(String id) throws RemoteException ;

    public void addIntoFriendRequestTable(int idOfUser, int idOfFriend) throws RemoteException;

    public void goOffline(User client) throws RemoteException ;

    public ArrayList getFriendRequest(int id) throws RemoteException ;

    public void confirmFriendRequest(int idOfUser, int userID) throws RemoteException;

    public void ignoreRequest(int idOfUser, int userID)throws RemoteException;

    
    public ArrayList<User> friendList(int id)throws RemoteException;

    public void TellOtheFiles(InputStream sendFile, String ext2, User client ,User reciever)throws RemoteException;

    public void removeFromFriendList(User client, String id)throws RemoteException;

    public void modeChanged(User client, String statues)throws RemoteException;
    
    public void sendFile(String filename,User client,User reciever, RemoteInputStream data, String ext)throws RemoteException;

    public void closeAllOpenWindows(String text)throws RemoteException;
    
    
	
}  