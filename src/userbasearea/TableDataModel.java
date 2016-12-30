/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userbasearea;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
    private int ColumnCount;
    public String ColumnNames[];
    
    
    public TableDataModel() throws ClassNotFoundException, SQLException {
        //initializing connection to database
        con = new MySqlConn().dbConnection();
    }
    
    
    
    public void setColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        ColumnCount = metaData.getColumnCount();
        ColumnNames = new String[ColumnCount];
        int count = 1;
        while(count <= ColumnCount) {
            ColumnNames[count - 1] = metaData.getColumnName(count);
            count++;
        }
    }
    
    public void updateChanges(int id, String columnName, String newValue) throws SQLException {
        query = "UPDATE details SET " + columnName + " = '" + newValue + "' WHERE user_id = " + id;
        System.out.println(query);
        Statement st = con.createStatement();
        st.executeUpdate(query);
    }
    
    public Object[][] getAllData(String username) throws SQLException {
        
        if(username.equals("")) {
            query = "SELECT * FROM details";
        }
        else {
            query = "SELECT * FROM details WHERE user_id IN (SELECT id FROM users WHERE username = '" + username + "')";
        }
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        setColumnNames(rs);
        int totalData = getDataCount(rs);
        data = new Object[totalData][7];
        
        
        int i = 0;
        while(rs.next()) {
            
            data[i][0] = rs.getString("user_id");
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
