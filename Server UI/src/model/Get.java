/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import common.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TECHNOLOGY CITY
 */
public class Get {

    ResultSet rs = null;

    Connection con = null;

    PreparedStatement ps = null;

    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=Chat";
    String user = "sa";
    String pass = "1234";
    Statement stmt;

    public int getGenderFemale() {
        try {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException ex) {
                System.out.println("error");
            } catch (SQLException ex) {
                Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
            }

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("select count(gender) as femaleCount from dbo.Clients where gender=0");
            if (rs.next()) {
                return Integer.parseInt(rs.getString("femaleCount"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public User getClientFromDatabase(String username) {
        try {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException ex) {
                System.out.println("error");
            } catch (SQLException ex) {
                Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
            }

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            User userOfSearch = new User();
            rs = stmt.executeQuery("select fname , gender , user_name,email,status,age from Clients where user_name like '%" + username + "%'");

            if (rs.next()) {
                do {
                    userOfSearch.setAge(Integer.parseInt(rs.getString("age")));
                    userOfSearch.setGender(Integer.parseInt(rs.getString("gender")));
                    userOfSearch.setStatus(Integer.parseInt(rs.getString("status")));
                    userOfSearch.setFName(rs.getString("fname"));
                    userOfSearch.setUserName(rs.getString("user_name"));
                    userOfSearch.setEmail(rs.getString("email"));

                } while (rs.next());
                return userOfSearch;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public int getGenderMale() {
        try {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException ex) {
                System.out.println("error");
            } catch (SQLException ex) {
                Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
            }

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("select count(gender) as maleCount from dbo.Clients where gender=1");
            if (rs.next()) {
                return Integer.parseInt(rs.getString("maleCount"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int numberOfOnline() {
        try {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException ex) {
                System.out.println("error");
            } catch (SQLException ex) {
                Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
            }

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("select count(status) as online from Clients where status=1");
            if (rs.next()) {
                return Integer.parseInt(rs.getString("online"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int numberOfOffline() {
        try {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException ex) {
                System.out.println("error");
            } catch (SQLException ex) {
                Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
            }

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("select count(status) as offline from Clients where status=0");
            if (rs.next()) {
                return Integer.parseInt(rs.getString("offline"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
