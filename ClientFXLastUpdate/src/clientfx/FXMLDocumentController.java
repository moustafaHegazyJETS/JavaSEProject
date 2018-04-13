/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientfx;

import SignUpWindow.SignUpWinController;
import common.ChatServerInt;
import common.Message;
import common.User;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import searchFriend.SearchFriend;

public class FXMLDocumentController implements Initializable {

    int id;

    @FXML
    private Label label;
    @FXML
    TextField text0;
    @FXML
    VBox vb;
    @FXML
    ImageView img;
    @FXML
    ImageView minimize;

    @FXML
    ImageView close;

    @FXML
    ImageView smile;

    @FXML
    ImageView crazy;

    @FXML
    ImageView love;

    @FXML
    ImageView gamza;

    @FXML
    Label username;
    @FXML
    Label office;
    @FXML
    ImageView imgLike;

    @FXML
    ImageView right;
    @FXML
    ImageView fist;
    @FXML
    ImageView raise;

    @FXML
    ImageView imgDisLike;
    String name;

    @FXML
    ScrollPane sc;
    @FXML
    Button addFriendBtn;

    static boolean mymessage = true;
    static boolean hismessage = true;
    ClientImpl currentClient;
    private double xOffset = 0;
    private double yOffset = 0;
    ArrayList<HBox> hboxArray = new ArrayList<>();
    int idOfUser;
    ArrayList<String> friendRequestsArray = new ArrayList<>();
    int friendArrayLengthBefore = 0;
    int friendArrayLengthAfter = 0;
    int countFriendRequestsOld = 0;
    int countFriendRequestsNew = 0;
    ChatServerInt chRef;
    User client;
    User reciever;

    Message msg;
    static int idOfPage;

    FXMLDocumentController(User _client, User _reciever, ChatServerInt _chRef, Message _msg, int _idOfPage) {
        client = _client;
        name = _reciever.getFName();
        chRef = _chRef;
        idOfUser = client.getId();
        reciever = _reciever;

        msg = _msg;
        idOfPage = _idOfPage;
    }

    FXMLDocumentController(User _client, User _reciever, ChatServerInt _chRef) {
        client = _client;
        name = _reciever.getFName();
        chRef = _chRef;
        idOfUser = client.getId();
        reciever = _reciever;

    }

