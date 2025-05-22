package guis;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import constants.CommonConstants;
import db.MyDBC;

public class PlayersTableGUI extends Form {

    private String username;
    private String password;

    public PlayersTableGUI(String username, String password) {
        super("BLACK JACK");
        this.username = username;
        this.password = password;
        addGuiComponents();
    }

    JLabel table;
    JLabel topLabel;

    public void addGuiComponents() {
        table = new JLabel("RATING");
        table.setBounds(60, 1, 520, 100);
        table.setForeground(CommonConstants.FONT_COLOR);
        table.setFont(new Font("Dialog", Font.BOLD, 40));
        table.setHorizontalAlignment(SwingConstants.CENTER);
        add(table);

        topLabel = new JLabel();
        topLabel.setText("TOP 10 PLAYERS");
        topLabel.setBounds(60, 80, 520, 50);
        topLabel.setForeground(CommonConstants.BUTTON_COLOR);
        topLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(topLabel);


        String[] columnNames = { "Player", "Victories" };

        Object[][] data = MyDBC.fetchTopPlayers();

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable ratingTable = new JTable(model);
        ratingTable.setFont(new Font("Dialog", Font.BOLD, 18));
        ratingTable.setForeground(CommonConstants.FONT_COLOR);
        ratingTable.setRowHeight(30);
        ratingTable.setBackground(CommonConstants.SECONDARY_COLOR);
        ratingTable.setGridColor(CommonConstants.BUTTON_COLOR);
        ratingTable.setShowHorizontalLines(false);
        ratingTable.setShowVerticalLines(true);
        ratingTable.setRowSelectionAllowed(false);    


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        ratingTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        ratingTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        ratingTable.setBounds(0, 185, 650, 300);

        JLabel playerLabel = new JLabel("Player");
        playerLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        playerLabel.setForeground(CommonConstants.FONT_COLOR);
        playerLabel.setBounds(0, 0, 325, 35); 
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER); 

        JLabel victoriesLabel = new JLabel("Victories");
        victoriesLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        victoriesLabel.setForeground(CommonConstants.FONT_COLOR);
        victoriesLabel.setBounds(325, 0, 325, 35); 
        victoriesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel headerPanel = new JPanel();
        headerPanel.add(playerLabel);
        headerPanel.add(victoriesLabel);
        headerPanel.setBackground(CommonConstants.BUTTON_COLOR);
        headerPanel.setBounds(0,150,650,35);
        headerPanel.setLayout(null);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(CommonConstants.BUTTON_COLOR);
        bottomPanel.setBounds(0,480,650,15);
        bottomPanel.setLayout(null);

        //back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Dialog", Font.BOLD,18));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBackground(CommonConstants.BUTTON_COLOR);
        backButton.setForeground(CommonConstants.FONT_COLOR);
        backButton.setBounds(200, 530, 250, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                PlayersTableGUI.this.dispose();
                new BlackJackGUI(username, password);
            }
        });
        add(backButton);

        add(headerPanel);
        add(ratingTable);
        add(bottomPanel);
        

    }

}
