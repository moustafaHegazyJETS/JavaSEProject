/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendList;

import clientfx.ChatWindow;
//salmaaaaaaaaaaaaaa
import static clientfx.ChatWindow.sendMessage;
//import static clientfx.ChatWindow.recieveMessage;
import clientfx.ClientImpl;
import clientfx.FXMLDocumentController;
import clientfx.loginWindow.LoginWindow;
import clientfx.loginWindow.LoginWindowFXMLController;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.sun.javafx.scene.control.skin.VirtualScrollBar;
import common.ChatServerInt;
import common.Message;
import common.User;
import java.io.ByteArrayInputStream;
//import java.io.BufferedWriter;
//import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;
import org.controlsfx.control.Notifications;
import searchFriend.SearchFriendController;
import org.apache.commons.io.FilenameUtils;

/**
 * FXML Controller class
 *
 * @author TECHNOLOGY CITY
 */
public class FriendListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ClientImpl currentClient;
    @FXML
    TextField search;
    @FXML
    VBox friendsList;
    @FXML
    VBox vb;
    @FXML
    TabPane mainTabPane;
    @FXML
    TextField searchFriend;
    @FXML
    TextField searchClient;
    @FXML
    VBox friendList;
    @FXML
    Label myName;
    @FXML
    TabPane chatWindow;
    @FXML
    TextField messageTextField;
    @FXML
    Button upload;
    @FXML
    private ComboBox<String> Mode;
    ObservableList<String> modeStatues = FXCollections.observableArrayList("Away", "Busy", "Offline");

    @FXML
    ImageView state;

    @FXML
    ColorPicker color;

    @FXML
    private ComboBox<Integer> fontSize;
    ObservableList<Integer> size = FXCollections.observableArrayList(5, 10, 15, 20, 25, 30, 40);
    @FXML
    private ComboBox<String> fontfamily;
    ObservableList<String> fontType = FXCollections.observableArrayList("Arial Black", "Impact", "arial", "Georgia");

    static boolean mymessage = true;
    static boolean hismessage = true;
    String modeType = null;
    Boolean clientChatWinOpened = true;
    User client;
    ChatServerInt chRef;
    public List<User> friendUsers;
    public List<User> tempFriendUsers;
    Message m;
    public List<Tab> openedTabs;
//    TextField messageTextField;
    User reciever;
    BorderPane BP;
    TextArea t;
    int saf7etalschatal2ola;
    int senderID;
    TextArea wholeMessage;
    File file;
    ArrayList<HBox> hboxArray = new ArrayList<>();
    static HashMap<Integer, Tab> friendTabs;
    int txtSize = 0;
    String font = null;
    Stage stage;
    private byte[] fileBytes;

    public FriendListController(User _client, ChatServerInt _chRef, Stage s) {
        friendTabs = new HashMap<>();
        client = _client;
        chRef = _chRef;
        stage = s;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fontSize.setItems(size);
        fontfamily.setItems(fontType);
        //set mode 
        Mode.setItems(modeStatues);

        //creating component
        messageTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        System.out.println("You clicked me! -- in first key pressed");
                        try {
                            System.out.println("You clicked msasasasasae!");
                            Message msg = new Message();
                            msg.setColor(toRgbString(color.getValue())); // change from ui 
                            if (fontSize.getValue() == null) {
                                msg.setSize(20);
                            } else {
                                msg.setSize(fontSize.getValue());
                            }
                            if (fontfamily.getValue() == null) {
                                msg.setFont("Arial Black");
                            } else {
                                msg.setFont(fontfamily.getValue()); // change from ui
                            }

                            msg.setText(messageTextField.getText());
                            msg.setSender(client);
                            msg.setReciever(reciever);
                            if (client == null || reciever == null) {
                                System.out.println("you should specify receiver open tab");
                            } else {
                                chRef.tellOnly(client, msg, reciever);

                                VBox vb = (VBox) friendTabs.get(reciever.getId()).getContent();
                                Label msgg = new Label(msg.getText());
                                msgg.setFont(Font.font(msg.getFont()));

                                msgg.setStyle("-fx-background-color:#ffff99;-fx-font-size:" + msg.getSize() + ";-fx-text-fill:" + msg.getColor() + ";-fx-border-radius: 15 15 15 15; -fx-background-radius: 10 10 10 10;-fx-padding:5 5 5 5;");
                                HBox hBox = new HBox();

                                hBox.getChildren().add(msgg);
                                hBox.setAlignment(Pos.CENTER_LEFT);

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                        vb.getChildren().add(hBox);
                                        vb.setSpacing(10);
                                        messageTextField.setText("");

                                    }
                                });

                                //client dh ana ,reciver dh hwh 
                            }
                        } catch (RemoteException ex) {
                            System.out.println("message error");
                        }

                }

            }
        });

        ////////
        myName.setText(client.getFName());
        state.setImage(new Image(getClass().getResourceAsStream("/images/available.png")));

