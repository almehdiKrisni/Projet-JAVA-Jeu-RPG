import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.Timer;

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
                panel.setBackground(Color.BLACK);
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












    public static void basicFightScreen(JFrame mainFrame, String backgroundFile, Party p, Enemies e, int order, Fight f) {
        
        // On ajoute le fond de combat et la barre de menu

        if (order >= p.getTeam().size()) {
            f.basicFightScreen(mainFrame, backgroundFile, p, e, 0, f);
        }

        if (p.getTeam().get(order).getIsDead()) {
            if (order < p.getTeam().size() - 1) f.basicFightScreen(mainFrame, backgroundFile, p, e, order + 1, f);
        }

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
        attackButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                f.chooseTeamAttack(mainFrame, backgroundFile, p, e, order, f);
            }
        });
        menuBar.add(attackButton);

        JButton infoButton = new JButton("Team Info");
        infoButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                JOptionPane.showMessageDialog(null, p.toString() + "\n\n" + e.toString());
            }
        });
        menuBar.add(infoButton);


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
            imageEList[i] = e.getEnemies().get(i).getImageN();
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











    // Fonction du choix de la cible pour les alliés

    public static void chooseTeamAttack(JFrame mainFrame, String backgroundFile, Party p, Enemies e, int Turn, Fight f) {
        //On ajoute le fond d'écran

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
            imageEList[i] = e.getEnemies().get(i).getImageN();
        }

        x = 455; y = 105; largeur = 90; longueur = 90;
        for (int i = 0; i < imageEList.length; i++) {
            imageEList[i].setBounds(x, y, largeur, longueur);
            usedFrame.add(imageEList[i]);
            y = y + 100;
        }

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

        // On crée le menu des actions

        menuBar.add(new JLabel("It's " + p.getTeam().get(Turn).getName() + " turn to attack ..."));

        // On ajoute les choix de combats
        
        for (Mob m : e.getEnemies()) {
            JButton button = new JButton(m.toString());
            button.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent a) {
                    f.attackTeam(mainFrame, backgroundFile, e.posMob(m), Turn, p, e, f);
                }
            });
            menuBar.add(button);
        }

        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    }

    // Fonction d'attaque

    public static void attackTeam(JFrame mainFrame, String backgroundFile, int target, int attacker, Party p, Enemies e, Fight f) {
        System.out.println("Hey");

        int damage = Math.max(p.getTeam().get(attacker).getATK() - e.getEnemies().get(target).getDEF(), 0);

        if (p.getTeam().get(attacker).getSPEED() >= e.getEnemies().get(target).getSPEED() + 5) {
            System.out.print("Superior speed");

            JFrame usedFrame1 = mainFrame;
            usedFrame1.getContentPane().removeAll();
            usedFrame1.setLayout(null);

            GamePanel background1 = new GamePanel();
            background1.setSize(600, 600);
            background1.setLayout(null);

            JLabel backscreen1 = new JLabel(new ImageIcon(backgroundFile));
            backscreen1.setBounds(0, 0, 600, 500);
            background1.add(backscreen1);

            GamePanel menuBar1 = new GamePanel();
            menuBar1.setLayout(new FlowLayout());
            menuBar1.setSize(600, 100);
            menuBar1.setBackground(Color.WHITE);

            // Les boutons suivants permettent de se repérer pendant le combat

            JButton partyS1 = new JButton("Party Side");
            partyS1.setBounds(55, 20, 90, 60);
            usedFrame1.add(partyS1);

            JButton fightS1 = new JButton("Fight Scene");
            fightS1.setBounds(255, 20, 90, 60);
            usedFrame1.add(fightS1);

            JButton enemyS1 = new JButton("Enemy Side");
            enemyS1.setBounds(455, 20, 90, 60);
            usedFrame1.add(enemyS1);

            // On affiche les sprites des alliés

            JLabel[] imagePList1 = new JLabel[(p.getTeam().size())];
            for (int i = 0; i < p.getTeam().size(); i++) {
                imagePList1[i] = p.getTeam().get(i).getImageN();
            }

            for (int i = 0; i < imagePList1.length; i++) {
                int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                if (p.getTeam().get(i).getIsDead() == false) {
                    if (i == attacker) {
                        imagePList1[i].setBounds(305, (105 + target * 100), 90, 90);
                        usedFrame1.add(imagePList1[i]);
                        y = y + 100;
                    }
                    else {
                        imagePList1[i].setBounds(x, y, largeur, longueur);
                        usedFrame1.add(imagePList1[i]);
                        y = y + 100;
                    }
                }
            }

            JLabel[] imageEList1 = new JLabel[e.getEnemies().size()];
            for (int i = 0; i < e.getEnemies().size(); i++) {
                imageEList1[i] = e.getEnemies().get(i).getImageN();
            }

            for (int i = 0; i < imageEList1.length; i++) {
                int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                imageEList1[i].setBounds(x, y, largeur, longueur);
                usedFrame1.add(imageEList1[i]);
                y = y + 100;
            }

            JLabel combatEffect1 = new JLabel(new ImageIcon("Images/Hit.png"));
            combatEffect1.setBounds(395, 120 + (100 * target), 60, 60);
            usedFrame1.add(combatEffect1);

            String infoBattle1 = p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + "!";

            JLabel info1 = new JLabel(infoBattle1);
            menuBar1.add(info1);
            menuBar1.setBounds(0, 500, 600, 100);
            background1.add(menuBar1);
            usedFrame1.getContentPane().add(background1);
            usedFrame1.repaint();
            usedFrame1.revalidate();

            if ((int)(Math.random() * 100) > p.getTeam().get(attacker).getLUCK()) {

                JFrame usedFrame2 = mainFrame;
                usedFrame2.getContentPane().removeAll();
                usedFrame2.setLayout(null);

                GamePanel background2 = new GamePanel();
                background2.setSize(600, 600);
                background2.setLayout(null);

                JLabel backscreen2 = new JLabel(new ImageIcon(backgroundFile));
                backscreen2.setBounds(0, 0, 600, 500);
                background2.add(backscreen2);

                GamePanel menuBar2 = new GamePanel();
                menuBar2.setLayout(new FlowLayout());
                menuBar2.setSize(600, 100);
                menuBar2.setBackground(Color.WHITE);

                // Les boutons suivants permettent de se repérer pendant le combat

                JButton partyS2 = new JButton("Party Side");
                partyS2.setBounds(55, 20, 90, 60);
                usedFrame2.add(partyS2);

                JButton fightS2 = new JButton("Fight Scene");
                fightS2.setBounds(255, 20, 90, 60);
                usedFrame2.add(fightS2);

                JButton enemyS2 = new JButton("Enemy Side");
                enemyS2.setBounds(455, 20, 90, 60);
                usedFrame2.add(enemyS2);

                // On affiche les sprites des alliés

                JLabel[] imagePList2 = new JLabel[(p.getTeam().size())];
                for (int i = 0; i < p.getTeam().size(); i++) {
                    imagePList2[i] = p.getTeam().get(i).getImageN();
                }

                for (int i = 0; i < imagePList2.length; i++) {
                    int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                    if (p.getTeam().get(i).getIsDead() == false) {
                        if (i == attacker) {
                            imagePList2[i].setBounds(305, (105 + target * 100), 90, 90);
                            usedFrame2.add(imagePList2[i]);
                            y = y + 100;
                        }
                        else {
                            imagePList2[i].setBounds(x, y, largeur, longueur);
                            usedFrame2.add(imagePList2[i]);
                            y = y + 100;
                        }
                    }
                }

                JLabel[] imageEList2 = new JLabel[e.getEnemies().size()];
                for (int i = 0; i < e.getEnemies().size(); i++) {
                    imageEList2[i] = e.getEnemies().get(i).getImageN();
                }

                for (int i = 0; i < imageEList2.length; i++) {
                    int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                    imageEList2[i].setBounds(x, y, largeur, longueur);
                    usedFrame2.add(imageEList2[i]);
                    y = y + 100;
                }

                JLabel combatEffect2 = new JLabel(new ImageIcon("Images/Hit.png"));
                combatEffect2.setBounds(395, 120 + (100 * target), 60, 60);
                usedFrame2.add(combatEffect2);

                String infoBattle2 = p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + " for " + damage + "!";

                JLabel info2 = new JLabel(infoBattle2);
                menuBar2.add(info2);
                menuBar2.setBounds(0, 500, 600, 100);
                background2.add(menuBar2);
                usedFrame2.getContentPane().add(background2);
                usedFrame2.repaint();
                usedFrame2.revalidate();

                if (e.getEnemies().get(target).getActualHP() != 0) {

                    if ((int)(Math.random() * 100) > p.getTeam().get(attacker).getLUCK()) {

                        JFrame usedFrame3 = mainFrame;
                        usedFrame3.getContentPane().removeAll();
                        usedFrame3.setLayout(null);

                        GamePanel background3 = new GamePanel();
                        background3.setSize(600, 600);
                        background3.setLayout(null);

                        JLabel backscreen3 = new JLabel(new ImageIcon(backgroundFile));
                        backscreen3.setBounds(0, 0, 600, 500);
                        background3.add(backscreen3);

                        GamePanel menuBar3 = new GamePanel();
                        menuBar3.setLayout(new FlowLayout());
                        menuBar3.setSize(600, 100);
                        menuBar3.setBackground(Color.WHITE);

                        // Les boutons suivants permettent de se repérer pendant le combat

                        JButton partyS3 = new JButton("Party Side");
                        partyS3.setBounds(55, 20, 90, 60);
                        usedFrame3.add(partyS3);

                        JButton fightS3 = new JButton("Fight Scene");
                        fightS3.setBounds(255, 20, 90, 60);
                        usedFrame3.add(fightS3);

                        JButton enemyS3 = new JButton("Enemy Side");
                        enemyS3.setBounds(455, 20, 90, 60);
                        usedFrame3.add(enemyS3);

                        // On affiche les sprites des alliés

                        JLabel[] imagePList3 = new JLabel[(p.getTeam().size())];
                        for (int i = 0; i < p.getTeam().size(); i++) {
                            imagePList3[i] = p.getTeam().get(i).getImageN();
                        }

                        for (int i = 0; i < imagePList3.length; i++) {
                            int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                            if (p.getTeam().get(i).getIsDead() == false) {
                                if (i == attacker) {
                                    imagePList3[i].setBounds(305, (105 + target * 100), 90, 90);
                                    usedFrame3.add(imagePList3[i]);
                                    y = y + 100;
                                }
                                else {
                                    imagePList3[i].setBounds(x, y, largeur, longueur);
                                    usedFrame3.add(imagePList3[i]);
                                    y = y + 100;
                                }
                            }
                        }

                        JLabel[] imageEList3 = new JLabel[e.getEnemies().size()];
                        for (int i = 0; i < e.getEnemies().size(); i++) {
                            imageEList3[i] = e.getEnemies().get(i).getImageN();
                        }

                        for (int i = 0; i < imageEList3.length; i++) {
                            int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                            imageEList3[i].setBounds(x, y, largeur, longueur);
                            usedFrame3.add(imageEList3[i]);
                            y = y + 100;
                        }

                        JLabel combatEffect3 = new JLabel(new ImageIcon("Images/Hit.png"));
                        combatEffect3.setBounds(395, 120 + (100 * target), 60, 60);
                        usedFrame3.add(combatEffect3);

                        String infoBattle3 = p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + " again for " + damage + "!";

                        JLabel info3 = new JLabel(infoBattle3);
                        menuBar3.add(info3);
                        menuBar3.setBounds(0, 500, 600, 100);
                        background3.add(menuBar3);
                        usedFrame3.getContentPane().add(background3);
                        usedFrame3.repaint();
                        usedFrame3.revalidate();

                        if (e.getEnemies().get(target).getActualHP() == 0) {

                            JFrame usedFrame4 = mainFrame;
                            usedFrame4.getContentPane().removeAll();
                            usedFrame4.setLayout(null);
        
                            GamePanel background4 = new GamePanel();
                            background4.setSize(600, 600);
                            background4.setLayout(null);
        
                            JLabel backscreen4 = new JLabel(new ImageIcon(backgroundFile));
                            backscreen4.setBounds(0, 0, 600, 500);
                            background4.add(backscreen4);
        
                            GamePanel menuBar4 = new GamePanel();
                            menuBar4.setLayout(new FlowLayout());
                            menuBar4.setSize(600, 100);
                            menuBar4.setBackground(Color.WHITE);
        
                            // Les boutons suivants permettent de se repérer pendant le combat
        
                            JButton partyS4 = new JButton("Party Side");
                            partyS4.setBounds(55, 20, 90, 60);
                            usedFrame4.add(partyS4);
        
                            JButton fightS4 = new JButton("Fight Scene");
                            fightS4.setBounds(255, 20, 90, 60);
                            usedFrame4.add(fightS4);
        
                            JButton enemyS4 = new JButton("Enemy Side");
                            enemyS4.setBounds(455, 20, 90, 60);
                            usedFrame4.add(enemyS4);
        
                            // On affiche les sprites des alliés
        
                            JLabel[] imagePList4 = new JLabel[(p.getTeam().size())];
                            for (int i = 0; i < p.getTeam().size(); i++) {
                                imagePList4[i] = p.getTeam().get(i).getImageN();
                            }
        
                            for (int i = 0; i < imagePList4.length; i++) {
                                int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                                if (p.getTeam().get(i).getIsDead() == false) {
                                    if (i == attacker) {
                                        imagePList4[i].setBounds(305, (105 + target * 100), 90, 90);
                                        usedFrame4.add(imagePList4[i]);
                                        y = y + 100;
                                    }
                                    else {
                                        imagePList4[i].setBounds(x, y, largeur, longueur);
                                        usedFrame4.add(imagePList4[i]);
                                        y = y + 100;
                                    }
                                }
                            }
        
                            JLabel[] imageEList4 = new JLabel[e.getEnemies().size()];
                            for (int i = 0; i < e.getEnemies().size(); i++) {
                                imageEList4[i] = e.getEnemies().get(i).getImageN();
                            }
        
                            for (int i = 0; i < imageEList4.length; i++) {
                                int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                                if (i != target) {
                                    imageEList4[i].setBounds(x, y, largeur, longueur);
                                    usedFrame4.add(imageEList4[i]);
                                    y = y + 100;
                                }
                                else {
                                    y = y + 100;
                                }
                            }
        
                            String infoBattle4 = e.getEnemies().get(target).getName() + " has been defeated! " + p.getTeam().get(attacker).getName() + " has earned " + e.getEnemies().get(target).getExpDrop() + " experience points!";
                            // Ajouter la nouvelle fonction pour les montées de niveau
                            e.deleteMob(e.getEnemies().get(target));
        
                            JLabel info4 = new JLabel(infoBattle4);
                            menuBar4.add(info4);
                            menuBar4.setBounds(0, 500, 600, 100);
                            background4.add(menuBar4);
                            usedFrame4.getContentPane().add(background4);
                            usedFrame4.repaint();
                            usedFrame4.revalidate();

                            f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                        }
                        else {

                            JFrame usedFrame4 = mainFrame;
                            usedFrame4.getContentPane().removeAll();
                            usedFrame4.setLayout(null);
        
                            GamePanel background4 = new GamePanel();
                            background4.setSize(600, 600);
                            background4.setLayout(null);
        
                            JLabel backscreen4 = new JLabel(new ImageIcon(backgroundFile));
                            backscreen4.setBounds(0, 0, 600, 500);
                            background4.add(backscreen4);
        
                            GamePanel menuBar4 = new GamePanel();
                            menuBar4.setLayout(new FlowLayout());
                            menuBar4.setSize(600, 100);
                            menuBar4.setBackground(Color.WHITE);
        
                            // Les boutons suivants permettent de se repérer pendant le combat
        
                            JButton partyS4 = new JButton("Party Side");
                            partyS4.setBounds(55, 20, 90, 60);
                            usedFrame4.add(partyS4);
        
                            JButton fightS4 = new JButton("Fight Scene");
                            fightS4.setBounds(255, 20, 90, 60);
                            usedFrame4.add(fightS4);
        
                            JButton enemyS4 = new JButton("Enemy Side");
                            enemyS4.setBounds(455, 20, 90, 60);
                            usedFrame4.add(enemyS4);
        
                            // On affiche les sprites des alliés
        
                            JLabel[] imagePList4 = new JLabel[(p.getTeam().size())];
                            for (int i = 0; i < p.getTeam().size(); i++) {
                                imagePList4[i] = p.getTeam().get(i).getImageN();
                            }
        
                            for (int i = 0; i < imagePList4.length; i++) {
                                int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                                if (p.getTeam().get(i).getIsDead() == false) {
                                    if (i == attacker) {
                                        imagePList4[i].setBounds(305, (105 + target * 100), 90, 90);
                                        usedFrame4.add(imagePList4[i]);
                                        y = y + 100;
                                    }
                                    else {
                                        imagePList4[i].setBounds(x, y, largeur, longueur);
                                        usedFrame4.add(imagePList4[i]);
                                        y = y + 100;
                                    }
                                }
                            }
        
                            JLabel[] imageEList4 = new JLabel[e.getEnemies().size()];
                            for (int i = 0; i < e.getEnemies().size(); i++) {
                                imageEList4[i] = e.getEnemies().get(i).getImageN();
                            }
        
                            for (int i = 0; i < imageEList3.length; i++) {
                                int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                                imageEList4[i].setBounds(x, y, largeur, longueur);
                                usedFrame4.add(imageEList4[i]);
                                y = y + 100;
                            }
        
                            String infoBattle4 = "Waiting for the next turn...";
        
                            JLabel info4 = new JLabel(infoBattle4);
                            menuBar4.add(info4);
                            menuBar4.setBounds(0, 500, 600, 100);
                            background4.add(menuBar4);
                            usedFrame4.getContentPane().add(background4);
                            usedFrame4.repaint();
                            usedFrame4.revalidate();
        
                            f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                        }
                    }
                    
                    else {

                        JFrame usedFrame3 = mainFrame;
                        usedFrame3.getContentPane().removeAll();
                        usedFrame3.setLayout(null);

                        GamePanel background3 = new GamePanel();
                        background3.setSize(600, 600);
                        background3.setLayout(null);

                        JLabel backscreen3 = new JLabel(new ImageIcon(backgroundFile));
                        backscreen3.setBounds(0, 0, 600, 500);
                        background3.add(backscreen3);

                        GamePanel menuBar3 = new GamePanel();
                        menuBar3.setLayout(new FlowLayout());
                        menuBar3.setSize(600, 100);
                        menuBar3.setBackground(Color.WHITE);

                        // Les boutons suivants permettent de se repérer pendant le combat

                        JButton partyS3 = new JButton("Party Side");
                        partyS3.setBounds(55, 20, 90, 60);
                        usedFrame3.add(partyS3);

                        JButton fightS3 = new JButton("Fight Scene");
                        fightS3.setBounds(255, 20, 90, 60);
                        usedFrame3.add(fightS3);

                        JButton enemyS3 = new JButton("Enemy Side");
                        enemyS3.setBounds(455, 20, 90, 60);
                        usedFrame3.add(enemyS3);

                        // On affiche les sprites des alliés

                        JLabel[] imagePList3 = new JLabel[(p.getTeam().size())];
                        for (int i = 0; i < p.getTeam().size(); i++) {
                            imagePList3[i] = p.getTeam().get(i).getImageN();
                        }

                        for (int i = 0; i < imagePList3.length; i++) {
                            int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                            if (p.getTeam().get(i).getIsDead() == false) {
                                if (i == attacker) {
                                    imagePList3[i].setBounds(305, (105 + target * 100), 90, 90);
                                    usedFrame3.add(imagePList3[i]);
                                    y = y + 100;
                                }
                                else {
                                    imagePList3[i].setBounds(x, y, largeur, longueur);
                                    usedFrame3.add(imagePList3[i]);
                                    y = y + 100;
                                }
                            }
                        }

                        JLabel[] imageEList3 = new JLabel[e.getEnemies().size()];
                        for (int i = 0; i < e.getEnemies().size(); i++) {
                            imageEList3[i] = e.getEnemies().get(i).getImageN();
                        }

                        for (int i = 0; i < imageEList3.length; i++) {
                            int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                            imageEList3[i].setBounds(x, y, largeur, longueur);
                            usedFrame3.add(imageEList3[i]);
                            y = y + 100;
                        }

                        JLabel combatEffect3 = new JLabel(new ImageIcon("Images/Hit.png"));
                        combatEffect3.setBounds(395, 120 + (100 * target), 60, 60);
                        usedFrame3.add(combatEffect3);

                        String infoBattle3 = p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + " again for " + damage + "!";

                        JLabel info3 = new JLabel(infoBattle3);
                        menuBar3.add(info3);
                        menuBar3.setBounds(0, 500, 600, 100);
                        background3.add(menuBar3);
                        usedFrame3.getContentPane().add(background3);
                        usedFrame3.repaint();
                        usedFrame3.revalidate();
                    }
                }
                else {

                    JFrame usedFrame4 = mainFrame;
                    usedFrame4.getContentPane().removeAll();
                    usedFrame4.setLayout(null);
        
                    GamePanel background4 = new GamePanel();
                    background4.setSize(600, 600);
                    background4.setLayout(null);
        
                    JLabel backscreen4 = new JLabel(new ImageIcon(backgroundFile));
                    backscreen4.setBounds(0, 0, 600, 500);
                    background4.add(backscreen4);
        
                    GamePanel menuBar4 = new GamePanel();
                    menuBar4.setLayout(new FlowLayout());
                    menuBar4.setSize(600, 100);
                    menuBar4.setBackground(Color.WHITE);
        
                    // Les boutons suivants permettent de se repérer pendant le combat
        
                    JButton partyS4 = new JButton("Party Side");
                    partyS4.setBounds(55, 20, 90, 60);
                    usedFrame4.add(partyS4);
        
                    JButton fightS4 = new JButton("Fight Scene");
                    fightS4.setBounds(255, 20, 90, 60);
                    usedFrame4.add(fightS4);
        
                    JButton enemyS4 = new JButton("Enemy Side");
                    enemyS4.setBounds(455, 20, 90, 60);
                    usedFrame4.add(enemyS4);
        
                    // On affiche les sprites des alliés
        
                    JLabel[] imagePList4 = new JLabel[(p.getTeam().size())];
                    for (int i = 0; i < p.getTeam().size(); i++) {
                        imagePList4[i] = p.getTeam().get(i).getImageN();
                    }
        
                    for (int i = 0; i < imagePList4.length; i++) {
                        int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                        if (p.getTeam().get(i).getIsDead() == false) {
                            if (i == attacker) {
                            imagePList4[i].setBounds(305, (105 + target * 100), 90, 90);
                            usedFrame4.add(imagePList4[i]);
                            y = y + 100;
                            }
                            else {
                            imagePList4[i].setBounds(x, y, largeur, longueur);
                            usedFrame4.add(imagePList4[i]);
                            y = y + 100;
                            }
                        }
                    }
        
                    JLabel[] imageEList4 = new JLabel[e.getEnemies().size()];
                    for (int i = 0; i < e.getEnemies().size(); i++) {
                        imageEList4[i] = e.getEnemies().get(i).getImageN();
                    }
        
                    for (int i = 0; i < imageEList4.length; i++) {
                        int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                        if (i != target) {
                            imageEList4[i].setBounds(x, y, largeur, longueur);
                            usedFrame4.add(imageEList4[i]);
                              y = y + 100;
                        }
                        else {
                            y = y + 100;
                        }
                    }
        
                    String infoBattle4 = e.getEnemies().get(target).getName() + " has been defeated! " + p.getTeam().get(attacker).getName() + " has earned " + e.getEnemies().get(target).getExpDrop() + " experience points!";
                    // Ajouter la nouvelle fonction pour les montées de niveau
                    e.deleteMob(e.getEnemies().get(target));
        
                    JLabel info4 = new JLabel(infoBattle4);
                    menuBar4.add(info4);
                    menuBar4.setBounds(0, 500, 600, 100);
                    background4.add(menuBar4);
                    usedFrame4.getContentPane().add(background4);
                    usedFrame4.repaint();
                    usedFrame4.revalidate();

                    f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                }
            }

        }
        else {

            System.out.print("Inferior speed");

            JFrame usedFrame1 = mainFrame;
            usedFrame1.getContentPane().removeAll();
            usedFrame1.setLayout(null);

            GamePanel background1 = new GamePanel();
            background1.setSize(600, 600);
            background1.setLayout(null);

            JLabel backscreen1 = new JLabel(new ImageIcon(backgroundFile));
            backscreen1.setBounds(0, 0, 600, 500);
            background1.add(backscreen1);

            GamePanel menuBar1 = new GamePanel();
            menuBar1.setLayout(new FlowLayout());
            menuBar1.setSize(600, 100);
            menuBar1.setBackground(Color.WHITE);

            // Les boutons suivants permettent de se repérer pendant le combat

            JButton partyS1 = new JButton("Party Side");
            partyS1.setBounds(55, 20, 90, 60);
            usedFrame1.add(partyS1);

            JButton fightS1 = new JButton("Fight Scene");
            fightS1.setBounds(255, 20, 90, 60);
            usedFrame1.add(fightS1);

            JButton enemyS1 = new JButton("Enemy Side");
            enemyS1.setBounds(455, 20, 90, 60);
            usedFrame1.add(enemyS1);

            // On affiche les sprites des alliés

            JLabel[] imagePList1 = new JLabel[(p.getTeam().size())];
            for (int i = 0; i < p.getTeam().size(); i++) {
                imagePList1[i] = p.getTeam().get(i).getImageN();
            }

            for (int i = 0; i < imagePList1.length; i++) {
                int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                if (p.getTeam().get(i).getIsDead() == false) {
                    if (i == attacker) {
                        imagePList1[i].setBounds(305, (105 + target * 100), 90, 90);
                        usedFrame1.add(imagePList1[i]);
                        y = y + 100;
                    }
                    else {
                        imagePList1[i].setBounds(x, y, largeur, longueur);
                        usedFrame1.add(imagePList1[i]);
                        y = y + 100;
                    }
                }
            }

            JLabel[] imageEList1 = new JLabel[e.getEnemies().size()];
            for (int i = 0; i < e.getEnemies().size(); i++) {
                imageEList1[i] = e.getEnemies().get(i).getImageN();
            }

            for (int i = 0; i < imageEList1.length; i++) {
                int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                imageEList1[i].setBounds(x, y, largeur, longueur);
                usedFrame1.add(imageEList1[i]);
                y = y + 100;
            }

            JLabel combatEffect1 = new JLabel(new ImageIcon("Images/Hit.png"));
            combatEffect1.setBounds(395, 120 + (100 * target), 60, 60);
            usedFrame1.add(combatEffect1);

            String infoBattle1 = p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + "!";

            JLabel info1 = new JLabel(infoBattle1);
            menuBar1.add(info1);
            menuBar1.setBounds(0, 500, 600, 100);
            background1.add(menuBar1);
            usedFrame1.getContentPane().add(background1);
            usedFrame1.repaint();
            usedFrame1.revalidate();
            
            if ((int)(Math.random() * 100) > p.getTeam().get(attacker).getLUCK()) {

                int damage2 = damage;
                e.getEnemies().get(target).setActualHP(Math.max(0, e.getEnemies().get(target).getActualHP() - damage2));

                JFrame usedFrame2 = mainFrame;
                usedFrame2.getContentPane().removeAll();
                usedFrame2.setLayout(null);

                GamePanel background2 = new GamePanel();
                background2.setSize(600, 600);
                background2.setLayout(null);

                JLabel backscreen2 = new JLabel(new ImageIcon(backgroundFile));
                backscreen2.setBounds(0, 0, 600, 500);
                background2.add(backscreen2);

                GamePanel menuBar2 = new GamePanel();
                menuBar2.setLayout(new FlowLayout());
                menuBar2.setSize(600, 100);
                menuBar2.setBackground(Color.WHITE);

                // Les boutons suivants permettent de se repérer pendant le combat

                JButton partyS2 = new JButton("Party Side");
                partyS2.setBounds(55, 20, 90, 60);
                usedFrame2.add(partyS2);

                JButton fightS2 = new JButton("Fight Scene");
                fightS2.setBounds(255, 20, 90, 60);
                usedFrame2.add(fightS2);

                JButton enemyS2 = new JButton("Enemy Side");
                enemyS2.setBounds(455, 20, 90, 60);
                usedFrame2.add(enemyS2);

                // On affiche les sprites des alliés

                JLabel[] imagePList2 = new JLabel[(p.getTeam().size())];
                for (int i = 0; i < p.getTeam().size(); i++) {
                    imagePList2[i] = p.getTeam().get(i).getImageN();
                }

                for (int i = 0; i < imagePList2.length; i++) {
                    int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                    if (p.getTeam().get(i).getIsDead() == false) {
                        if (i == attacker) {
                            imagePList2[i].setBounds(305, (105 + target * 100), 90, 90);
                            usedFrame2.add(imagePList2[i]);
                            y = y + 100;
                        }
                        else {
                            imagePList2[i].setBounds(x, y, largeur, longueur);
                            usedFrame2.add(imagePList2[i]);
                            y = y + 100;
                        }
                    }
                }

                JLabel[] imageEList2 = new JLabel[e.getEnemies().size()];
                for (int i = 0; i < e.getEnemies().size(); i++) {
                    imageEList2[i] = e.getEnemies().get(i).getImageN();
                }

                for (int i = 0; i < imageEList2.length; i++) {
                    int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                    imageEList2[i].setBounds(x, y, largeur, longueur);
                    usedFrame2.add(imageEList2[i]);
                    y = y + 100;
                }

                JLabel combatEffect2 = new JLabel(new ImageIcon("Images/Hit.png"));
                combatEffect2.setBounds(395, 120 + (100 * target), 60, 60);
                usedFrame2.add(combatEffect2);

                String infoBattle2 = p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + " for " + damage + "!";

                JLabel info2 = new JLabel(infoBattle2);
                menuBar2.add(info2);
                menuBar2.setBounds(0, 500, 600, 100);
                background2.add(menuBar2);
                usedFrame2.getContentPane().add(background2);
                usedFrame2.repaint();
                usedFrame2.revalidate();

                if (e.getEnemies().get(target).getActualHP() == 0) {

                    JFrame usedFrame3 = mainFrame;
                    usedFrame3.getContentPane().removeAll();
                    usedFrame3.setLayout(null);

                    GamePanel background3 = new GamePanel();
                    background3.setSize(600, 600);
                    background3.setLayout(null);

                    JLabel backscreen3 = new JLabel(new ImageIcon(backgroundFile));
                    backscreen3.setBounds(0, 0, 600, 500);
                    background3.add(backscreen3);

                    GamePanel menuBar3 = new GamePanel();
                    menuBar3.setLayout(new FlowLayout());
                    menuBar3.setSize(600, 100);
                    menuBar3.setBackground(Color.WHITE);

                    // Les boutons suivants permettent de se repérer pendant le combat

                    JButton partyS3 = new JButton("Party Side");
                    partyS3.setBounds(55, 20, 90, 60);
                    usedFrame3.add(partyS3);

                    JButton fightS3 = new JButton("Fight Scene");
                    fightS3.setBounds(255, 20, 90, 60);
                    usedFrame3.add(fightS3);

                    JButton enemyS3 = new JButton("Enemy Side");
                    enemyS3.setBounds(455, 20, 90, 60);
                    usedFrame3.add(enemyS3);

                    // On affiche les sprites des alliés

                    JLabel[] imagePList3 = new JLabel[(p.getTeam().size())];
                    for (int i = 0; i < p.getTeam().size(); i++) {
                        imagePList3[i] = p.getTeam().get(i).getImageN();
                    }

                    for (int i = 0; i < imagePList3.length; i++) {
                        int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                        if (p.getTeam().get(i).getIsDead() == false) {
                            if (i == attacker) {
                                imagePList3[i].setBounds(305, (105 + target * 100), 90, 90);
                                usedFrame3.add(imagePList3[i]);
                                y = y + 100;
                            }
                            else {
                                imagePList3[i].setBounds(x, y, largeur, longueur);
                                usedFrame3.add(imagePList3[i]);
                                y = y + 100;
                            }
                        }
                    }

                    JLabel[] imageEList3 = new JLabel[e.getEnemies().size()];
                    for (int i = 0; i < e.getEnemies().size(); i++) {
                        imageEList3[i] = e.getEnemies().get(i).getImageN();
                    }

                    for (int i = 0; i < imageEList3.length; i++) {
                        int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                        if (i != target) {
                            imageEList3[i].setBounds(x, y, largeur, longueur);
                            usedFrame3.add(imageEList3[i]);
                            y = y + 100;
                        }
                        else {
                            y = y + 100;
                        }
                    }

                    String infoBattle3 = e.getEnemies().get(target).getName() + " has been defeated! " + p.getTeam().get(attacker).getName() + " has earned " + e.getEnemies().get(target).getExpDrop() + " experience points!";
                    // Ajouter la nouvelle fonction pour les montées de niveau
                    e.deleteMob(e.getEnemies().get(target));

                    JLabel info3 = new JLabel(infoBattle3);
                    menuBar3.add(info3);
                    menuBar3.setBounds(0, 500, 600, 100);
                    background3.add(menuBar3);
                    usedFrame3.getContentPane().add(background3);
                    usedFrame3.repaint();
                    usedFrame3.revalidate();

                    f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                }
                else {

                    JFrame usedFrame3 = mainFrame;
                    usedFrame3.getContentPane().removeAll();
                    usedFrame3.setLayout(null);

                    GamePanel background3 = new GamePanel();
                    background3.setSize(600, 600);
                    background3.setLayout(null);

                    JLabel backscreen3 = new JLabel(new ImageIcon(backgroundFile));
                    backscreen3.setBounds(0, 0, 600, 500);
                    background3.add(backscreen3);

                    GamePanel menuBar3 = new GamePanel();
                    menuBar3.setLayout(new FlowLayout());
                    menuBar3.setSize(600, 100);
                    menuBar3.setBackground(Color.WHITE);

                    // Les boutons suivants permettent de se repérer pendant le combat

                    JButton partyS3 = new JButton("Party Side");
                    partyS3.setBounds(55, 20, 90, 60);
                    usedFrame3.add(partyS3);

                    JButton fightS3 = new JButton("Fight Scene");
                    fightS3.setBounds(255, 20, 90, 60);
                    usedFrame3.add(fightS3);

                    JButton enemyS3 = new JButton("Enemy Side");
                    enemyS3.setBounds(455, 20, 90, 60);
                    usedFrame3.add(enemyS3);

                    // On affiche les sprites des alliés

                    JLabel[] imagePList3 = new JLabel[(p.getTeam().size())];
                    for (int i = 0; i < p.getTeam().size(); i++) {
                        imagePList3[i] = p.getTeam().get(i).getImageN();
                    }

                    for (int i = 0; i < imagePList3.length; i++) {
                        int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                        if (p.getTeam().get(i).getIsDead() == false) {
                            if (i == attacker) {
                                imagePList3[i].setBounds(305, (105 + target * 100), 90, 90);
                                usedFrame3.add(imagePList3[i]);
                                y = y + 100;
                            }
                            else {
                                imagePList3[i].setBounds(x, y, largeur, longueur);
                                usedFrame3.add(imagePList3[i]);
                                y = y + 100;
                            }
                        }
                    }

                    JLabel[] imageEList3 = new JLabel[e.getEnemies().size()];
                    for (int i = 0; i < e.getEnemies().size(); i++) {
                        imageEList3[i] = e.getEnemies().get(i).getImageN();
                    }

                    for (int i = 0; i < imageEList3.length; i++) {
                        int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                        imageEList3[i].setBounds(x, y, largeur, longueur);
                        usedFrame3.add(imageEList3[i]);
                        y = y + 100;
                    }

                    String infoBattle3 = "Waiting for the next turn...";

                    JLabel info3 = new JLabel(infoBattle3);
                    menuBar3.add(info3);
                    menuBar3.setBounds(0, 500, 600, 100);
                    background3.add(menuBar3);
                    usedFrame3.getContentPane().add(background3);
                    usedFrame3.repaint();
                    usedFrame3.revalidate();

                    f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                }
            }
            else {

                int damage2 = damage * 3;
                e.getEnemies().get(target).setActualHP(Math.max(0, e.getEnemies().get(target).getActualHP() - damage2));

                JFrame usedFrame2 = mainFrame;
                usedFrame2.getContentPane().removeAll();
                usedFrame2.setLayout(null);

                GamePanel background2 = new GamePanel();
                background2.setSize(600, 600);
                background2.setLayout(null);

                JLabel backscreen2 = new JLabel(new ImageIcon(backgroundFile));
                backscreen2.setBounds(0, 0, 600, 500);
                background2.add(backscreen2);

                GamePanel menuBar2 = new GamePanel();
                menuBar2.setLayout(new FlowLayout());
                menuBar2.setSize(600, 100);
                menuBar2.setBackground(Color.WHITE);

                // Les boutons suivants permettent de se repérer pendant le combat

                JButton partyS2 = new JButton("Party Side");
                partyS2.setBounds(55, 20, 90, 60);
                usedFrame2.add(partyS2);

                JButton fightS2 = new JButton("Fight Scene");
                fightS2.setBounds(255, 20, 90, 60);
                usedFrame2.add(fightS2);

                JButton enemyS2 = new JButton("Enemy Side");
                enemyS2.setBounds(455, 20, 90, 60);
                usedFrame2.add(enemyS2);

                // On affiche les sprites des alliés

                JLabel[] imagePList2 = new JLabel[(p.getTeam().size())];
                for (int i = 0; i < p.getTeam().size(); i++) {
                    imagePList2[i] = p.getTeam().get(i).getImageN();
                }

                for (int i = 0; i < imagePList2.length; i++) {
                    int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                    if (p.getTeam().get(i).getIsDead() == false) {
                        if (i == attacker) {
                            imagePList2[i].setBounds(305, (105 + target * 100), 90, 90);
                            usedFrame2.add(imagePList2[i]);
                            y = y + 100;
                        }
                        else {
                            imagePList2[i].setBounds(x, y, largeur, longueur);
                            usedFrame2.add(imagePList2[i]);
                            y = y + 100;
                        }
                    }
                }

                JLabel[] imageEList2 = new JLabel[e.getEnemies().size()];
                for (int i = 0; i < e.getEnemies().size(); i++) {
                    imageEList2[i] = e.getEnemies().get(i).getImageN();
                }

                for (int i = 0; i < imageEList2.length; i++) {
                    int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                    imageEList2[i].setBounds(x, y, largeur, longueur);
                    usedFrame2.add(imageEList2[i]);
                    y = y + 100;
                }

                JLabel combatEffect2 = new JLabel(new ImageIcon("Images/Hit.png"));
                combatEffect2.setBounds(395, 120 + (100 * target), 60, 60);
                usedFrame2.add(combatEffect2);

                String infoBattle2 = "CRITICAL HIT!!! " + p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + " for " + damage + "!";

                JLabel info2 = new JLabel(infoBattle2);
                menuBar2.add(info2);
                menuBar2.setBounds(0, 500, 600, 100);
                background2.add(menuBar2);
                usedFrame2.getContentPane().add(background2);
                usedFrame2.repaint();
                usedFrame2.revalidate();

                if (e.getEnemies().get(target).getActualHP() == 0) {

                    JFrame usedFrame3 = mainFrame;
                    usedFrame3.getContentPane().removeAll();
                    usedFrame3.setLayout(null);

                    GamePanel background3 = new GamePanel();
                    background3.setSize(600, 600);
                    background3.setLayout(null);

                    JLabel backscreen3 = new JLabel(new ImageIcon(backgroundFile));
                    backscreen3.setBounds(0, 0, 600, 500);
                    background3.add(backscreen3);

                    GamePanel menuBar3 = new GamePanel();
                    menuBar3.setLayout(new FlowLayout());
                    menuBar3.setSize(600, 100);
                    menuBar3.setBackground(Color.WHITE);

                    // Les boutons suivants permettent de se repérer pendant le combat

                    JButton partyS3 = new JButton("Party Side");
                    partyS3.setBounds(55, 20, 90, 60);
                    usedFrame3.add(partyS3);

                    JButton fightS3 = new JButton("Fight Scene");
                    fightS3.setBounds(255, 20, 90, 60);
                    usedFrame3.add(fightS3);

                    JButton enemyS3 = new JButton("Enemy Side");
                    enemyS3.setBounds(455, 20, 90, 60);
                    usedFrame3.add(enemyS3);

                    // On affiche les sprites des alliés

                    JLabel[] imagePList3 = new JLabel[(p.getTeam().size())];
                    for (int i = 0; i < p.getTeam().size(); i++) {
                        imagePList3[i] = p.getTeam().get(i).getImageN();
                    }

                    for (int i = 0; i < imagePList3.length; i++) {
                        int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                        if (p.getTeam().get(i).getIsDead() == false) {
                            if (i == attacker) {
                                imagePList3[i].setBounds(305, (105 + target * 100), 90, 90);
                                usedFrame3.add(imagePList3[i]);
                                y = y + 100;
                            }
                            else {
                                imagePList3[i].setBounds(x, y, largeur, longueur);
                                usedFrame3.add(imagePList3[i]);
                                y = y + 100;
                            }
                        }
                    }

                    JLabel[] imageEList3 = new JLabel[e.getEnemies().size()];
                    for (int i = 0; i < e.getEnemies().size(); i++) {
                        imageEList3[i] = e.getEnemies().get(i).getImageN();
                    }

                    for (int i = 0; i < imageEList3.length; i++) {
                        int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                        if (i != target) {
                            imageEList3[i].setBounds(x, y, largeur, longueur);
                            usedFrame3.add(imageEList3[i]);
                            y = y + 100;
                        }
                        else {
                            y = y + 100;
                        }
                    }

                    String infoBattle3 = e.getEnemies().get(target).getName() + " has been defeated! " + p.getTeam().get(attacker).getName() + " has earned " + e.getEnemies().get(target).getExpDrop() + " experience points!";
                    // Ajouter la nouvelle fonction pour les montées de niveau
                    e.deleteMob(e.getEnemies().get(target));

                    JLabel info3 = new JLabel(infoBattle3);
                    menuBar3.add(info3);
                    menuBar3.setBounds(0, 500, 600, 100);
                    background3.add(menuBar3);
                    usedFrame3.getContentPane().add(background3);
                    usedFrame3.repaint();
                    usedFrame3.revalidate();

                    f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                }
                else {

                    JFrame usedFrame3 = mainFrame;
                    usedFrame3.getContentPane().removeAll();
                    usedFrame3.setLayout(null);

                    GamePanel background3 = new GamePanel();
                    background3.setSize(600, 600);
                    background3.setLayout(null);

                    JLabel backscreen3 = new JLabel(new ImageIcon(backgroundFile));
                    backscreen3.setBounds(0, 0, 600, 500);
                    background3.add(backscreen3);

                    GamePanel menuBar3 = new GamePanel();
                    menuBar3.setLayout(new FlowLayout());
                    menuBar3.setSize(600, 100);
                    menuBar3.setBackground(Color.WHITE);

                    // Les boutons suivants permettent de se repérer pendant le combat

                    JButton partyS3 = new JButton("Party Side");
                    partyS3.setBounds(55, 20, 90, 60);
                    usedFrame3.add(partyS3);

                    JButton fightS3 = new JButton("Fight Scene");
                    fightS3.setBounds(255, 20, 90, 60);
                    usedFrame3.add(fightS3);

                    JButton enemyS3 = new JButton("Enemy Side");
                    enemyS3.setBounds(455, 20, 90, 60);
                    usedFrame3.add(enemyS3);

                    // On affiche les sprites des alliés

                    JLabel[] imagePList3 = new JLabel[(p.getTeam().size())];
                    for (int i = 0; i < p.getTeam().size(); i++) {
                        imagePList3[i] = p.getTeam().get(i).getImageN();
                    }

                    for (int i = 0; i < imagePList3.length; i++) {
                        int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                        if (p.getTeam().get(i).getIsDead() == false) {
                            if (i == attacker) {
                                imagePList3[i].setBounds(305, (105 + target * 100), 90, 90);
                                usedFrame3.add(imagePList3[i]);
                                y = y + 100;
                            }
                            else {
                                imagePList3[i].setBounds(x, y, largeur, longueur);
                                usedFrame3.add(imagePList3[i]);
                                y = y + 100;
                            }
                        }
                    }

                    JLabel[] imageEList3 = new JLabel[e.getEnemies().size()];
                    for (int i = 0; i < e.getEnemies().size(); i++) {
                        imageEList3[i] = e.getEnemies().get(i).getImageN();
                    }

                    for (int i = 0; i < imageEList3.length; i++) {
                        int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                        imageEList3[i].setBounds(x, y, largeur, longueur);
                        usedFrame3.add(imageEList3[i]);
                        y = y + 100;
                    }

                    String infoBattle3 = "Waiting for the next turn...";

                    JLabel info3 = new JLabel(infoBattle3);
                    menuBar3.add(info3);
                    menuBar3.setBounds(0, 500, 600, 100);
                    background3.add(menuBar3);
                    usedFrame3.getContentPane().add(background3);
                    usedFrame3.repaint();
                    usedFrame3.revalidate();

                    f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                }
            }
        }
    }

    // Mise en place de l'attaque

    public static void attackPrep(JFrame mainFrame, String backgroundFile, int attacker, int target, Party p, Enemies e, Fight f) {
        Character a = p.getTeam().get(attacker);
        Mob t = e.getEnemies().get(target);

        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();
        usedFrame.setLayout(null);

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

        // On affiche les sprites des alliés

        JLabel[] imagePList = new JLabel[(p.getTeam().size())];
        for (int i = 0; i < p.getTeam().size(); i++) {
            imagePList[i] = p.getTeam().get(i).getImageN();
        }

        for (int i = 0; i < imagePList.length; i++) {
            int x = 55; int y = 105; int largeur = 90; int longueur = 90;
            if (p.getTeam().get(i).getIsDead() == false) {
                if (i == attacker) {
                    imagePList[i].setBounds(305, (105 + target * 100), 90, 90);
                    usedFrame.add(imagePList[i]);
                    y = y + 100;
                }
                else {
                    imagePList[i].setBounds(x, y, largeur, longueur);
                    usedFrame.add(imagePList[i]);
                    y = y + 100;
                }
            }
        }

        JLabel[] imageEList = new JLabel[e.getEnemies().size()];
        for (int i = 0; i < e.getEnemies().size(); i++) {
            imageEList[i] = e.getEnemies().get(i).getImageN();
        }

        for (int i = 0; i < imageEList.length; i++) {
            int x = 455; int y = 105; int largeur = 90; int longueur = 90;
            imageEList[i].setBounds(x, y, largeur, longueur);
            usedFrame.add(imageEList[i]);
            y = y + 100;
        }

        String infoBattle = a.getName() + " attacks " + t.getName() + "!";

        JLabel info = new JLabel(infoBattle);

        JButton continueButton = new JButton("Continue ...");
        continueButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                f.attaque(mainFrame, backgroundFile, attacker, target, p, e, f);
            }
        });
        menuBar.add(continueButton);

        menuBar.add(info);
        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    } 

    // Fonction en cas de double attaque

    public static void attaque(JFrame mainFrame, String backgroundFile, int attacker, int target, Party p, Enemies e, Fight f) {
        Character a = p.getTeam().get(attacker);
        Mob t = e.getEnemies().get(target);

        if ((int)(Math.random() * 100) > p.getTeam().get(attacker).getLUCK()) {
            JFrame usedFrame = mainFrame;
            usedFrame.getContentPane().removeAll();
            usedFrame.setLayout(null);

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

            // On affiche les sprites des alliés

            JLabel[] imagePList = new JLabel[(p.getTeam().size())];
            for (int i = 0; i < p.getTeam().size(); i++) {
                imagePList[i] = p.getTeam().get(i).getImageN();
            }

            for (int i = 0; i < imagePList.length; i++) {
                int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                if (p.getTeam().get(i).getIsDead() == false) {
                    if (i == attacker) {
                        imagePList[i].setBounds(305, (105 + target * 100), 90, 90);
                        usedFrame.add(imagePList[i]);
                        y = y + 100;
                    }
                    else {
                        imagePList[i].setBounds(x, y, largeur, longueur);
                        usedFrame.add(imagePList[i]);
                        y = y + 100;
                    }
                }
            }

            JLabel[] imageEList = new JLabel[e.getEnemies().size()];
            for (int i = 0; i < e.getEnemies().size(); i++) {
                imageEList[i] = e.getEnemies().get(i).getImageN();
            }

            for (int i = 0; i < imageEList.length; i++) {
                int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                imageEList[i].setBounds(x, y, largeur, longueur);
                usedFrame.add(imageEList[i]);
                y = y + 100;
            }

            JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit.png"));
            combatEffect.setBounds(395, 120 + (100 * target), 60, 60);
            usedFrame.add(combatEffect);

            int damage = Math.max(a.getATK() - t.getDEF(), 0);

            String infoBattle = a.getName() + " attacks " + t.getName() + " for " + damage + "damages!";
            t.setActualHP(Math.max(0, t.getActualHP() - damage));

            JLabel info = new JLabel(infoBattle);

            JButton continueButton = new JButton("Continue ...");
            continueButton.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (t.getActualHP() == 0) defeatedEnemy(mainFrame, backgroundFile, attacker, target, p, e, f);
                    else {
                        if (a.getSPEED() >= t.getSPEED() + 5) {
                            f.attaque(mainFrame, backgroundFile, attacker, target, p, e, f);
                        }
                        else {
                            f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                        }
                    }
                }
            });
            menuBar.add(continueButton);

            menuBar.add(info);
            menuBar.setBounds(0, 500, 600, 100);
            background.add(menuBar);
            usedFrame.getContentPane().add(background);
            usedFrame.repaint();
            usedFrame.revalidate();
        }
        else {
            JFrame usedFrame = mainFrame;
            usedFrame.getContentPane().removeAll();
            usedFrame.setLayout(null);

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

            // On affiche les sprites des alliés

            JLabel[] imagePList = new JLabel[(p.getTeam().size())];
            for (int i = 0; i < p.getTeam().size(); i++) {
                imagePList[i] = p.getTeam().get(i).getImageN();
            }

            for (int i = 0; i < imagePList.length; i++) {
                int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                if (p.getTeam().get(i).getIsDead() == false) {
                    if (i == attacker) {
                        imagePList[i].setBounds(305, (105 + target * 100), 90, 90);
                        usedFrame.add(imagePList[i]);
                        y = y + 100;
                    }
                    else {
                        imagePList[i].setBounds(x, y, largeur, longueur);
                        usedFrame.add(imagePList[i]);
                        y = y + 100;
                    }
                }
            }

            JLabel[] imageEList = new JLabel[e.getEnemies().size()];
            for (int i = 0; i < e.getEnemies().size(); i++) {
                imageEList[i] = e.getEnemies().get(i).getImageN();
            }

            for (int i = 0; i < imageEList.length; i++) {
                int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                imageEList[i].setBounds(x, y, largeur, longueur);
                usedFrame.add(imageEList[i]);
                y = y + 100;
            }

            JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit.png"));
            combatEffect.setBounds(395, 120 + (100 * target), 60, 60);
            usedFrame.add(combatEffect);

            int damage = Math.max((a.getATK() - t.getDEF()) * 3, 0);

            String infoBattle = "CRITICAL HIT!!! " + a.getName() + " attacks " + t.getName() + " for " + damage + "damages!";
            t.setActualHP(Math.max(0, t.getActualHP() - damage));

            JLabel info = new JLabel(infoBattle);

            JButton continueButton = new JButton("Continue ...");
            continueButton.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (t.getActualHP() == 0) defeatedEnemy(mainFrame, backgroundFile, attacker, target, p, e, f);
                    else {
                        if (a.getSPEED() >= t.getSPEED() + 5) {
                            f.attaque2(mainFrame, backgroundFile, attacker, target, p, e, f);
                        }
                        else {
                            f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                        }
                    }
                }
            });
            menuBar.add(continueButton);

            menuBar.add(info);
            menuBar.setBounds(0, 500, 600, 100);
            background.add(menuBar);
            usedFrame.getContentPane().add(background);
            usedFrame.repaint();
            usedFrame.revalidate();
        }
    }

    // Fonction d'attaque pour la deuxième attaque en cas de double-attaque

    public static void attaque2(JFrame mainFrame, String backgroundFile, int attacker, int target, Party p, Enemies e, Fight f) {
        Character a = p.getTeam().get(attacker);
        Mob t = e.getEnemies().get(target);

        if ((int)(Math.random() * 100) > p.getTeam().get(attacker).getLUCK()) {
            JFrame usedFrame = mainFrame;
            usedFrame.getContentPane().removeAll();
            usedFrame.setLayout(null);

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

            // On affiche les sprites des alliés

            JLabel[] imagePList = new JLabel[(p.getTeam().size())];
            for (int i = 0; i < p.getTeam().size(); i++) {
                imagePList[i] = p.getTeam().get(i).getImageN();
            }

            for (int i = 0; i < imagePList.length; i++) {
                int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                if (p.getTeam().get(i).getIsDead() == false) {
                    if (i == attacker) {
                        imagePList[i].setBounds(305, (105 + target * 100), 90, 90);
                        usedFrame.add(imagePList[i]);
                        y = y + 100;
                    }
                    else {
                        imagePList[i].setBounds(x, y, largeur, longueur);
                        usedFrame.add(imagePList[i]);
                        y = y + 100;
                    }
                }
            }

            JLabel[] imageEList = new JLabel[e.getEnemies().size()];
            for (int i = 0; i < e.getEnemies().size(); i++) {
                imageEList[i] = e.getEnemies().get(i).getImageN();
            }

            for (int i = 0; i < imageEList.length; i++) {
                int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                imageEList[i].setBounds(x, y, largeur, longueur);
                usedFrame.add(imageEList[i]);
                y = y + 100;
            }

            JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit.png"));
            combatEffect.setBounds(395, 120 + (100 * target), 60, 60);
            usedFrame.add(combatEffect);

            int damage = Math.max(a.getATK() - t.getDEF(), 0);

            String infoBattle = a.getName() + " attacks " + t.getName() + " for " + damage + "damages!";
            t.setActualHP(Math.max(0, t.getActualHP() - damage));

            JLabel info = new JLabel(infoBattle);

            JButton continueButton = new JButton("Continue ...");
            continueButton.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (t.getActualHP() == 0) defeatedEnemy(mainFrame, backgroundFile, attacker, target, p, e, f);
                    else {
                        f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                    }
                }
            });
            menuBar.add(continueButton);

            menuBar.add(info);
            menuBar.setBounds(0, 500, 600, 100);
            background.add(menuBar);
            usedFrame.getContentPane().add(background);
            usedFrame.repaint();
            usedFrame.revalidate();
        }
        else {
            JFrame usedFrame = mainFrame;
            usedFrame.getContentPane().removeAll();
            usedFrame.setLayout(null);

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

            // On affiche les sprites des alliés

            JLabel[] imagePList = new JLabel[(p.getTeam().size())];
            for (int i = 0; i < p.getTeam().size(); i++) {
                imagePList[i] = p.getTeam().get(i).getImageN();
            }

            for (int i = 0; i < imagePList.length; i++) {
                int x = 55; int y = 105; int largeur = 90; int longueur = 90;
                if (p.getTeam().get(i).getIsDead() == false) {
                    if (i == attacker) {
                        imagePList[i].setBounds(305, (105 + target * 100), 90, 90);
                        usedFrame.add(imagePList[i]);
                        y = y + 100;
                    }
                    else {
                        imagePList[i].setBounds(x, y, largeur, longueur);
                        usedFrame.add(imagePList[i]);
                        y = y + 100;
                    }
                }
            }

            JLabel[] imageEList = new JLabel[e.getEnemies().size()];
            for (int i = 0; i < e.getEnemies().size(); i++) {
                imageEList[i] = e.getEnemies().get(i).getImageN();
            }

            for (int i = 0; i < imageEList.length; i++) {
                int x = 455; int y = 105; int largeur = 90; int longueur = 90;
                imageEList[i].setBounds(x, y, largeur, longueur);
                usedFrame.add(imageEList[i]);
                y = y + 100;
            }

            JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit.png"));
            combatEffect.setBounds(395, 120 + (100 * target), 60, 60);
            usedFrame.add(combatEffect);

            int damage = Math.max((a.getATK() - t.getDEF()) * 3, 0);

            String infoBattle = "CRITICAL HIT!!! " + a.getName() + " attacks " + t.getName() + " for " + damage + "damages!";
            t.setActualHP(Math.max(0, t.getActualHP() - damage));

            JLabel info = new JLabel(infoBattle);

            JButton continueButton = new JButton("Continue ...");
            continueButton.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (t.getActualHP() == 0) defeatedEnemy(mainFrame, backgroundFile, attacker, target, p, e, f);
                    else {
                        if (a.getSPEED() >= t.getSPEED() + 5) {
                            f.attaque2(mainFrame, backgroundFile, attacker, target, p, e, f);
                        }
                        else {
                            f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                        }
                    }
                }
            });
            menuBar.add(continueButton);

            menuBar.add(info);
            menuBar.setBounds(0, 500, 600, 100);
            background.add(menuBar);
            usedFrame.getContentPane().add(background);
            usedFrame.repaint();
            usedFrame.revalidate();
        }
    }

    public static void defeatedEnemy(JFrame mainFrame, String backgroundFile, int attacker, int target, Party p, Enemies e, Fight f) {

    }



    // On affiche l'écran de défaite en cas de Game Over

    public static void gameOverScreen(JFrame mainFrame) {
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();

        JLabel goImage = new JLabel(new ImageIcon("Images/GameOverScreen.png"));
        goImage.setBounds(0, 0, 600, 600);
        usedFrame.setLayout(null);
        usedFrame.getContentPane().add(goImage);

        usedFrame.repaint();
        usedFrame.revalidate();

        try { Thread.sleep(5000); } catch (InterruptedException i) { System.out.println("Error in GO sleep"); }

        usedFrame.dispatchEvent(new WindowEvent(usedFrame, WindowEvent.WINDOW_CLOSING));
    }


    public static void fightOnScreen(Fight f, JFrame mainFrame, String backgroundFile) {
        System.out.println(f.getParty().toString());
        System.out.println(f.getEnemies().toString());

        JFrame usedFrame = mainFrame;
        f.startFightScreen(usedFrame);
        f.basicFightScreen(usedFrame, backgroundFile, f.getParty(), f.getEnemies(), 0, f);

    }
}

