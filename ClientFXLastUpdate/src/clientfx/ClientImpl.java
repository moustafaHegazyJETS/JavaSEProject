package clientfx;

import com.healthmarketscience.rmiio.RemoteInputStream;
import common.ClientInt;
import common.Message;
import common.User;
import friendList.FriendListController;
import java.io.InputStream;
import java.rmi.*;
import java.rmi.server.*;

public class ClientImpl extends UnicastRemoteObject implements ClientInt {

    private int clientId;
    String clientName;
    FriendListController c;

    public ClientImpl(FriendListController _c) throws RemoteException {

        c = _c;

    }

    @Override
    public void setId(int id) throws RemoteException {
        clientId = id;
    }

    @Override
    public int getId() throws RemoteException {
        return clientId;
    }

    @Override
    public void setClientName(String cName) {
        clientName = cName;
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public void getFriendRequest(int idOfUserSender, int friendID) throws RemoteException {
    }

    @Override
    public void receiveOnly(String clientName, Message text, int idOfUser) throws RemoteException {

    }

    @Override
    public void receiveFromServer(String msg) {
        c.displayFromServer(msg);
    }

    @Override
    public void receiveMe(User _client, User _reciever, Message msg) throws RemoteException {
        System.out.println("inside recieve me");
        c.display(_client, _reciever, msg);
    }

    @Override
    public void receive(User _client, User _reciever, Message msg) throws RemoteException {
        System.out.println("i have reached recieve method");
        c.display(_client, _reciever, msg);
    }

    @Override
    public void recieveFile(User client, String filename, RemoteInputStream data, String ext) throws RemoteException {
        c.recieveFile(client, filename, data, ext);

    }

    @Override
    public void receiveFile(User client, User reciever, InputStream sendFile, String ext2) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void closeChatWindow(String text) throws RemoteException {
        c.closeFromServer(text);
        
    }

}