//        state.setImage(new Image(getClass().getResourceAsStream("/images/available.png")));
        // to be online
        try {
            currentClient = new ClientImpl(this);
            //la7z gwa al login state de ana hazwed any lma ab2h online kman hay2ool llkol alfriend aly 3andy 
            chRef.setLoginStatus(client, currentClient);

            //
        } catch (RemoteException ex) {
            Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
        }

////// dh aly bymla friend list
//////////////////////////////
//
        /////hna kman b3ml al ist of tabs//////////////////////////
        try {
            friendUsers = chRef.friendList(client.getId());
            for (User friendUser : friendUsers) {
//                TextArea t = new TextArea();
//                ScrollBar sc = new  ScrollBar();
                VBox tabVb = new VBox();
//                                sc.getChildrenUnmodifiable().add(tabVb);

//                VirtualScrollBar vsb=new VirtualScrollBar(new VirtualFlow());
                Tab tab = new Tab(friendUser.getFName(), tabVb);
                tab.setOnSelectionChanged((event) -> {
                    reciever = friendUser;
                });

                friendTabs.put(friendUser.getId(), tab);
            }
            tempFriendUsers = new ArrayList<>(friendUsers);
            if (friendUsers == null) {
                System.out.println("you have no friend");

            } else {

                for (int i = 0; i < friendUsers.size(); i++) {
                    User u = (User) friendUsers.get(i);
                    String friendID = String.valueOf(u.getId());
                    Button chatWith;
                    Button delete;
                    System.out.println("chatWith" + " " + u.getFName());

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

                    chatWith = new Button("startCaht");
                    delete = new Button("Delete");
                    chatWith.setId(friendID);
                    delete.setId(friendID);

                    chatWith.setOnAction((ActionEvent event) -> {

//                        if (tempFriendUsers.contains(u)) {
                        reciever = u;
                        if (chatWindow.getTabs().contains(friendTabs.get(Integer.parseInt(chatWith.getId())))) {
                            System.out.println("already opened");
                        } else {
                            chatWindow.getTabs().add(friendTabs.get(Integer.parseInt(chatWith.getId())));
                        }

                    });

                    HBox hBox = new HBox();
                    hBox.getChildren().add(delete);
                    hBox.getChildren().add(chatWith);
                    hBox.getChildren().add(l);
                    hBox.getChildren().add(senderImage);
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setId(friendID);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            friendList.getChildren().add(hBox);
                            friendList.setSpacing(10);

                        }
                    });
                    delete.setOnAction((event) -> {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                friendList.getChildren().remove(hBox);
                                friendList.setSpacing(10);

                            }
                        });
                        try {
                            chRef.removeFromFriendList(client, delete.getId());
                        } catch (RemoteException ex) {
                            Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                }
            }

        } catch (RemoteException ex) {
            Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////// friend request
        Thread recieveRequest;
        recieveRequest = new Thread(new Thread() {
            @Override
            public void run() {

                while (true) {
                    try {

                        ArrayList resultOfSearch = chRef.getFriendRequest(client.getId());
                        for (int i = 0; i < resultOfSearch.size(); i++) {
                            User u = (User) resultOfSearch.get(i);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    createFriendNotification(u.getFName(), u.getId(), u);
                                }
                            });
                        }

                    } catch (RemoteException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        this.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            private void createFriendNotification(String userName, int userID, User u) {
                //idOfUser dh al id bta3 aluser aly sha8al dlw2ty 
                //userid dh al id bta3 aly ba3et alrequest
                Label l = new Label();
                l.setText(userName);
                Button confirm = new Button("Confirm");
                confirm.setId(String.valueOf(userID));
                Button ignore = new Button("ignore");
                HBox hBox = new HBox();
                hBox.getChildren().add(l);
                hBox.getChildren().add(confirm);
                hBox.getChildren().add(ignore);

                hBox.setAlignment(Pos.CENTER_RIGHT);
                System.out.println(userName + " " + userID + " " + confirm.getId());
                boolean exist = false;
                for (int i = 0; i < vb.getChildren().size() && !exist; i++) {
                    HBox temp = (HBox) vb.getChildren().get(i);
                    if (temp != null) {
                        Button node = (Button) temp.getChildren().get(1);
                        if (node.getId().equalsIgnoreCase(String.valueOf(userID))) {
                            exist = true;
                        }
                    }
                }
                if (!exist) {
                    vb.getChildren().add(hBox);
                }
                confirm.setOnAction((event) -> {

                    try {
                        chRef.confirmFriendRequest(client.getId(), userID);//user id dh bta3 aly ba3et al add asln
                        Alert a=new Alert(Alert.AlertType.INFORMATION,"you need to reopen window to chat with friend ");
                        a.showAndWait();

                        vb.getChildren().remove(hBox);
                        VBox tabVb = new VBox();
//                VirtualScrollBar vsb=new VirtualScrollBar(new VirtualFlow());

                        Tab tab = new Tab(userName, tabVb);
                        tab.setOnSelectionChanged((eventt) -> {
                            reciever = u;
                        });

                        friendTabs.put(u.getId(), tab);

                        String friendID = String.valueOf(u.getId());
                        Button chatWith;
                        Button delete;
                        System.out.println("chatWith" + " " + u.getFName());

                        Label ll = new Label();
                        ll.setText(u.getFName());
                        ll.setStyle("-fx-background-color:#99e6ff;-fx-border-radius: 15 15 15 15; -fx-background-radius: 10 10 10 10;-fx-padding:5 5 5 5;");
                        ImageView senderImage = new ImageView();
                        senderImage.setImage(new Image(getClass().getResourceAsStream("/images/images.jpg")));
                        senderImage.setFitWidth(20);
                        senderImage.setFitHeight(20);
                        Rectangle rect = new Rectangle(senderImage.getFitWidth(), senderImage.getFitHeight());

                        rect.setArcWidth(20);
                        rect.setArcHeight(20);
                        senderImage.setClip(rect);

                        chatWith = new Button("startCaht");
                        delete = new Button("Delete");
                        chatWith.setId(friendID);
                        delete.setId(friendID);

                        chatWith.setOnAction((ActionEvent eventee) -> {

//                        if (tempFriendUsers.contains(u)) {
                            reciever = u;
                            if (chatWindow.getTabs().contains(friendTabs.get(Integer.parseInt(chatWith.getId())))) {
                                System.out.println("already opened");
                            } else {
                                chatWindow.getTabs().add(friendTabs.get(Integer.parseInt(chatWith.getId())));
                            }

                        });

                        HBox hBoxa = new HBox();
                        hBoxa.getChildren().add(delete);
                        hBoxa.getChildren().add(chatWith);
                        hBoxa.getChildren().add(l);
                        hBoxa.getChildren().add(senderImage);
                        hBoxa.setAlignment(Pos.CENTER_RIGHT);
                        hBoxa.setId(friendID);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                friendList.getChildren().add(hBoxa);
                                friendList.setSpacing(10);

                            }
                        });
                        delete.setOnAction((eventee) -> {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    friendList.getChildren().remove(hBox);
                                    friendList.setSpacing(10);

                                }
                            });
                            try {
                                chRef.removeFromFriendList(client, delete.getId());
                            } catch (RemoteException ex) {
                                Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                    } catch (RemoteException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

                ignore.setOnAction((event) -> {

                    try {
                        chRef.ignoreRequest(client.getId(), userID);
                        vb.getChildren().remove(hBox);
                    } catch (RemoteException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

            }

        });

        recieveRequest.start();

        upload.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                file = fileChooser.showOpenDialog(new Stage());

                if (file != null) {
                    try {
                        String path = file.getCanonicalPath();
                        path = path.replace('\\', '/');
                        String ext = FilenameUtils.getExtension(path);
                        fileBytes = new byte[(int) file.length()];
                        fileBytes = Files.readAllBytes(file.toPath());
                        ByteArrayInputStream fileStream = new ByteArrayInputStream(fileBytes);
                        RemoteInputStreamServer data = new SimpleRemoteInputStream(fileStream);
                        if (reciever == null) {
                            System.out.println("you must choose receiver");
                        } else {

                            System.out.println("befor");
                            try {
                                chRef.sendFile(file.getName(), client, reciever, data.export(), ext);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            System.out.println("after");
                        }
                    } catch (IOException ex) {
                        System.out.println("problem in sending file");
                    }
                }

            }
        });
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

                resultOfSearch = chRef.selectClientsFromDatabase(client.getId(), search.getText()); // de aly hatrg3ly resultset feha asamy alusers
                //aly ana 3amel behom search 

                for (int i = 0; i < resultOfSearch.size(); i++) {
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
                            status = chRef.getStatus(addFriendbtn.getId());
                            System.out.println("Offline");
                            chRef.addIntoFriendRequestTable(client.getId(), Integer.parseInt(addFriendbtn.getId()));

                        } catch (RemoteException ex) {
                            Logger.getLogger(SearchFriendController.class
                                    .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SearchFriendController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("enter value valied"); // dh lw alsearch fady 
        }

    }

    public void addClosedChat(User closeChatUser) {
        try {
            tempFriendUsers.add(closeChatUser);
        } catch (Exception e) {
        }

    }

    public void display(User _client, User _reciever, Message msg) {

        System.out.println("from" + "   " + _client.getFName() + "    " + msg.getText());
        System.out.println("to " + "     " + _reciever.getId() + "     " + _reciever.getFName());
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Notifications.create()
                        .title(_reciever.getFName()+", you have received message from  "+_client.getFName())
                        .text(msg.getText())
                        .hideAfter(Duration.seconds(2))
                        .darkStyle()
                        .graphic(null)//new ImageView(n)
                        .position(Pos.BOTTOM_RIGHT).show();
            }
        });
        
        
        
        
        
