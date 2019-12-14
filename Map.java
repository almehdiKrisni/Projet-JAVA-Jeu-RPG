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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;

public class Map {
    // J'explique rapidement chaque case et ses caractéristiques
    // 0 = Grass : Terrain travserable
    // 1 = Sand : Terrain traversable
    // 2 = HighGrass : Terrain traversable, possibiliité de combats
    // 3 = Mountains : Terrain non-traversable
    // 4 = Trees : Terrain non-traversable
    // 5 = Water : Terrain non-traversable
    // 6 = HealTile : Terrain traversable, heal la team

    private Party party;
    private int posX;
    private int posY;
    private final int[][] map = {
        {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
        {5, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 1, 1, 4},
        {5, 1, 1, 1, 0, 0, 0, 0, 1, 1, 5, 5, 1, 0, 4},
        {5, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 4},
        {5, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
        {5, 1, 0, 4, 2, 2, 0, 0, 0, 2, 2, 2, 0, 0, 4},
        {5, 1, 0, 2, 2, 4, 0, 0, 0, 2, 2, 4, 0, 0, 4},
        {5, 1, 0, 2, 2, 4, 0, 0, 0, 4, 4, 4, 0, 0, 4},
        {5, 1, 0, 0, 4, 0, 0, 6, 0, 0, 0, 0, 0, 0, 4},
        {5, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
        {5, 1, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 4, 4},
        {5, 1, 1, 0, 0, 0, 0, 2, 2, 2, 2, 4, 4, 3, 3},
        {5, 5, 1, 0, 0, 0, 2, 2, 2, 2, 4, 3, 3, 3, 3},
        {5, 5, 1, 0, 0, 2, 2, 2, 2, 4, 3, 3, 3, 3, 3},
        {5, 5, 5, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3}
    };

    public Map(Party p) {
        this.posX = 1;
        this.posY = 1;
        this.party = p;
    }

    // Méthode pour obtenir les éléments d'un objet de la classe

    public Party getParty() {
        return this.party;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int[][] getMap() {
        return this.map;
    }

    public int getTile(int x, int y) {
        return this.map[x][y];
    }

    // Méthode de set des éléments de la classe

    public void setPosX(int x) { this.posX = x; }

    public void setPosY(int y) { this.posY = y; }

    // Vérifie si les terrains sont infranchissables ou non

    public Boolean isTraversable(int x, int y) {
        int c = this.getTile(x, y);
        return ( Arrays.asList(0, 1, 2, 6).contains(c) );
    }

    public Boolean inHighGrass(int x, int y) {
        int i = this.getTile(x, y);
        return (i == 2);
    }

    public Boolean onHealTile(int x, int y) {
        int i = this.getTile(x, y);
        return (i == 6);
    }

    // Retourne l'affichage en fonction des cases

    public static JLabel imageByCase(int x, int y, Map map) {
        int i = map.getTile(x, y);
        if (i == 0) return new JLabel(new ImageIcon("Images/GrassTile.png"));
        else if (i == 1) return new JLabel(new ImageIcon("Images/SandTile.png"));
        else if (i == 2) return new JLabel(new ImageIcon("Images/HighGrassTile.png"));
        else if (i == 3) return new JLabel(new ImageIcon("Images/MountainTile.png"));
        else if (i == 4) return new JLabel(new ImageIcon("Images/TreeTile.png"));
        else if (i == 5) return new JLabel(new ImageIcon("Images/WaterTile.png"));
        else return new JLabel(new ImageIcon("Images/HealTile.png"));
    }

    // Méthode de Test pour afficher la map

    public static void startFight(JFrame mainFrame, JFrame fightFrame, Map map1, int px, int py) {
        System.out.println("Hello");
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();

        Party p = map1.getParty();
        Enemies e = new Enemies(p.getAverageLevel(), 1);
        Utilitaries u = new Utilitaries();
        Fight f1 = new Fight(p, e, u);
        f1.fightOnScreen(f1, fightFrame, "Forest_Background.png");

        while (u.getValue() == 0) {}

        map1.afficheMap(mainFrame, map1, px, py);
    }

    public static void afficheMap(JFrame mainFrame, Map map1, int px, int py) {
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();

        if (map1.onHealTile(px, py)) {
            map1.getParty().healTeam();
            JOptionPane.showMessageDialog(null, "All your party members are now full of energy!");
        }

        if (map1.inHighGrass(px, py)) {
            JFrame frame = new JFrame("Story Fight");
            frame.setTitle("The Game - Fight");
            frame.setSize(600, 600);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            map1.startFight(mainFrame, frame, map1, px, py);
        }

        JPanel bg = new JPanel();
        bg.setLayout(null);
        bg.setSize(600, 600);

        map1.setPosX(px); map1.setPosY((py));

        JLabel imT = new JLabel(new ImageIcon("Images/TeamIcon.png"));
        imT.setBounds(map1.getPosX() * 40, map1.getPosY() * 40, 40, 40);
        bg.add(imT);

        JButton upB = new JButton("UP");
        upB.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                if (map1.isTraversable(px, py - 1)) {
                    afficheMap(mainFrame, map1, px, py - 1);
                }
            }
        });
        upB.setBounds(480, 460, 60, 60);
        bg.add(upB);

        JButton downB = new JButton("DOWN");
        downB.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                if (map1.isTraversable(px, py + 1)) {
                    afficheMap(mainFrame, map1, px, py + 1);
                }
            }
        });
        downB.setBounds(480, 520, 60, 60);
        bg.add(downB);

        JButton leftB = new JButton("LEFT");
        leftB.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                if (map1.isTraversable(px - 1, py)) {
                    afficheMap(mainFrame, map1, px - 1, py);
                }
            }
        });
        leftB.setBounds(420, 520, 60, 60);
        bg.add(leftB);

        JButton rightB = new JButton("RIGHT");
        rightB.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                if (map1.isTraversable(px + 1, py)) {
                    afficheMap(mainFrame, map1, px + 1, py);
                }
            }
        });
        rightB.setBounds(540, 520, 60, 60);
        bg.add(rightB);

        for (int i = 0; i < map1.map.length; i++) {
            for (int j = 0; j < map1.map.length; j++) {
                JLabel im = imageByCase(i, j, map1);
                im.setBounds(i * 40, j * 40, 40, 40);
                bg.add(im);
            }
        }

        bg.setBounds(0, 0, 600, 600);
        mainFrame.add(bg);

        mainFrame.repaint();
        mainFrame.revalidate();
    }

    public static void main(String[] args) {
        Party p = new Party();
        Map map1 = new Map(p);

        JFrame mainFrame = new JFrame("Test Frame");

        mainFrame.setVisible(true);
        mainFrame.setSize(600, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);

        map1.afficheMap(mainFrame, map1, 1, 1);

        mainFrame.repaint();
        mainFrame.revalidate();
    }
}