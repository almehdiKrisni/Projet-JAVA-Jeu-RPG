import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Insets;

public class StoryWindow {

    // On crée les images du premier chapitre

    public static void ch1(JFrame mainFrame, Party p) {
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();
        usedFrame.setLayout(null);

        JLabel image = new JLabel(new ImageIcon("Images/Ch1.png"));
        image.setBounds(0, 0, 600, 600);
        usedFrame.add(image);

        usedFrame.repaint();
        usedFrame.revalidate();

        try { Thread.sleep(1000); } catch (InterruptedException e) { System.out.println("Error"); }
        
        ch1_pt1(mainFrame, p);
    }

    public static void ch1_pt1(JFrame mainFrame, Party p) {
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();
        usedFrame.setLayout(null);

        GamePanel background = new GamePanel();
        background.setSize(600, 600);
        background.setLayout(null);

        JLabel face = new JLabel(new ImageIcon("Images/Chrom_Happy.png"));
        face.setBounds(150, 100, 300, 350);
        usedFrame.add(face);

        JLabel backscreen = new JLabel(new ImageIcon("Images/Forest_Background.png"));
        backscreen.setBounds(0, 0, 600, 450);
        background.add(backscreen);

        GamePanel menuBar = new GamePanel();
        menuBar.setLayout(null);
        menuBar.setSize(600, 150);
        menuBar.setBackground(Color.WHITE);

        menuBar.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridy = 0;
        c.gridx = 0;

        menuBar.add(new JLabel("CHROM :"), c);

        c.gridy++;
        menuBar.add(new JLabel(" "), c);

        c.gridy++;
        menuBar.add(new JLabel("Now that's an unusual spot to fall asleep. You should be more careful, there are a lot of"), c);

        c.gridy++;
        menuBar.add(new JLabel("monsters around here. Anyway, name's Chrom. What's yours?"), c);

        c.gridy++;
        menuBar.add(new JLabel(p.getTeam().get(0).getName() + "? That has to be the dumbest name I've ever heard..."), c);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                //ch1_pt2(mainFrame, p);
            }
        });

        c.gridy++;
        menuBar.add(nextButton, c);

        menuBar.setBounds(0, 450, 600, 150);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);

        usedFrame.repaint();
        usedFrame.revalidate();
    }
}