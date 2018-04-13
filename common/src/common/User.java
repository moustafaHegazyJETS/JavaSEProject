/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;
import java.sql.Date;
import javafx.scene.image.Image;

/**
 *
 * @author TECHNOLOGY CITY
 */
public class User implements Serializable{
    
    int id;
    String userName;
    String password;
    String fName;
    int gender;
    Image profilePic;
    String dateOfBirth;
    int age;
    int status;
    String email;
    
    //setters 
    public void setID(int _id){id = _id;}
    public void setUserName(String _userName){userName = _userName;}
    public void setFName(String _fName){fName = _fName;}
    public void setGender(int _gender){gender = _gender;}
    public void setProfilePic(Image _profilePic){profilePic = _profilePic;}
    public void setDOB(String _dateOfBirth){dateOfBirth = _dateOfBirth;}
    public void setAge(int _age){age = _age;}
    public void setStatus(int _status){status = _status;}
    public void setEmail(String _email){email = _email;}
    
    //getters
    public int getId(){return id;}
    public String getUserName(){return userName;}
    public String getFName(){return fName;}
    public int getGender(){return gender;}
    public Image getProfilePic(){return profilePic;}
    public String getDOB(){return dateOfBirth;}
    public int getAge(){return age;}
    public int getStatus(){return status;}
    public String getEmail(){return email;}
}
