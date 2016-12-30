/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userbasearea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Arjun
 */
public class MySqlConn {
    
    public MySqlConn() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
    
    public Connection dbConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/userbasearea", "root", "root");
    }
}