    public static int returnPageNumber(User spacificPageToUser) {

        return idOfPage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        friendRequestsArray = new ArrayList<>();
        try {

            sc.setVvalue(1.0);

            img.setImage(new Image(getClass().getResourceAsStream("/images/images.jpg")));
            img.setFitWidth(60);
            img.setFitHeight(60);

            Rectangle rect = new Rectangle(img.getFitWidth(), img.getFitHeight());

            rect.setArcWidth(60);
            rect.setArcHeight(60);
            img.setClip(rect);

            minimize.setImage(new Image(getClass().getResourceAsStream("/images/icons8-minimize-window-50.png")));

            close.setImage(new Image(getClass().getResourceAsStream("/images/icons8-cancel-50.png")));

            username.setText(name);
            office.setText("Office Boy");
            imgLike.setImage(new Image(getClass().getResourceAsStream("/images/facebook-like-hand-symbol-outline.png")));
            imgLike.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    text0.appendText(" 👍 ");

                }
            });
            imgDisLike.setImage(new Image(getClass().getResourceAsStream("/images/dislike-thumb.png")));
            imgDisLike.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    text0.appendText(" 👎 ");

                }
            });

            crazy.setImage(new Image(getClass().getResourceAsStream("/images/crazy_smile.png")));

            crazy.setFitHeight(28);
            crazy.setFitWidth(28);

            crazy.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    text0.appendText(" 😄");

                }
            });
            smile.setImage(new Image(getClass().getResourceAsStream("/images/smile.png")));
            smile.setFitHeight(28);
            smile.setFitWidth(28);

            smile.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    text0.appendText(" 😊");

                }
            });

            love.setImage(new Image(getClass().getResourceAsStream("/images/loveEmoji1.png")));
            love.setFitHeight(28);
            love.setFitWidth(28);

            love.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    text0.appendText(" 😍");

                }
            });

            gamza.setImage(new Image(getClass().getResourceAsStream("/images/8amza.png")));
            gamza.setFitHeight(28);
            gamza.setFitWidth(28);

            gamza.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    text0.appendText(" 😏");

                }
            });

            right.setImage(new Image(getClass().getResourceAsStream("/images/right.png")));
            right.setFitHeight(28);
            right.setFitWidth(28);

            right.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    text0.appendText(" 👉");

                }
            });

            fist.setImage(new Image(getClass().getResourceAsStream("/images/fist.png")));
            fist.setFitHeight(28);
            fist.setFitWidth(28);

            fist.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    text0.appendText(" 👊");

                }
            });

            raise.setImage(new Image(getClass().getResourceAsStream("/images/raise.png")));
            raise.setFitHeight(28);
            raise.setFitWidth(28);

            raise.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    text0.appendText(" ✋");

                }
            });

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    public void display(User _client, User _reciever, Message msg) {
        System.out.println(_client.getFName() + "    " + msg.getText());
        if (hismessage) {
            hboxArray.clear();
            Label l = new Label();
            l.setText(_client.getFName());
            HBox hBox = new HBox();
            hBox.getChildren().add(l);
            hBox.setAlignment(Pos.CENTER_RIGHT);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    vb.getChildren().add(hBox);

                }
            });

        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (vb.getChildren().size() > 0) {
                    HBox b = (HBox) vb.getChildren().get(vb.getChildren().size() - 1);
                    try {
                        if (b.getChildren().get(1) instanceof ImageView) {
                            b.getChildren().remove(1);
                        }
                    } catch (Exception ex) {
                        System.out.println("first mssage");
                    }
                }

                ImageView senderImage = new ImageView();
                senderImage.setImage(new Image(getClass().getResourceAsStream("/images/images.jpg")));
                senderImage.setFitWidth(20);
                senderImage.setFitHeight(20);
                Rectangle rect = new Rectangle(senderImage.getFitWidth(), senderImage.getFitHeight());

                rect.setArcWidth(20);
                rect.setArcHeight(20);
                senderImage.setClip(rect);

                Label l = new Label();
                l.setText(msg.getText());

                l.setStyle("-fx-background-color:#ff9999;-fx-border-radius: 15 15 15 15; -fx-background-radius: 10 10 10 10;-fx-padding:5 5 5 5;");
                HBox hBox = new HBox();
                hBox.getChildren().add(l);
                hBox.getChildren().add(senderImage);

                hBox.setStyle("-fx-padding: 0 0 0 15;");

                hBox.setAlignment(Pos.CENTER_RIGHT);
                hboxArray.add(hBox);

                if (hboxArray.size() == 1) {
                    System.out.println("one message");

                }

                if (hboxArray.size() == 2) {
                    System.out.println("2 message");
                    hboxArray.get(0).getChildren().get(0).setStyle("-fx-background-color:#ff9999;-fx-border-radius: 15 15 5 15; -fx-background-radius: 10 10 2 10;-fx-padding:5 5 5 5;");
                    hboxArray.get(1).getChildren().get(0).setStyle("-fx-background-color:#ff9999;-fx-border-radius: 15 5 15 15; -fx-background-radius: 10 2 10 10;-fx-padding:5 5 5 5;");
                }

                if (hboxArray.size() > 2) {
                    System.out.println("4 message");
                    hboxArray.get(hboxArray.size() - 2).getChildren().get(0).setStyle("-fx-background-color:#ff9999;-fx-border-radius: 15 5 5 15; -fx-background-radius: 10 2 2 10;-fx-padding:5 5 5 5;");
                    hboxArray.get(hboxArray.size() - 1).getChildren().get(0).setStyle("-fx-background-color:#ff9999;-fx-border-radius: 15 5 15 15; -fx-background-radius: 10 2 10 10;-fx-padding:5 5 5 5;");
                }

                vb.getChildren().add(hBox);

            }
        });
        hismessage = false;
        mymessage = true;

    }

    public void displayMe(User _client, User _receiver, Message msg) {

        if (mymessage) {
            hboxArray.clear();
            Label l = new Label();
            l.setText(_client.getFName());
            HBox hBox = new HBox();
            hBox.getChildren().add(l);
            hBox.setAlignment(Pos.CENTER_LEFT);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    vb.getChildren().add(hBox);
                    vb.setSpacing(10);

                }
            });

        }

        Label l = new Label();
        l.setText(msg.getText());
        l.setStyle("-fx-background-color:#FFEB94;-fx-border-radius: 15 15 15 15; -fx-background-radius: 10 10 10 10;-fx-padding:5 5 5 5;");
        HBox hBox = new HBox();
        hBox.getChildren().add(l);
        hBox.setAlignment(Pos.CENTER_LEFT);

        hboxArray.add(hBox);

        if (hboxArray.size() == 1) {
            System.out.println("one message");

        }
        if (hboxArray.size() == 2) {
            System.out.println("2 message");
            hboxArray.get(0).getChildren().get(0).setStyle("-fx-background-color:#FFEB94;-fx-border-radius: 15 15 15 5; -fx-background-radius: 10 10 10 1;-fx-padding:5 5 5 5;");
            hboxArray.get(1).getChildren().get(0).setStyle("-fx-background-color:#FFEB94;-fx-border-radius: 5 15 15 15; -fx-background-radius: 1 10 10 10;-fx-padding:5 5 5 5;");
        }
        if (hboxArray.size() > 2) {
            System.out.println("4 message");
            hboxArray.get(hboxArray.size() - 2).getChildren().get(0).setStyle("-fx-background-color:#FFEB94;-fx-border-radius: 5 15 15 5; -fx-background-radius: 1 10 10 1;-fx-padding:5 5 5 5;");
            hboxArray.get(hboxArray.size() - 1).getChildren().get(0).setStyle("-fx-background-color:#FFEB94;-fx-border-radius: 5 15 15 15; -fx-background-radius: 1 10 10 10;-fx-padding:5 5 5 5;");
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vb.getChildren().add(hBox);
                vb.setSpacing(5);
            }
        });
        mymessage = false;
        hismessage = true;

    }

    @FXML
    private void KeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                System.out.println("You clicked me!");
                try {
                    Message msg = new Message();
                    msg.setColor("#fff000"); // change from ui 
                    msg.setFont("Verdana"); // change from ui
                    msg.setText(text0.getText());
                    chRef.tellOnly(client, msg, reciever);
                    text0.setText("");
                } catch (RemoteException ex) {

                }

        }

    }

    public void getStagePosition(MouseEvent evt) {
        xOffset = ((Node) (evt.getSource())).getScene().getWindow().getX() - evt.getScreenX();
        yOffset = ((Node) (evt.getSource())).getScene().getWindow().getY() - evt.getScreenY();
    }

    public void dragWindow(MouseEvent evt) {
        ((Node) (evt.getSource())).getScene().getWindow().setX(evt.getScreenX() + xOffset);
        ((Node) (evt.getSource())).getScene().getWindow().setY(evt.getScreenY() + yOffset);
    }

    public void minimizeBtn(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.setIconified(true);
        System.out.println("minimize");
    }

    public void addFriend(MouseEvent event) {
        try {
            SearchFriend searchFriendStage = new SearchFriend(client.getId(), chRef);

            searchFriendStage.openAddPage();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
