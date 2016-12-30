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
import javax.swing.table.AbstractTableModel;
import userbasearea.MySqlConn;
/**
 *
 * @author Arjun
 */
public class TableDataModel {
    
    private String query = "";
    private Connection con;
    private int count = 0;
    Object data[][];
    
    public TableDataModel() throws ClassNotFoundException, SQLException {
        //initializing connection to database
        con = new MySqlConn().dbConnection();
    }
    
    public Object[][] getAllData() throws SQLException {
        query = "SELECT * FROM details";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        int totalData = getDataCount(rs);
        data = new Object[totalData][7];
        
        
        int i = 0;
        while(rs.next()) {
            
            data[i][0] = rs.getString("id");
            data[i][1] = rs.getString("first_name");
            data[i][2] = rs.getString("last_name");
            data[i][3] = rs.getString("gender");
            data[i][4] = rs.getString("position");
            data[i][5] = rs.getString("company");
            data[i][6] = rs.getString("dob");
            
            i++;
        }
        
        return data;
    }
    
    private int getDataCount(ResultSet rs) throws SQLException {
        while(rs.next()) {
            count++;
        }
        while(rs.previous());
        return count;
    }
    
    public int getRowCount() {
        return count;
    }

}
