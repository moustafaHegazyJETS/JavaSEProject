package server;

//import com.sun.security.ntlm.Client;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import common.ChatServerInt;
import common.ClientInt;
import common.Message;
import common.User;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.xml.bind.JAXBException;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServerInt, Serializable {

    //database connection 
    ResultSet rs = null;

    Connection con = null;

    PreparedStatement ps = null;

    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=Chat";
    String user = "sa";
    String pass = "1234";
    Statement stmt;
    Statement stmt2;

    //for recieve message
    ClientInt clientRef;

    static int id = 0;
    static int lastSenderid = 39;
    Vector<ClientInt> clientsVector = new Vector<ClientInt>();
    Vector<ClientInt> lastSender = new Vector<ClientInt>();
    Vector<User> loginedClients = new Vector<User>();

    public ChatServerImpl() throws RemoteException {
    }

    @Override
    public int register(ClientInt clientRef, int idOfUser) throws RemoteException {
        clientRef.setId(idOfUser);
        clientsVector.add(id, clientRef);
        id++;
//                clientsVector.indexOf(clientRef);
        System.out.println("Client added" + clientsVector.indexOf(clientRef));
        return clientRef.getId();
    }

    @Override
    public void unRegister(ClientInt clientRef) throws RemoteException {
        clientsVector.remove(clientRef);
        System.out.println("Client removed");
    }

    @Override
    public void tellOthers(String senderName2, Message msg, int id) throws RemoteException {
        System.out.println("Message received: " + msg.getText());
        for (ClientInt clientRef : clientsVector) {
            try {
                if (clientRef.getId() == id) {
//                    clientRef.receiveMe(senderName2, msg);
                } else {
//                    clientRef.receive(senderName2, msg.getText());
                }

            } catch (RemoteException ex) {
                System.out.println("Can not send message to client");
                ex.printStackTrace();

            }

        }

//        SaveChatSession writeIntoXml = new SaveChatSession(_rec , msg , id);
//        try {
//            writeIntoXml.write();
//        } catch (JAXBException ex) {
//            System.out.println("error in executing jaxb class");
//        }
    }

    @Override
    public void sendFriendRequest(int idOfUserSender, int friendID) throws RemoteException {
        //clientRef.getFriendRequest(idOfUserSender, friendID);
    }

    public int checkOnline(User recieverUser) {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            System.out.println("can't connect to driver");
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            System.out.println("can't connect to database");

            try {
                Statement stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt3.executeQuery("select status from clients where id='" + recieverUser.getId() + "'");
                if (rs.next()) {
                    return Integer.parseInt(rs.getString("status"));
                }
            } catch (Exception e) {
            }

        }
        return 0;
    }

    @Override
    public void tellOnly(User _client, Message msg, User _reciever) throws RemoteException {
        //clientName dh esm aly byb3t , text dh altext aly bytb3t , idofreciever dh aly 3ayz ywsloo hwh bs , id of user dh alid bta3y ana bs 
        System.out.println("Message received: " + msg.getText());
        System.out.println("Message received from : " + _client.getFName());
//        System.out.println("Message will be send to: " + _reciever.getFName());
        System.out.println("Clients vector " + clientsVector.get(0));

//        int checkOnline = checkOnline(_reciever);
        for (int i = 0; i < clientsVector.size(); i++) {
            ClientInt checkChRef = clientsVector.get(i);
            System.out.println("clients ids" + clientsVector.get(i).getId());
            if (checkChRef.getId() == _reciever.getId()) {
                checkChRef.receive(_client, _reciever, msg);
                System.out.println("ana mawgood f om alclient vector ya3ny ana lya chatInt");
                //almfrod hgna hanroo7 n5znha f malf al xml aly b3d lma yft7 hay2rah
            } else {
                System.out.println("can't recieve dlw2ty");

            }

        }

//        for (int i = 0; i < loginedClients.size(); i++) {
//            reciever = loginedClients.get(i);
//            if (reciever.getId() == idOfUser && reciever.getId() == idofReciever) {
//                {
//                    reciever.receiveMe(clientName, msg.getText());
////                    if(lastSenderid!=reciever.getId())
////                    this.tellOnly(clientName, text, reciever.getId(),lastSenderid);
////                    reciever=lastSender.get(lastSender.size()-2);
////                    reciever.receive(clientName, text);
//                    System.out.println("im the reciever and the sender");
//                }
//            } else {
//                if (reciever.getId() == idOfUser) {
//                    reciever.receiveMe(clientName, msg.getText());
//
////                    lastSender.add(reciever);
//                    System.out.println(idOfUser + "id of user sender" + idofReciever + "id of reciever");
//
//                    //de lw ana asln alreciever w ana asln alsender 3shan tzhr 3ndy
//                } else {
//                    if (reciever.getId() == idofReciever) {
//
//                        reciever.receiveOnly(clientName, msg.getText(), idOfUser);
////                         if(idOfUser!=lastSenderid)
////                    lastSenderid=reciever.getId();
////                        lastSender.add(reciever);
////                                            System.out.println(idOfUser+"id of user sender"+idofReciever+"id of reciever");
//
////                                          System.out.println(idOfUser+"id of user sender"+idofReciever+"id of reciever");
//                        //de btb3t mn ay user ll user almo3yen aly hna static hwh 39 
//                    }
//
//                }
//
////                          
//            }
//
//        }
        SaveChatSession writeIntoXml = new SaveChatSession(_reciever, msg, _client);
        try {
            writeIntoXml.write();
        } catch (JAXBException ex) {
            System.out.println("error in executing jaxb class");
        }

    }

    @Override
    public void servertellOthers(String text) {
        System.out.println("in server tell other");
        for (ClientInt clientRef : clientsVector) {
            try {
                clientRef.receiveFromServer(text);
            } catch (RemoteException ex) {
                System.out.println("Can not send message to client");

            }
        }

    }

    @Override
    public User login(String userName, String password) throws RemoteException {
        User loginUser = new User();
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

        try {
//            System.out.println(nameField.getText() + "    " + passwordField.getText());
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("select * from clients where user_name like '" + userName + "' and password like '" + password + "'");
            rs.next();
//    System.out.println(rs.getString("fname"));
            try {
                if (rs.getString("fname").equals(null)) {
                    System.out.println("no login");
                } else {
                    if (rs.getInt("status") == 1) {
                        System.out.println("you already online");
                    } else {
                        loginUser.setFName(rs.getString("fname"));
                        loginUser.setAge(Integer.parseInt(rs.getString("age")));
                        loginUser.setDOB(rs.getString("DOB"));
                        loginUser.setEmail(rs.getString("email"));
                        loginUser.setGender(Integer.parseInt(rs.getString("gender")));
                        loginUser.setID(Integer.parseInt(rs.getString("ID")));
                        loginUser.setStatus(Integer.parseInt(rs.getString("status")));
                        loginUser.setUserName(rs.getString("user_name"));
                        return loginUser;

                    }
                }
            } catch (Exception ex) {
                System.out.println("error in loging");
            }
//            ((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (Exception ex) {
            System.out.println("clientfx.loginWindow.LoginWindowFXMLController.saveName()");
        }
        return null;

    }

    private void sendNotificationToBeOnline(User client) {
        try {
            List<User> friendUsers;
            friendUsers = friendList(client.getId());

            for (ClientInt clientRef : clientsVector) {
                try {
                    for (User u : friendUsers) {
                        if (clientRef.getId() == u.getId()) {
                            clientRef.receiveFromServer(client.getFName() + "   " + "is online now");
                        }
                        System.out.println("clinet reference id" + clientRef.getId());
                        System.out.println("id of user online kman " + u.getId());
                    }

                } catch (RemoteException ex) {
                    System.out.println("Can not send message to client");

                }
            }

        } catch (RemoteException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void setLoginStatus(User client, ClientInt _clientRef) throws RemoteException {
        try {
            clientRef = _clientRef;
            stmt2 = con.createStatement();
            stmt2.executeUpdate("update clients set status = 1 where id = " + client.getId() + "");

            clientRef.setId(client.getId());
            System.out.println(clientsVector.isEmpty()+"-----------------------"+id+"+++++++++++++"+clientRef);
            clientsVector.add(id, clientRef);
            id++;
            System.out.println(client.getId() + "client has been login and added to the sysytem  " + client.getFName());
            sendNotificationToBeOnline(client);

//            loginedClients.add(client);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Boolean signUp(String user_name, String password, String email, String fname, int gender, LocalDate date) throws RemoteException {
        boolean add = false;

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

                try {//rs.getString("user_name").equals(null)   !(rs.getString("email").equals(null))
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
//                            rs.close();
//                            stmt3.close();
//                            con.close();
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

    @Override
    public ArrayList selectClientsFromDatabase(int idOfUser, String friendEmail) throws RemoteException {

        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            //    Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            //Logger.getLogger(SearchFriendController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
//                System.out.println("entered if con");
            ArrayList resultOfSearch = new ArrayList<User>();

            User userOfSearch;
            rs = stmt.executeQuery("select fname,id from clients where email like '%" + friendEmail + "%' and ID <>" + idOfUser + " except select  c.fname, f.friendID as ff  from friend f , Clients c where f.friendID=c.ID and  f.id =" + idOfUser + " and c.ID<>" + idOfUser + " and c.email like '%" + friendEmail + "%'" + "");

            while (rs.next()) {
                userOfSearch = new User();
                userOfSearch.setFName(rs.getString("fname"));
                userOfSearch.setID(Integer.parseInt(rs.getString("id")));
                resultOfSearch.add(userOfSearch);

            }
            return resultOfSearch;
//                rs2 = stmt2.executeQuery("select f.friendID as ff , c.fname,c.email from friend f , Clients c where f.friendID=c.ID and  f.id = " + idOfUser + " and c.ID<>" + idOfUser + " and c.email like '%" + search.getText() + "%'");
        } catch (SQLException ex) {
            //Logger.getLogger(SearchFriendController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    @Override
    public int getStatus(String id) throws RemoteException {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            //    Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs = stmt.executeQuery("select status from clients where id =" + Integer.parseInt(id) + "");
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (rs.next() && Integer.parseInt(rs.getString("status")) == 0) {
                return 0;
            } else {

                if (rs.next() && Integer.parseInt(rs.getString("status")) == 1) {
                    return 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 2;

    }

    @Override
    public void addIntoFriendRequestTable(int idOfUser, int idOfFriend) throws RemoteException {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            //    Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stmt.executeUpdate("insert into friendRequest values(" + idOfUser + "," + idOfFriend + ",0)");
            System.out.println("inserted");
        } catch (SQLException ex) {
            System.out.println("you already seand request");

        }

    }

    @Override
    public void goOffline(User _client) throws RemoteException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            //    Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stmt.executeUpdate("update clients set status = 0 where id =  " + _client.getId() + "");

            sendNotificationToBeOffline(_client);

            //ana 3ayz azbt hna ano ytshal kman mn alclientInterface
//            clientsVector.remove(id);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendNotificationToBeOffline(User _client) {
        try {
            List<User> friendUsers;
            friendUsers = friendList(_client.getId());

            for (ClientInt clientRef : clientsVector) {
                try {
                    for (User u : friendUsers) {
                        if (clientRef.getId() == u.getId()) {
                            clientRef.receiveFromServer(_client.getFName() + "   " + "is Offline now");
                        }
                        System.out.println("clinet reference id" + clientRef.getId());
                        System.out.println("id of user online kman " + u.getId());
                    }

                } catch (RemoteException ex) {
                    System.out.println("Can not send message to client");

                }
            }

        } catch (RemoteException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList getFriendRequest(int id) throws RemoteException {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {

        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            //    Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs = stmt.executeQuery("select f.id as FID ,fname from friendRequest f , Clients c  where f.id=c.ID and  requestID=" + id + "");
            ArrayList resultOfSearch = new ArrayList<User>();

            User userOfSearch;

            while (rs.next()) {
                userOfSearch = new User();
                userOfSearch.setFName(rs.getString("fname"));
                userOfSearch.setID(Integer.parseInt(rs.getString("FID")));
                resultOfSearch.add(userOfSearch);

            }
            return resultOfSearch;

        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void confirmFriendRequest(int idOfUser, int userID) throws RemoteException {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            //    Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stmt.executeUpdate("insert into friend values (" + idOfUser + "," + userID + ")");
            stmt.executeUpdate("insert into friend values (" + userID + "," + idOfUser + ")");
            stmt.executeUpdate("delete from friendRequest where id=" + userID + " and requestID=" + idOfUser);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void ignoreRequest(int idOfUser, int userID) throws RemoteException {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            //    Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            stmt.executeUpdate("delete from friendRequest where id=" + userID + " and requestID=" + idOfUser);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // dh aly byrg3ly alfriends awel lma a3mel login 
    @Override
    public ArrayList<User> friendList(int id) throws RemoteException {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            //    Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ArrayList<User> friendUser = new ArrayList<>();
            rs = stmt.executeQuery("select c.ID as id, c.fname as fname , c.user_name as username,c.gender as gender,c.status as status,c.email as email,c.age as age,c.DOB as dob from Clients c , friend  f where c.ID=f.friendID and f.ID=" + id + "");
            if (rs.next()) {
                do {
                    User user = new User();
                    user.setAge(Integer.parseInt(rs.getString("age")));
                    user.setDOB(rs.getString("dob"));
                    user.setEmail(rs.getString("email"));
                    user.setFName(rs.getString("fname"));
                    user.setID(Integer.parseInt(rs.getString("id")));
//                 user.setProfilePic(Image.impl_fromPlatformImage(rs.getString("pic")));
                    user.setStatus(Integer.parseInt(rs.getString("status")));
                    user.setGender(Integer.parseInt(rs.getString("gender")));
                    user.setUserName(rs.getString("username"));
                    friendUser.add(user);

                } while (rs.next());
            } else {
                System.out.println("have no friend");
            }
            return friendUser;
//                            stmt.executeUpdate("delete from friendRequest where id=" + userID + " and requestID=" + idOfUser);
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void TellOtheFiles(InputStream sendFile, String ext2, User client, User reciever) throws RemoteException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        for (int i = 0; i < clientsVector.size(); i++) {
            ClientInt checkChRef = clientsVector.get(i);
            System.out.println("clients ids" + clientsVector.get(i).getId());
            if (checkChRef.getId() == reciever.getId()) {
                checkChRef.receiveFile(client, reciever, sendFile, ext2);
                System.out.println("ana mawgood f om alclient vector ya3ny ana lya chatInt");
                //almfrod hgna hanroo7 n5znha f malf al xml aly b3d lma yft7 hay2rah
            } else {
                System.out.println("can't recieve dlw2ty");

            }

        }

    }

    @Override
    public void removeFromFriendList(User client, String id) throws RemoteException {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);

        } catch (SQLException ex) {
            //    Logger.getLogger(SignUpWinController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate("delete friend where id=" + client.getId() + " and friendID=" + Integer.parseInt(id) + "");
            stmt.executeUpdate("delete friend where friendID=" + client.getId() + " and id=" + Integer.parseInt(id) + "");
        } catch (SQLException ex) {
            Logger.getLogger(ChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void modeChanged(User client, String statues) throws RemoteException {

        List<User> friendUsers;
        friendUsers = friendList(client.getId());

        for (ClientInt clientRef : clientsVector) {
            try {
                for (User u : friendUsers) {
                    if (clientRef.getId() == u.getId()) {
//                        clientRef.receiveFromServer(client.getFName() + "   " + "is Offline now");

                        switch (statues) {
                            case "Away": {
//                                try {
                                clientRef.receiveFromServer(client.getFName() + "   " + "is Away now");
//                                    clientRef.modeChanged(client, "Away");
//                                } catch (RemoteException ex) {
//                                    Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
                            }
                            break;
                            case "Busy": {
//                                try {
                                clientRef.receiveFromServer(client.getFName() + "   " + "is Busy now");
//                                    client.modeChanged(client, "Busy");
//                                } catch (RemoteException ex) {
//                                    Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
                            }

                            break;

                            case "Offline": {
//                                try {
                                clientRef.receiveFromServer(client.getFName() + "   " + "is Offline now");
//                                    clientRef.modeChanged(client, "Offline");
//                                } catch (RemoteException ex) {
//                                    Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
                            }

                            break;
                        }
                    }
                    System.out.println("clinet reference id" + clientRef.getId());
                    System.out.println("id of user online kman " + u.getId());
                }

            } catch (RemoteException ex) {
                System.out.println("Can not send message to client");

            }
        }

    }

    @Override
    public void sendFile(String filename, User client, User reciever, RemoteInputStream data, String ext) throws RemoteException {
        System.out.println("in send file serverimp");
         List<User> friendUsers;
         friendUsers = friendList(client.getId());

            for (ClientInt clientRef : clientsVector) {
                try {
                    for (User u : friendUsers) {
                        if (u.getId() == reciever.getId()) {
                            clientRef.recieveFile(client, filename,data,ext);
                        }
                        System.out.println("clinet reference id" + clientRef.getId());
                        System.out.println("id of user online kman " + u.getId());
                    }

                } catch (RemoteException ex) {
                    System.out.println("Can not send message to client");

                }
            }

        }

    @Override
    public void closeAllOpenWindows(String text) throws RemoteException {
        id=0;     
        for (ClientInt clientRef : clientsVector) {
            try {
                clientRef.closeChatWindow(text);
            } catch (RemoteException ex) {
                System.out.println("Can not send message to client");

            }
        }

    }
        
        
        
    

}
//ClientInt reciever;
//        for (int i = 0; i < clientsVector.size(); i++) {
//            reciever = clientsVector.get(i);
//            if (reciever.getId() == idOfUser &&reciever.getId() == idofReciever) {
//                {
//                    reciever.receiveMe(clientName, text);
////                    if(lastSenderid!=reciever.getId())
////                    this.tellOnly(clientName, text, reciever.getId(),lastSenderid);
////                    reciever=lastSender.get(lastSender.size()-2);
////                    reciever.receive(clientName, text);
//                      System.out.println("im the reciever and the sender");
//                }
//            } else {
//                if (reciever.getId() == idOfUser) {
//                    reciever.receiveMe(clientName, text);
//                   
////                    lastSender.add(reciever);
//                    
//                    System.out.println(idOfUser+"id of user sender"+idofReciever+"id of reciever");
//
//                    //de lw ana asln alreciever w ana asln alsender 3shan tzhr 3ndy
//                } else {
//                    if ( reciever.getId()==idofReciever) {
//                        
//                        reciever.receiveOnly(clientName, text, idOfUser);
////                         if(idOfUser!=lastSenderid)
////                    lastSenderid=reciever.getId();
////                        lastSender.add(reciever);
////                                            System.out.println(idOfUser+"id of user sender"+idofReciever+"id of reciever");
//
////                                          System.out.println(idOfUser+"id of user sender"+idofReciever+"id of reciever");
//
//                                            //de btb3t mn ay user ll user almo3yen aly hna static hwh 39 
//
//                    }
//
//                }
//
////                          
//            }
//
//        }
