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
    private String columnNames[] = { "ID","First Name","Last Name","Sex","Position","Company","DOB" };
    private TableDataModel tdm;
    
    public TableModel() throws ClassNotFoundException, SQLException {
        tdm = new TableDataModel();
        data = tdm.getAllData();
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
        if(col < 1) return false; else return true;
    }
    
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
