/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userbasearea;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import userbasearea.MySqlConn;
import userbasearea.TableDataModel;
/**
 *
 * @author Arjun
 */
public class AdminArea {
    private Connection con;
    private JFrame mainFrame;
    private JPanel mainPanel, headerPanel, tabPanel;
    private JLabel welcomeText;
    private int userId;
    private String query = "", username = "";
    private JComboBox usernameSelect;
    private JButton retrieveBtn;
    private JTabbedPane tabPane;
    private JPanel viewPanel, editPanel;
    private String windowTitle = "";
    private JTable viewTable;
    private JScrollPane scrolling;
    
    public AdminArea(int user) throws ClassNotFoundException, SQLException {
        //initializing database
        con = new MySqlConn().dbConnection();
        userId = user;
        createWindow();
    }
    
    private void createWindow() throws SQLException, ClassNotFoundException {
        mainFrame = new JFrame();
        mainFrame.setSize(new Dimension(500, 500));
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
        
        mainPanel = new JPanel();
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        mainPanel.setBorder(padding);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        
        setWelcomeText();
        
        mainFrame.setTitle(windowTitle);
        headerPanel.add(welcomeText);
        
        
        fixMainControls();
        
        headerPanel.add(usernameSelect);
        headerPanel.add(retrieveBtn);
        
        tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());
        
        prepareTabbedPanes();
        tabPanel.add(tabPane);
        
        
        mainPanel.add(headerPanel);
        mainPanel.add(tabPanel);
        mainFrame.add(mainPanel);
    }
    
    private void setWelcomeText() throws SQLException {
        query = "SELECT username FROM users WHERE id = " + userId + " LIMIT 1";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()) {
            username = rs.getString(1);
        }
        windowTitle = "Welcome, " + username;
        welcomeText = new JLabel("Choose a username: ", SwingConstants.CENTER);
        welcomeText.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
    
    private void fixMainControls() throws SQLException {
        usernameSelect = new JComboBox();
        
        query = "SELECT id, username FROM users WHERE role = 'user'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()) {
            int id = rs.getInt(1);
            String uname = rs.getString(2);
            usernameSelect.addItem(uname);
        }
        usernameSelect.setSelectedIndex(0);
        usernameSelect.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        retrieveBtn = new JButton("Filter");
        retrieveBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
    }
    
    private void prepareTabbedPanes() throws ClassNotFoundException, SQLException {
        tabPane = new JTabbedPane();
        viewPanel = new JPanel();
        
        
        viewTable = new JTable(new TableModel());
        viewTable.getModel().addTableModelListener(new updateMyTable());
        
        scrolling = new JScrollPane(viewTable);
        viewPanel.add(scrolling);
        
        tabPane.addTab("View", viewPanel);
    }
    
    private class updateMyTable implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int col = e.getColumn();
            
            TableModel myTM = (TableModel) e.getSource();
            String columnName = myTM.getColumnName(col);
            Object data = myTM.getValueAt(row, col);
            Object id = myTM.getValueAt(row, 0);
            
            System.out.println(data.toString());
            System.out.println(id.toString());
        }
        
    }
}
