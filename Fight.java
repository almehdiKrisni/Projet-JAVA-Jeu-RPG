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

public class Fight {
    private int Turn;
    private Party allies;
    private Enemies enemies;

    public Fight(Party allies, Enemies enemies){
        this.Turn = 1;
        this.allies = allies;
        this.enemies = enemies;
    }

    // Accesseurs aux éléments d'un objet de la classe

    public int getTurn() { return this.Turn; }

    public Party getParty() { return this.allies; } 
    
    public Enemies getEnemies() { return this.enemies; }

    // Méthode servant à simuler les combats

    public void actionFight() {
        System.out.println("################################## FIGHT ###################################\n");

        int expIfWin = this.enemies.getAverageExp();

        while (!this.enemies.haveBeenDefeated()) {
            System.out.println("\n--------------------------------- TURN " + this.Turn + " -----------------------------------\n");
            this.Turn++;

            System.out.println(this.allies + "\nEnemies : \n" + this.enemies);
            for (Character c : this.allies.getTeam()) {
                if (c.getIsDead() == false) {
                    try { Thread.sleep(2500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                    c.actionCombat(this.enemies);

                    // Fin du combat lorsque tout les ennemis ont été vaincus

                    if (this.enemies.haveBeenDefeated()) {
                        try { Thread.sleep(2500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                        System.out.println("______________________________________________________________________________\n");
                        double r = Math.random();
                        if (r < 0.33) System.out.println("The enemies have been defeated! Let's move on...\n");
                        else if (r < 0.67) System.out.println("Victory! To the next fight...\n");
                        else System.out.println("That settles it! It's crazy how strong we are...\n");

                        try { Thread.sleep(2500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                        for (Character perso : this.allies.getTeam()) {
                            perso.earnExp(expIfWin);
                        }

                        System.out.println("############################# END OF THE FIGHT #############################\n");
                        return;
                    }
                }
            }
            for (Mob m : this.enemies.getEnemies()) {
                try { Thread.sleep(2500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                m.actionCombat(this.allies);

                // Fin du combat si tout l'équipe est à terre

                if (this.allies.haveLost()) {
                    try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    System.out.println("______________________________________________________________________________\n");
                        double r = Math.random();
                        if (r < 0.33) System.out.println("It's a shame ...\n");
                        else if (r < 0.67) System.out.println("How could something like that happen\n");
                        else System.out.println("It's a cruel world we live in ...\n");

                        System.out.println("############################# END OF THE GAME ##############################\n");

                        return;
                }
            }

        }
    }

    public static void startFightScreen(JFrame mainFrame) {
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();

        // Petite animation pour signaler le debut d'un combat
        for (int i = 0; i < 6; i++) {
            GamePanel panel = new GamePanel();
            panel.setSize(600, 600);
            usedFrame.getContentPane().removeAll();

            if ((i % 2) == 0) {
                panel.setBackground(Color.WHITE);
                panel.setLayout(null);
                JLabel img = new JLabel(new ImageIcon("Images/Fight_Logo.png"));
                img.setBounds(200, 200, 200, 200);
                panel.add(img);

                usedFrame.setLayout(null);
                panel.setBounds(0, 0, 600, 600);
                usedFrame.getContentPane().add(panel);

                usedFrame.repaint();
                usedFrame.revalidate();
            }

            else {
                panel.setBackground(Color.RED);
                usedFrame.setLayout(null);
                panel.setBounds(0, 0, 600, 600);
                usedFrame.getContentPane().add(panel);

                usedFrame.repaint();
                usedFrame.revalidate();
            }

            try { Thread.sleep(200); } catch (InterruptedException e) { System.out.println("Error in fight sleep"); }
        }
    }

    public static void basicFightScreen(JFrame mainFrame, String backgroundFile, Party p, Enemies e) {
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();

        GamePanel background = new GamePanel();
        background.setSize(600, 600);
        background.setLayout(null);

        JLabel backscreen = new JLabel(new ImageIcon(backgroundFile));
        backscreen.setBounds(0, 0, 600, 500);
        background.add(backscreen);

        GamePanel menuBar = new GamePanel();
        menuBar.setLayout(new FlowLayout());
        menuBar.setSize(600, 100);
        menuBar.setBackground(Color.WHITE);

        // On ajoute les boutons au menu de combat

        JButton attackButton = new JButton("Attack");
        menuBar.add(attackButton);

        JButton infoButton = new JButton("Team Info");
        infoButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, p.toString());
            }
        });


        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);

        usedFrame.setLayout(null);
        background.setBounds(0, 0, 600, 600);

        // Les boutons suivants permettent de se repérer pendant le combat

        JButton partyS = new JButton("Party Side");
        partyS.setBounds(55, 20, 90, 60);
        usedFrame.add(partyS);

        JButton fightS = new JButton("Fight Scene");
        fightS.setBounds(255, 20, 90, 60);
        usedFrame.add(fightS);

        JButton enemyS = new JButton("Enemy Side");
        enemyS.setBounds(455, 20, 90, 60);
        usedFrame.add(enemyS);

        // On ajoute les sprites des alliés (à condition qu'ils ne soient pas à terre) et des ennemis à l'écran

        JLabel[] imagePList = new JLabel[(p.getTeam().size())];
        for (int i = 0; i < p.getTeam().size(); i++) {
            imagePList[i] = p.getTeam().get(i).getImageN();
        }

        int x = 55; int y = 105; int largeur = 90; int longueur = 90;
        for (int i = 0; i < imagePList.length; i++) {
            if (p.getTeam().get(i).getIsDead() == false) {
                imagePList[i].setBounds(x, y, largeur, longueur);
                usedFrame.add(imagePList[i]);
                y = y + 100;
            }
        }

        JLabel[] imageEList = new JLabel[e.getEnemies().size()];
        for (int i = 0; i < e.getEnemies().size(); i++) {
            imageEList[i] = e.getMobInPos(i).getImageN();
        }

        x = 455; y = 105; largeur = 90; longueur = 90;
        for (int i = 0; i < imageEList.length; i++) {
            imageEList[i].setBounds(x, y, largeur, longueur);
            usedFrame.add(imageEList[i]);
            y = y + 100;
        }

        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    }


    public static void fightOnScreen(Fight f, JFrame mainFrame, String backgroundFile) {
        System.out.println(f.toString());

        JFrame usedFrame = mainFrame;
        f.startFightScreen(usedFrame);
        f.basicFightScreen(usedFrame, backgroundFile, f.getParty(), f.getEnemies());

    }
}