//        TextArea textArea5 = new TextArea();
        if (client.getId() == _client.getId()) {
            VBox v = (VBox) friendTabs.get(_reciever.getId()).getContent();
            Label msgg = new Label(msg.getText());
            msgg.setFont(Font.font(msg.getFont()));

            msgg.setStyle("-fx-background-color:#ff9999;-fx-text-fill:" + msg.getColor() + ";-fx-border-radius: 15 15 15 15; -fx-background-radius: 10 10 10 10;-fx-padding:5 5 5 5;");
            HBox hBox = new HBox();

            hBox.getChildren().add(msgg);
            hBox.setAlignment(Pos.CENTER_RIGHT);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    v.getChildren().add(hBox);
                    v.setSpacing(10);

                }
            });

        } else {

            Platform.runLater(() -> {

                hboxArray.clear();
                VBox v = (VBox) friendTabs.get(_client.getId()).getContent();

                Label msgg = new Label(msg.getText());

                msgg.setFont(Font.font(msg.getFont()));

                msgg.setStyle("-fx-background-color:#ff9999;-fx-font-size:" + msg.getSize() + ";-fx-text-fill:" + msg.getColor() + ";-fx-border-radius: 15 15 15 15; -fx-background-radius: 10 10 10 10;-fx-padding:5 5 5 5;");
                HBox hBox = new HBox();

//                msgg.setStyle("-fx-background-color:#ff9999;-fx-border-radius: 15 15 15 15; -fx-background-radius: 10 10 10 10;-fx-padding:5 5 5 5;");
                hBox.getChildren().add(msgg);
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hboxArray.add(hBox);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        v.getChildren().add(hBox);
                        v.setSpacing(10);

                    }
                });

