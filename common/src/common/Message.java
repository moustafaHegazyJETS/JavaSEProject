/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;

/**
 *
 * @author salma hasan
 */
public class Message implements Serializable {

    String color;
    String font;
    String text;
    int size;
    User sender;
    User reciever;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public void setColor(String _color) {
        color = _color;
    }

    public void setFont(String _font) {
        font = _font;
    }

    public void setText(String _text) {
        text = _text;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public String getFont() {
        return font;
    }

}
