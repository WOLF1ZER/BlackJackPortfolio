package guis;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import constants.CommonConstants;
import db.MyDBC;
import gamelogic.GameLogic;

public class BlackJackGUI {
    private GameLogic game;
    private String username;
    private String password;

    static JFrame frame;
    private JPanel gamePanel;
    private JPanel buttonPanel;
    private JButton hitButton, stayButton, resetButton;
    private JPanel settingsPanel;
    private JButton settingsButton, ratingButton;
    private JLabel winrate;

    private final int boardWidth = 650;
    private final int boardHeight = 650;
    private final int cardWidth = 110;
    private final int cardHeight = 154;
    private int wins = 0;

    public BlackJackGUI(String username, String password) {
        game = new GameLogic();
        this.username = username;
        this.password = password;
        addGUIComponents();
    }

    public void addGUIComponents() {
        frame = new JFrame("BLACK JACK");
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image hiddenCardImg = new ImageIcon(getClass().getResource("/cardsimages/BACK.png")).getImage();

                    if (!stayButton.isEnabled()) {
                        hiddenCardImg = new ImageIcon(getClass().getResource(game.hiddenCard.getImagePath()))
                                .getImage();
                    }
                    g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                    for (int i = 0; i < game.dealerHand.size(); i++) {
                        GameLogic.Card card = game.dealerHand.get(i);
                        Image img = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                        g.drawImage(img, cardWidth + 25 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
                    }

                    for (int i = 0; i < game.playerHand.size(); i++) {
                        GameLogic.Card card = game.playerHand.get(i);
                        Image img = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                        g.drawImage(img, 20 + (cardWidth + 5) * i, 360, cardWidth, cardHeight, null);
                    }

                    if (!stayButton.isEnabled()) {
                        String message = "";
                        if (game.playerSum > 21) {
                            message = "You Lost!";
                        } else if (game.dealerSum > 21) {
                            message = "You Won!";
                            wins++;
                            MyDBC.updateVictories(username);
                        }

                        else if (game.playerSum == game.dealerSum)
                            message = "Tie!";
                        else if (game.playerSum > game.dealerSum) {
                            message = "You Won!";
                            wins++;
                            MyDBC.updateVictories(username);
                        } else {
                            message = "You Lost!";

                        }

                        g.setFont(new Font("Montserrat", Font.PLAIN, 30));
                        g.setColor(Color.red);
                        g.drawString(message, 250, 280);

                        // saving wins to db
                        wins = MyDBC.getVictories(username);
                        winrate.setText("VICTORIES: " + wins);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        buttonPanel = new JPanel();
        hitButton = new JButton("HIT");
        stayButton = new JButton("STAY");
        resetButton = new JButton("RESTART");

        setupUI();
        setupActions();
        game.startGame();
        gamePanel.repaint();
    }

    private void setupUI() {
        frame.setSize(boardWidth, boardHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(CommonConstants.SECONDARY_COLOR);
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        hitButton.setFont(new Font("Dialog", Font.BOLD, 15));
        hitButton.setBackground(CommonConstants.BUTTON_COLOR);
        hitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hitButton.setForeground((CommonConstants.FONT_COLOR));

        stayButton.setBackground(CommonConstants.BUTTON_COLOR);
        stayButton.setFocusable(false);
        stayButton.setFont(new Font("Dialog", Font.BOLD, 15));
        stayButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stayButton.setForeground((CommonConstants.FONT_COLOR));

        resetButton.setFocusable(false);
        resetButton.setBackground(CommonConstants.BUTTON_COLOR);
        resetButton.setFont(new Font("Dialog", Font.BOLD, 15));
        resetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetButton.setForeground((CommonConstants.FONT_COLOR));

        buttonPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        winrate = new JLabel();
        winrate.setText("VICTORIES: " + wins);
        winrate.setFont(new Font("Dialog", Font.BOLD, 25));
        winrate.setForeground(CommonConstants.FONT_COLOR);
        winrate.setHorizontalAlignment(SwingConstants.CENTER);
        winrate.setHorizontalAlignment(SwingConstants.LEFT);
        winrate.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 0));
        settingsPanel.add(winrate, BorderLayout.WEST);
        settingsPanel.add(winrate);
        settingsPanel.revalidate();
        settingsPanel.repaint();

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.setBackground(CommonConstants.PRIMARY_COLOR);

        settingsButton = new JButton("OPTIONS");
        ratingButton = new JButton("RATING");
        settingsButton.setFocusable(false);
        settingsButton.setBackground(CommonConstants.BUTTON_COLOR);
        settingsButton.setFont(new Font("Dialog", Font.BOLD, 15));
        settingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        settingsButton.setForeground((CommonConstants.FONT_COLOR));

        ratingButton.setFocusable(false);
        ratingButton.setBackground(CommonConstants.BUTTON_COLOR);
        ratingButton.setFont(new Font("Dialog", Font.BOLD, 15));
        ratingButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ratingButton.setForeground((CommonConstants.FONT_COLOR));

        buttonContainer.add(ratingButton);
        buttonContainer.add(settingsButton);

        settingsPanel.add(buttonContainer, BorderLayout.EAST);
        frame.add(settingsPanel, BorderLayout.NORTH);

    }

    private void setupActions() {
        hitButton.addActionListener(e -> {
            game.drawPlayerCard();
            if (game.playerSum > 21) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
            }
            gamePanel.repaint();
        });

        stayButton.addActionListener(e -> {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);

            while (game.dealerSum < 17) {
                game.drawDealerCard();
            }

            gamePanel.repaint();
        });

        resetButton.addActionListener(e -> {
            game.startGame();
            hitButton.setEnabled(true);
            stayButton.setEnabled(true);
            gamePanel.repaint();
        });
        ratingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new PlayersTableGUI(username, password).setVisible(true);
                ;
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = { "Log Out", "Delete Account", "Back" };
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "What would you like to do?",
                        "Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                switch (choice) {
                    case 0: frame.dispose();
                         new LoginFormGUI().setVisible(true);
                        break;
                    case 1: // Delete Account
                        int confirm = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete your account?", "Confirm", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            MyDBC.deleteAccount(username,password);
                            frame.dispose();
                            new LoginFormGUI().setVisible(true);
                        }
                        break;
                    case 2: // Back
                        break;
                    default:
                        System.out.println("No option selected.");
                }
            }
        });
    }

}