//                TextArea textArea = (TextArea) friendTabs.get(_client.getId()).getContent();
//                textArea.appendText(msg.getText());
            });

        }
    }

    //////////////////////////////////////////////////
    public void displayFromServer(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Notifications.create()
                        .title("Message from server")
                        .text(msg)
                        .hideAfter(Duration.seconds(5))
                        .darkStyle()
                        .graphic(null)//new ImageView(n)
                        .position(Pos.BOTTOM_RIGHT).show();
            }
        });
    }

    @FXML
    public void upload(ActionEvent event) throws IOException {

    }

    static InputStream sendFile(String path) throws FileNotFoundException {
        System.out.println(path);
        File file = new File(path);

        // byte[] dataArray = new byte[(int) file.length()];
        InputStream in = new FileInputStream(file);
//            in.read(dataArray);

        return in;
    }

    public void download(User _client, User _reciever, InputStream in, String ext) {
        int input = JOptionPane.showOptionDialog(null, "you will recieve an file from external device", "The title", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if (input == JOptionPane.OK_OPTION) {

            String name = JOptionPane.showInputDialog("please enter file name");

            File f = new File(".\\conversation" + name + "." + ext);
            FileOutputStream out;
            try {
                int readByte = 0;

                int size =1024*1024;
                byte[] data = new byte[size];
                out = new FileOutputStream(f);
                do {
                    readByte = in.read(data);
                    if (readByte != -1) {
                        out.write(data);
                    }
                } while (readByte != -1);
            } catch (FileNotFoundException ex) {
                System.out.println("file not found");
            } catch (IOException ex) {
                System.out.println("io Exception");
            }
        } else if (input == JOptionPane.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "not allow");
        }

    }

    @FXML
    public void statuesChange(ActionEvent event) {
        modeType = Mode.getValue();
        if (modeType != null) {
            switch (modeType) {
                case "Away": {
                    try {
                        chRef.modeChanged(client, "Away");
//                        state.setImage(new Image("away.png"));
                        Platform.runLater(() -> {
                            state.setImage(new Image(getClass().getResourceAsStream("/images/away.png")));

                        });

                    } catch (RemoteException ex) {
                        Logger.getLogger(FriendListController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "Busy": {
                    try {
                        chRef.modeChanged(client, "Busy");
                        Platform.runLater(() -> {
                            state.setImage(new Image(getClass().getResourceAsStream("/images/Red_X.png")));

                        });

//                        state.setImage(new Image("busy.png"));
                    } catch (RemoteException ex) {
                        Logger.getLogger(FriendListController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;

                case "Offline": {
                    try {
                        chRef.modeChanged(client, "Offline");
                        Platform.runLater(() -> {
                            state.setImage(new Image(getClass().getResourceAsStream("/images/offline.png")));

                        });

//                    state.setImage(new Image("offline.png"));
                    } catch (RemoteException ex) {
                        Logger.getLogger(FriendListController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;
            }
        }

    }

    public void signOut() throws RemoteException {

//       ((Node) (event.getSource())).getScene().getWindow().hide();
        Platform.runLater(() -> {
            stage.close();
        });

        chRef.goOffline(client);
        LoginWindow lw = new LoginWindow();
        lw.openWindow();

    }

    @FXML
    public void setColorPic(ActionEvent event) {
        if (font != null && txtSize != 0) {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";" + "-fx-font-family: " + fontfamily.getValue() + ";" + " -fx-font-size:" + fontSize.getValue() + ";");
        } else if (font != null) {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";" + "-fx-font-family: " + fontfamily.getValue() + ";");
        } else if (txtSize != 0) {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";" + " -fx-font-size:" + fontSize.getValue() + ";");

        } else {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";");

        }
    }

    @FXML
    public void setSize(ActionEvent event) {
        if (font != null && txtSize != 0) {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";" + "-fx-font-family: " + fontfamily.getValue() + ";" + " -fx-font-size:" + fontSize.getValue() + ";");
        } else if (font != null) {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";" + "-fx-font-family: " + fontfamily.getValue() + ";");
        } else if (txtSize != 0) {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";" + " -fx-font-size:" + fontSize.getValue() + ";");

        } else {
            messageTextField.setStyle("-fx-font-size: " + fontSize.getValue() + " ;");
        }
        txtSize = fontSize.getValue();
    }

    @FXML
    public void setFamily(ActionEvent event) {
        if (font != null && txtSize != 0) {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";" + "-fx-font-family: " + fontfamily.getValue() + ";" + " -fx-font-size:" + fontSize.getValue() + ";");
        } else if (font != null) {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";" + "-fx-font-family: " + fontfamily.getValue() + ";");
        } else if (txtSize != 0) {
            messageTextField.setStyle("-fx-text-fill: " + toRgbString(color.getValue()) + ";" + " -fx-font-size:" + fontSize.getValue() + ";");

        } else {
            messageTextField.setStyle("-fx-font-family: " + fontfamily.getValue() + " ;");
        }
        font = fontfamily.getValue();
    }

    private String toRgbString(Color c) {
        return "rgb("
                + to255Int(c.getRed())
                + "," + to255Int(c.getGreen())
                + "," + to255Int(c.getBlue())
                + ")";
    }

    private int to255Int(double d) {
        return (int) (d * 255);
    }

    public void recieveFile(User client, String filename, RemoteInputStream data, String ext) {
//        BufferedWriter bw = null;
        System.out.println("here");
//        if (ext.toLowerCase().equals("mp4")) {
        Platform.runLater(() -> {
            try {
                InputStream stream = RemoteInputStreamClient.wrap(data);
                FileOutputStream output = new FileOutputStream("D:\\" + filename);
                int chunk = 1024*1024;
                byte[] result = new byte[chunk];
                int readBytes = 0;
                do {
                    readBytes = stream.read(result);
                    if (readBytes != -1) {
                        output.write(result, 0, readBytes);

                    }
                } while (readBytes != -1);
                String st = "you hav file from" + client.getFName() + " and it saved at D:\\";
                Alert alt = new Alert(Alert.AlertType.INFORMATION, st);
                alt.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }

        });

    }

    public void closeFromServer(String text) {
        Platform.runLater(() -> {
            try {
                chRef.goOffline(client);
            } catch (RemoteException ex) {
                Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
            }
             Alert a = new Alert(Alert.AlertType.ERROR,"server is now shut down so Please Login Later");
        a.showAndWait();
        Platform.exit();
        
        });
       
        
        
    }
}
// ana momken a3ml thread by3ml check 3ala aldatabase lw 7ad b2h online aw lw friend b2h online y3mly al announcement w kman ydeefo w 8ayer shaklo 3andy 
