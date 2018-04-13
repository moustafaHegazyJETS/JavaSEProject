/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Registeration.Rigster;
import common.ChatServerInt;
import common.User;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Get;

public class FXMLDocumentController implements Initializable {

    @FXML
    Label rigster_btn;
    @FXML
    ImageView close_img;
    @FXML
    PieChart piech;
    @FXML
    AnchorPane tabanchor;
    @FXML
    AnchorPane tabanchor2;
    @FXML
    Tab tab1;
    @FXML
    Tab tab2;
    @FXML
    TabPane tab;
    @FXML
    TextField Server_txtf;
    @FXML
    Label online_Label;
    @FXML
    Label offline_Label;
    @FXML
    Label fname;
    @FXML
    Label username;
    @FXML
    Label gender;
    @FXML
    Label age;
    @FXML
    Label email;
    @FXML
    Label statues;
    @FXML
    TextField searchField;
    @FXML
    ToggleButton start;
    @FXML
    StackedBarChart chart;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    Registry reg;
    ChatServerInt chRef;
    ChatServerImpl obj;
    Get x = new Get();
    double xOffset;
    double yOffset;
    
    String theUrl = "rmi://10.0.0.52:29000/ChatService";

    private final ObservableList<PieChart.Data> details = FXCollections.observableArrayList();

    public void getStagePosition(MouseEvent evt) {
        xOffset = ((Node) (evt.getSource())).getScene().getWindow().getX() - evt.getScreenX();
        yOffset = ((Node) (evt.getSource())).getScene().getWindow().getY() - evt.getScreenY();
    }

    public void dragWindow(MouseEvent evt) {
        ((Node) (evt.getSource())).getScene().getWindow().setX(evt.getScreenX() + xOffset);
        ((Node) (evt.getSource())).getScene().getWindow().setY(evt.getScreenY() + yOffset);
    }

    public void exitBtnClicked(MouseEvent event) {
        System.out.println("exit clicked");
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    public void minimizeBtn(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.setIconified(true);
        System.out.println("minimize");
    }

    public ObservableList<PieChart.Data> generateData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("online", x.numberOfOnline()),
                new PieChart.Data("offline", x.numberOfOffline()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:

                        User find = x.getClientFromDatabase(searchField.getText());
                        if (find != null) {
                            username.setText(find.getUserName());
                            fname.setText(find.getFName());
                            age.setText(String.valueOf(find.getAge()));
                            email.setText(find.getEmail());
                            if (find.getGender() == 0) {
                                gender.setText("female");
                            } else {
                                gender.setText("male");
                            }
                            if (find.getStatus() == 0) {
                                statues.setText("offline");
                            } else {
                                statues.setText("online");
                            }
                        }

                }
            }
        });

        Thread loadFloatChart = new Thread(new Thread() {
            @Override
            public void run() {

                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            loadChartsPane();
                            chart2();
                            
                        }
                    });

                    try {
                        this.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        loadFloatChart.start();
        start.setStyle("-fx-base: green");
        try {
            ChatServerImpl obj = new ChatServerImpl();
          // System.setProperty("java.rmi.server.hostname","10.0.0.52");
            reg = LocateRegistry.createRegistry(29000);
        } catch (RemoteException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void setOnserverKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                System.out.println("you are now sedning msg to all ");
                 {
                    try {
                        try {
                            ////salmaaaaaaaaaaaaaa
                            chRef = (ChatServerInt) Naming.lookup(theUrl);
//                        chRef = (ChatServerInt) reg.lookup("ChatService");
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotBoundException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                 {
                    try {
                        chRef.servertellOthers(Server_txtf.getText());
                    } catch (RemoteException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Server_txtf.setText("");

        }

    }

    @FXML
    private void ToggleButtonAction(ActionEvent event) {

        try {
            obj = new ChatServerImpl();
            if (start.isSelected()) {

                start.setText("Stop");
                start.setStyle("-fx-base: red");
                System.out.println("server start  ");
                try {
                    try {
                        //// salmaaaaaaaaa
                        Naming.rebind(theUrl, obj);
//                    reg.rebind("ChatService", obj);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (RemoteException ex) {
                    System.out.println("rmi problem server was started ");
                }

            } else {
                start.setText("Start");
                start.setStyle("-fx-base:green");
                System.out.println("service is unbind ");

                try {
                    try {
                        try {
                            //// close all open windows and also make id is 0
                            //// salmaaaaaaa
                            chRef = (ChatServerInt) Naming.lookup(theUrl);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        chRef = (ChatServerInt) reg.lookup("ChatService");
                        chRef.closeAllOpenWindows("server is closed");
                        
                        try {
                            //// salmaaaaaaa
                            Naming.unbind(theUrl);
//                        reg.unbind("ChatService");
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                       

                        
                        
                    } catch (RemoteException ex) {
                        System.out.println("unbind problem");
                    }

                } catch (NotBoundException ex) {
                    System.out.println("Service not found you may truned it off ");
                }

            }
        } catch (RemoteException ex) {
            System.out.println("rmi invoked by non_localhost");
        }
    }

    private void loadChartsPane() {

        piech = new PieChart(generateData());
        piech.setClockwise(false);
        online_Label.setText(String.valueOf(x.numberOfOnline()));
        offline_Label.setText(String.valueOf(x.numberOfOffline()));
        piech.setPrefSize(250, 250);
        tabanchor.setTopAnchor(piech, 10.0);
        tabanchor.setRightAnchor(piech, 10.0);
        tabanchor.setBottomAnchor(piech, 10.0);
        tabanchor.setLeftAnchor(piech, 10.0);
        tabanchor.getChildren().add(piech);
    }

    public void chart2() {
        String[] ratio = {"male", "female"};
        xAxis = new CategoryAxis(observableArrayList(ratio));
        yAxis = new NumberAxis("gender", 0.0d, 50.0d, 100.0d);

        ObservableList<StackedBarChart.Series> barChartData
                = observableArrayList(
                        new StackedBarChart.Series("male",
                                observableArrayList(
                                        new StackedBarChart.Data(ratio[0], (double) x.getGenderMale())
                                )
                        ),
                        new StackedBarChart.Series("female",
                                observableArrayList(
                                        new StackedBarChart.Data(ratio[1], (double) x.getGenderFemale())
                                )
                        )
                );

        chart = new StackedBarChart(xAxis, yAxis, barChartData, 25.0d);
        tabanchor2.setTopAnchor(chart, 10.0);
        tabanchor2.setRightAnchor(chart, 10.0);
        tabanchor2.setBottomAnchor(chart, 10.0);
        tabanchor2.setLeftAnchor(chart, 10.0);
        tabanchor2.getChildren().add(chart);

    }

    @FXML
    private void new_user() {
        System.out.println(" server adding new user");
        Rigster reg = new Rigster();
        try {
            reg.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
