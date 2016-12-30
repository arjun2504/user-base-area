/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userbasearea;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import userbasearea.MySqlConn;
import userbasearea.AdminArea;
/**
 *
 * @author Arjun
 */
public class LoginHandler {
    
    private Connection con;
    private String query;
    private Statement stmt;
    private int userId;
    private String role;
    
    public LoginHandler() throws ClassNotFoundException, SQLException {
        con = new MySqlConn().dbConnection();
    }
    
    public boolean checkUserInfo(String username, String password) throws SQLException {
        query = "SELECT role, id FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        boolean flag = false;
        while(rs.next()) {
            role = rs.getString(1);
            userId = rs.getInt(2);
            flag = true;
        }
        con.close();
        
        return flag;
    }
    
    public void authenticateUser() throws ClassNotFoundException, SQLException {
        System.out.println(this.role);
        if(this.role.equals("user")) {
            //create user object
        }
        else if(this.role.equals("admin")) {
            //create admin object
            new AdminArea(userId);
        }
    }
}
