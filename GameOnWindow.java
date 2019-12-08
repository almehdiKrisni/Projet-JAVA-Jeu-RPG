
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

public class GameOnWindow {
    /*private static int choiceStart = 0;

    public static void main(String[] args) {
        Party p = new Party();

        JFrame mainFrame = new JFrame("The Game");

        mainFrame.setVisible(true);
        mainFrame.setSize(600, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Label de départ
        JLabel label = new JLabel(new ImageIcon("Images/FESD_Archanea.png"));

        label.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(20, 20, 20, 20);
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.gridx = 0;
        c1.gridy = 0;

        // Bouton pour commencer le mode Histoire
        JButton startButton = new JButton("Story Mode");
        startButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                p.addMembers(mainFrame);
                choiceStart = 1;
                System.out.println("Story Mode");
            }
        });
        
        label.add(startButton, c1);

        // Bouton pour commencer le mode Versus
        JButton versusButton = new JButton("Versus Mode");
        versusButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                p.addMembers(mainFrame);
                choiceStart = 2;
                System.out.println("Versus Mode");
            }
        });

        c1.gridx = 1;
        label.add(versusButton, c1);

        JPanel panelS = new JPanel();
        panelS.setBackground(Color.WHITE);
        panelS.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(20, 50, 20, 20);

        panelS.add(new JLabel("A Game by KRISNI Almehdi"), c2);

        panelS.add(new JLabel("JAVA Project"), c2);

        mainFrame.setLayout(new BorderLayout(3,1));
        mainFrame.add(panelS, BorderLayout.PAGE_END);
        mainFrame.add(label);

        mainFrame.repaint();
        mainFrame.revalidate();
    }*/

    public static void main(String[] args) {
        Party p = new Party();
        JFrame mainFrame = new GameFrame();
        Utilitaries choice = new Utilitaries();
        Utilitaries start = new Utilitaries();

        GamePanel hmPanel = new GamePanel();
        hmPanel.HomeScreenFrame(mainFrame, p, choice, start);

        while (start.getValue() == 0) {
            System.out.println("Waiting for the game to launch");
            try { Thread.sleep(1000); } catch (InterruptedException e) { System.out.println("Error"); }
        }

        try { Thread.sleep(1000); } catch (InterruptedException e) { System.out.println("Error"); }

        if (choice.getValue() == 2) {
            Fight f1 = new Fight(p, new Enemies(p.getAverageLevel(), 1));
            f1.fightOnScreen(f1, mainFrame, "Images/Forest_Background.png");
        }
    }
}