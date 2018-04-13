/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import chatschema.*;
import common.ClientInt;
import common.Message;
import common.User;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author salma hasan
 */
public class SaveChatSession {

    JAXBElement JAXBchat;

    static ArrayList<MsgType> messageList = new ArrayList<>();
    //static Vector<ClientInt> clients;
    User receiverClient;
    Message message;
    User senderClient;
    JAXBContext jaxbContext;
    File file;
    //Schema schema;

    public SaveChatSession(User _receiver, Message msg, User _client) {
        receiverClient = _receiver;
        message = msg;
        senderClient = _client;
    }

    public void write() throws JAXBException {

        System.out.println("=============================================");

        //create file name
        String fileName = "";
        fileName += senderClient.getUserName() + "_" + receiverClient.getUserName();
        System.out.println("file name is " + fileName);
        file = new File(".\\converstaion\\", fileName + ".xml");
//-------------------------
        try {
            jaxbContext = JAXBContext.newInstance("chatschema");
        } catch (JAXBException ex) {
            System.out.println("can't create jaxbContext ");
        }
        
        if (!fileName.equalsIgnoreCase("" + senderClient.getUserName() + "_" + receiverClient.getUserName())
                || !fileName.equalsIgnoreCase("" + receiverClient.getUserName() + "_" + senderClient.getUserName())) 
        {
             
            System.out.println("======================entered if =======================");
            System.out.println("the file is not exist");
            try {
                System.out.println("entered try section");
                Boolean fileExist = file.createNewFile();
                System.out.println("file exist or not" + fileExist);
                //set chat
                System.out.println("=======================start for in is ======================");
                ObjectFactory obj = new ObjectFactory();
                MsgType msg = obj.createMsgType();
                SenderType sender = new SenderType();
                //set sender data
                sender.setId(BigInteger.valueOf(senderClient.getId()));
                sender.setValue(message.getText());
                //set receiver data
                ReceiverType receiver = new ReceiverType();
                receiver.setId(BigInteger.valueOf(receiverClient.getId()));
                //set msg
                msg.setSender(sender);
                msg.setReceiver(receiver);
                msg.setColor(message.getColor());
                msg.setFont(message.getFont());
                messageList.add(msg);
                System.out.println("msgList" + messageList.size());

                System.out.println("====================end for in if=========================");
                //marshal [convert from object to xml ]

                ChatType test = new ChatType();
                test.setMessage(messageList);
                JAXBElement chatT = obj.createChat(test);

                Marshaller marsh = jaxbContext.createMarshaller();
               
                try {
                    marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    marsh.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "chat.xsd");
                    marsh.setProperty("com.sun.xml.internal.bind.xmlHeaders" ,
                            "<?xml-stylesheet type='text/xsl' href='history.xsl'?>");
                   
                } catch (PropertyException ex) {
                    System.out.println("error in marshaing propertites");
                }

                try {
                    marsh.marshal(chatT, file);//besho
                } catch (JAXBException ex) {
                    Logger.getLogger(SaveChatSession.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {

                System.out.println("can't create file");
            }

        } else {

            System.out.println("ana d5alt f el else");

           
           try {
                    
                    //unmarshal
                    Unmarshaller unmarsh = jaxbContext.createUnmarshaller();

                    try {
                        
                        JAXBchat = (JAXBElement) unmarsh.unmarshal(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("errror in unmarshaling");
                    }
                    ChatType chatType = (ChatType) JAXBchat.getValue();
                    System.out.println("message list size = " + messageList);

                    messageList = (ArrayList<MsgType>) chatType.getMessage();

                    ObjectFactory obj = new ObjectFactory();
                    MsgType msg = obj.createMsgType();
                    SenderType sender = new SenderType();
                    //set sender data
                    sender.setId(BigInteger.valueOf(senderClient.getId()));
                    sender.setValue(message.getText());
                    //set receiver data
                    ReceiverType receiver = new ReceiverType();
                    receiver.setId(BigInteger.valueOf(receiverClient.getId()));
                    //set msg
                    msg.setSender(sender);
                    msg.setReceiver(receiver);
                    msg.setColor(message.getColor());
                    msg.setFont(message.getFont());
                    messageList.add(msg);
                  
                    //marshal [convert from object to xml ]
                    //chatType.setMessage(messageList);
                    ChatType test = new ChatType();
                    test.setMessage(messageList);
                    JAXBElement element = obj.createChat(test);

                    Marshaller marsh = jaxbContext.createMarshaller();

                    marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    marsh.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "chat.xsd");
                    marsh.setProperty("com.sun.xml.internal.bind.xmlHeaders", 
                            "<?xml-stylesheet type='text/xsl' href='history.xsl'?>");

                    marsh.marshal(element, file);//besho

                } catch (JAXBException ex) {
                    System.out.println("error in unmarshaling block");
                }
//     
//           
//           
           }
                

           
            }

        

    }

