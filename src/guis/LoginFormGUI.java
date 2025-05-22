package guis;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import constants.CommonConstants;
import db.MyDBC;

public class LoginFormGUI extends Form {

    public LoginFormGUI() {
        super("BLACK JACK");
        addGuiComponents();
    }

    int height = 100;
    int width = 520;

    private void addGuiComponents() {
        JLabel loginLabel = new JLabel("BLACK JACK");


        loginLabel.setBounds(50, 50, 520, 100);
        loginLabel.setForeground(CommonConstants.BUTTON_COLOR);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);

        JLabel usernamLabel = new JLabel("Username");
        usernamLabel.setBounds(100, 150, 400, 25);
        usernamLabel.setForeground(CommonConstants.FONT_COLOR);
        usernamLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        
        JTextField usernamTextField = new JTextField();
        usernamTextField.setBounds(100, 185, 450, 55);
        usernamTextField.setBackground(CommonConstants.SECONDARY_COLOR);
        usernamTextField.setForeground(CommonConstants.FONT_COLOR);
        usernamTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(usernamLabel);
        add(usernamTextField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(100, 255, 400, 25);
        passLabel.setForeground(CommonConstants.FONT_COLOR);
        passLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        
        JPasswordField passTextField = new JPasswordField();
        passTextField.setBounds(100, 285, 450, 55);
        passTextField.setBackground(CommonConstants.SECONDARY_COLOR);
        passTextField.setForeground(CommonConstants.FONT_COLOR);
        passTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(passLabel);
        add(passTextField);
        

        JButton logiButton = new JButton("Login");
        logiButton.setFont(new Font("Dialog", Font.BOLD,18));
        logiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logiButton.setBackground(CommonConstants.BUTTON_COLOR);
        logiButton.setForeground(CommonConstants.FONT_COLOR);
        logiButton.setBounds(200, 375, 250, 50);
        logiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String username = usernamTextField.getText();
                String password = new String(passTextField.getPassword());
                if(MyDBC.validateLogin(username, password)){
                    LoginFormGUI.this.dispose();
                    new BlackJackGUI(username,password);
                }else{
                    JOptionPane.showMessageDialog(LoginFormGUI.this, "Login Failed...");
                }
            }
        });
        add(logiButton);


        JLabel registerLabel = new JLabel("Not a user? Register now!");
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.setForeground(CommonConstants.BUTTON_COLOR);


        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                LoginFormGUI.this.dispose();
                new RegisterFormGUI().setVisible(true);
            }
        });       
 
        registerLabel.setBounds(200, 425, 250, 50);
        add(registerLabel);

    }

}
