package guis;

import java.awt.Button;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.MouseEvent;

import constants.CommonConstants;
import db.MyDBC;

public class RegisterFormGUI extends Form {

    public RegisterFormGUI() {
        super("BLACK JACK");
        addGuiComponents();
    }

    private void addGuiComponents() {
        JLabel regLabel = new JLabel("BLACK JACK");

        regLabel.setBounds(50, 50, 520, 100);
        regLabel.setForeground(CommonConstants.BUTTON_COLOR);
        regLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        regLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(regLabel);

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


        JLabel repassLabel = new JLabel("Re-enter Password");
        repassLabel.setBounds(100, 365, 400, 25);
        repassLabel.setForeground(CommonConstants.FONT_COLOR);
        repassLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        
        JPasswordField repassTextField = new JPasswordField();
        repassTextField.setBounds(100, 395, 450, 55);
        repassTextField.setBackground(CommonConstants.SECONDARY_COLOR);
        repassTextField.setForeground(CommonConstants.FONT_COLOR);
        repassTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(repassLabel);
        add(repassTextField);
        


        JButton regButton = new JButton("Register");
        regButton.setFont(new Font("Dialog", Font.BOLD,18));
        regButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        regButton.setBackground(CommonConstants.BUTTON_COLOR);
        regButton.setForeground(CommonConstants.FONT_COLOR);
        regButton.setBounds(200, 480, 250, 50);
        regButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String username = usernamTextField.getText();
                String password = new String(passTextField.getPassword());
                String repassword = new String(repassTextField.getPassword());
                if(validateUserInput(username, password, repassword)){
                    if(MyDBC.register(username, repassword)){
                        RegisterFormGUI.this.dispose();
                        LoginFormGUI loginFormGUI = new LoginFormGUI();
                        loginFormGUI.setVisible(true);

                        JOptionPane.showMessageDialog(loginFormGUI, "Registered Sucessfully!");
                    } else{
                        JOptionPane.showMessageDialog(RegisterFormGUI.this, "Error: Username exists!");
                    }
                }else{
                    JOptionPane.showMessageDialog(RegisterFormGUI.this, "Error: Username must have at least 6 characters!");
                }


            }
        });
        add(regButton);
        



        JLabel loginLabel = new JLabel("Have an account? Log in");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.setForeground(CommonConstants.BUTTON_COLOR);

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                RegisterFormGUI.this.dispose();
                new LoginFormGUI().setVisible(true);
            }
        });  
        loginLabel.setBounds(200, 540, 250, 30);
        add(loginLabel);

    }

    private boolean validateUserInput(String username, String password, String repassword){
        if(username.length() == 0 || password.length() == 0 || repassword.length() == 0) return false;
        if(username.length() < 6) return false;
        if(!password.equals(repassword)) return false;
        return true;
    }

}
