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
    public StoryWindow() {}

    public static void ch1_pt1(JFrame mainFrame) {
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();
        usedFrame.setLayout(null);

        GamePanel background = new GamePanel();
        background.setSize(600, 600);
        background.setLayout(null);

        JLabel backscreen = new JLabel(new ImageIcon("Images/Forest_Background.png"));
        backscreen.setBounds(0, 0, 600, 500);
        background.add(backscreen);

        GamePanel menuBar = new GamePanel();
        menuBar.setLayout(new FlowLayout());
        menuBar.setSize(600, 100);
        menuBar.setBackground(Color.WHITE);

        JLabel face = new JLabel(new ImageIcon("Images/Chrom_Face1.png"));
        face.setBounds(150, 150, 300, 350);
        usedFrame.add(face);
    }
}