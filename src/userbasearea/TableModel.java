/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userbasearea;

import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
import userbasearea.TableDataModel;
/**
 *
 * @author Arjun
 */
public class TableModel extends AbstractTableModel {
    
    private Object data[][];
    private String columnNames[] = { "User ID","First Name","Last Name","Sex","Position","Company","DOB" };
    public String originalColumnNames[] = { "user_id","first_name", "last_name", "gender", "position", "company", "dob"};
    private TableDataModel tdm;
    private String role;
    
    public TableModel(String user_role) throws ClassNotFoundException, SQLException {
        tdm = new TableDataModel();
        data = tdm.getAllData("");
        role = user_role;
        //String[] originalColumnNames = tdm.ColumnNames;
    }
    
    public TableModel(String user_role, String username) throws ClassNotFoundException, SQLException {
        tdm = new TableDataModel();
        data = tdm.getAllData(username);
        role = user_role;
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    @Override
    public int getRowCount() {
        return tdm.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    
    public Class getColumnClass(int c) {
        return getValueAt(0,c).getClass();
    }
    
    public boolean isCellEditable(int row, int col) {
        if(col < 1 || role.equals("user"))
            return false;
        else
            return true;
    }
    
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
