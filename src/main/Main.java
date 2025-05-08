package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("TetraClone");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new BorderLayout());

        //GameArea
        GameArea ga = new GameArea();
        window.add(ga, BorderLayout.CENTER);

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        JButton quitButton = new JButton("Quit");
        quitButton.setFocusable(false);
        
        // style button to match with gui
        quitButton.setBackground(Color.BLACK);
        quitButton.setForeground(Color.MAGENTA);
        quitButton.setFont(new Font("Arial", Font.BOLD, 16));
        quitButton.setBorder(new LineBorder(Color.MAGENTA, 2));
        quitButton.setContentAreaFilled(false);
        quitButton.setOpaque(true);
        
        // hovering over button
        quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                quitButton.setBackground(new Color(20, 20, 20)); // Slightly lighter black
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                quitButton.setBackground(Color.BLACK);
            }
        });

        quitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("OptionPane.background", Color.BLACK);
                UIManager.put("Panel.background", Color.BLACK);
                UIManager.put("OptionPane.messageForeground", Color.MAGENTA);
                
                // confirm matching gui
                int confirm = JOptionPane.showConfirmDialog(
                    window,
                    "Are you sure you want to quit?",
                    "Confirm Quit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // button padding
        quitButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));


        buttonPanel.add(quitButton);

        window.add(buttonPanel, BorderLayout.SOUTH);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        ga.initGame();
    }
}