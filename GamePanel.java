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
import java.awt.Component;

public class GamePanel extends JPanel {
    
    public GamePanel() {
        super();
    }

    public static void HomeScreenFrame(JFrame usedFrame, Party p, Utilitaries choice, Utilitaries startTheGame) {
        usedFrame.getContentPane().removeAll();

        GameImage img = new GameImage();

        GamePanel gpMain = new GamePanel();
        gpMain.setSize(600, 600);
        gpMain.setLayout(new BorderLayout());
        gpMain.add(img.ImageMenu(usedFrame, p, choice, startTheGame), BorderLayout.CENTER);

        GamePanel lpMain = new GamePanel();
        lpMain.setSize(100, 100);
        lpMain.setLayout(new FlowLayout());
        lpMain.add(new JLabel("A prototype-game by KRISNI Almehdi"));

        usedFrame.setLayout(null);
        lpMain.setBounds(0, 520, 600, 80);
        usedFrame.getContentPane().add(lpMain);
        gpMain.setBounds(0, 0, 600, 500);
        usedFrame.getContentPane().add(gpMain);
        usedFrame.repaint();
        usedFrame.revalidate();
    }

    // All of the panels below are Story-related panels

    public static void Ch1Pt1Panel1_Story(JFrame usedFrame, Party p) {
        usedFrame.getContentPane().removeAll();

        GamePanel mainP = new GamePanel();
        mainP.setSize(600, 600);
    }


}