package common;

import com.healthmarketscience.rmiio.RemoteInputStream;
import java.io.InputStream;
import java.rmi.*;
public interface ClientInt extends Remote
{
	void receive( User _client, User _reciever , Message msg)throws RemoteException;
        void receiveMe( User _client, User _reciever , Message msg)throws RemoteException;
        void setId(int id)throws RemoteException;
        int getId()throws RemoteException;
        void setClientName(String name) throws RemoteException;
        String getClientName() throws RemoteException; 

    public void getFriendRequest(int idOfUserSender, int friendID)throws RemoteException;

    public void receiveOnly(String clientName, Message text, int idOfUser)throws RemoteException;
    void receiveFromServer (String msg) throws RemoteException; // besh

    public void receiveFile(User client, User reciever, InputStream sendFile, String ext2)throws RemoteException;

    public void recieveFile(User client, String filename, RemoteInputStream data, String ext)throws RemoteException;

    public void closeChatWindow(String text)throws RemoteException;

    

}