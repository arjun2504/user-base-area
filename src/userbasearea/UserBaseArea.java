/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userbasearea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import userbasearea.LoginHandler;
/**
 *
 * @author Arjun
 */

public class UserBaseArea {
    
    private JFrame loginFrame;
    private JPanel mainPanel, titlePanel, componentsPanel, buttonPanel;
    private JLabel loginTitle, usernameLabel, passwordLabel, messageLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private String errorMessage = "";
    
    /**
     * @param args the command line arguments
     */
    public UserBaseArea() {
        showLogin();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new UserBaseArea();
    }
    
    private void showLogin() {
        loginFrame = new JFrame("Login to access your data");
        loginFrame.setSize(300, 150);
        loginFrame.setLocationRelativeTo(null);
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(padding);
        
        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        
        loginTitle = new JLabel("Login", SwingConstants.CENTER);
        titlePanel.add(loginTitle, BorderLayout.NORTH);
        
        componentsPanel = new JPanel();
        componentsPanel.setLayout(new GridLayout(2,2));
        
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        usernameField = new JTextField();
        usernameField.getDocument().addDocumentListener(new changeTextListener());
        passwordField = new JPasswordField();
        passwordField.getDocument().addDocumentListener(new changeTextListener());
        
        componentsPanel.add(usernameLabel);
        componentsPanel.add(usernameField);
        componentsPanel.add(passwordLabel);
        componentsPanel.add(passwordField);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(40,40));
        loginButton.addActionListener(new LoginButtonHandler());
        buttonPanel.add(loginButton, BorderLayout.CENTER);
        
        
        messageLabel = new JLabel(" ", SwingConstants.CENTER);
        
        mainPanel.add(titlePanel);
        mainPanel.add(componentsPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(messageLabel);
        
        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);
    }
    
    private class LoginButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String buttonClicked = e.getActionCommand();
            if("Login".equals(buttonClicked)) {
                try {
                    LoginHandler lh = new LoginHandler();
                    if(!lh.checkUserInfo(usernameField.getText(), passwordField.getText())) {
                        errorMessage = "Error username or password";
                        messageLabel.setText(errorMessage);
                    }
                    else {
                        //close this dialog
                        loginFrame.setVisible(false);
                        
                        //authenticate user
                        lh.authenticateUser();
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(UserBaseArea.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    private class changeTextListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            this.changedUpdate(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            this.changedUpdate(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            errorMessage = " ";
            messageLabel.setText(errorMessage);
        }

    }
}
