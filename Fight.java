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

        GamePanel menuBar = new GamePanel();
        menuBar.setLayout(new FlowLayout());
        menuBar.setSize(600, 100);
        menuBar.setBackground(Color.WHITE);

        menuBar.add(new JLabel("It's " + p.getTeam().get(Turn).getName() + " turn to attack ..."));

        // On ajoute les choix de combats

        Utilitaries n = new Utilitaries();

        for (Mob m : e.getEnemies()) {
            JButton button = new JButton(m.toString());
            button.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent a) {
                    attackTeam(mainFrame, backgroundFile, n.getValue(), Turn, p, e, f);
                }
            });
            menuBar.add(button);
            n.setValue(n.getValue() + 1);
        }

        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
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



    // Fonction d'attaque

    public static void attackTeam(JFrame mainFrame, String backgroundFile, int target, int attacker, Party p, Enemies e, Fight f) {
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();

        GamePanel background = new GamePanel();
        background.setSize(600, 600);
        background.setLayout(null);

        JLabel backscreen = new JLabel(new ImageIcon(backgroundFile));
        backscreen.setBounds(0, 0, 600, 500);
        background.add(backscreen);

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

        JLabel attackerSprite = p.getTeam().get(attacker).getImageN();;
        JLabel targetSprite = e.getEnemies().get(target).getImageN();

        JLabel[] imagePList = new JLabel[(p.getTeam().size())];
        for (int i = 0; i < p.getTeam().size(); i++) {
            imagePList[i] = p.getTeam().get(i).getImageN();
        }

        for (int i = 0; i < imagePList.length; i++) {
            int x = 55; int y = 105; int largeur = 90; int longueur = 90;
            if (p.getTeam().get(i).getIsDead() == false) {
                if (i == attacker) {
                    attackerSprite.setBounds(305, (105 + target * 100), 90, 90);
                    usedFrame.add(attackerSprite);
                }
                else {
                    imagePList[i].setBounds(x, y, largeur, longueur);
                    usedFrame.add(imagePList[i]);
                }
                y = y + 100;
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

        // On crée les informations de combats

        GamePanel menuBar = new GamePanel();
        menuBar.setLayout(new FlowLayout());
        menuBar.setSize(600, 100);
        menuBar.setBackground(Color.WHITE);
        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);

        JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit.png"));
        combatEffect.setBounds(395, 120 + (100 * target), 60, 60);

        if (p.getTeam().get(attacker).getSPEED() < e.getEnemies().get(target).getSPEED() + 5) {
            String infoBattle1 = p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + "!";

            int damage = Math.min(p.getTeam().get(attacker).getATK() - e.getEnemies().get(target).getDEF(), 0);
            JLabel info1 = new JLabel(infoBattle1);
            menuBar.add(info1);

            usedFrame.repaint();
            usedFrame.revalidate();

            menuBar.remove(info1);

            try { Thread.sleep(3000); } catch (InterruptedException i) { System.out.println("Error in GO sleep"); }

            if ((int)(Math.random() * 100) > p.getTeam().get(attacker).getLUCK()) {
                usedFrame.add(combatEffect);
                String infoBattle2 = p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + " for " + damage + " damages!";
                e.getEnemies().get(target).setActualHP(Math.max(e.getEnemies().get(target).getActualHP() - damage, 0));
                JLabel info2 = new JLabel(infoBattle2);
                menuBar.add(info2);

                usedFrame.repaint();
                usedFrame.revalidate();

                try { Thread.sleep(500); } catch (InterruptedException i) { System.out.println("Error in GO sleep"); }

                usedFrame.remove(combatEffect);

                usedFrame.repaint();
                usedFrame.revalidate();
                
                menuBar.remove(info2);

                try { Thread.sleep(3000); } catch (InterruptedException i) { System.out.println("Error in GO sleep"); }

                if (e.getEnemies().get(target).getActualHP() != 0) {
                    if ((int)(Math.random() * 100) > p.getTeam().get(attacker).getLUCK()) {
                        usedFrame.add(combatEffect);
                        String infoBattle3 = p.getTeam().get(attacker).getName() + " attacks " + e.getEnemies().get(target).getName() + " for " + damage + " damages!";
                        e.getEnemies().get(target).setActualHP(Math.max(e.getEnemies().get(target).getActualHP() - damage, 0));
                        JLabel info3 = new JLabel(infoBattle3);
                        menuBar.add(info3);
        
                        usedFrame.repaint();
                        usedFrame.revalidate();
        
                        try { Thread.sleep(500); } catch (InterruptedException i) { System.out.println("Error in GO sleep"); }
        
                        usedFrame.remove(combatEffect);
        
                        usedFrame.repaint();
                        usedFrame.revalidate();
                        
                        menuBar.remove(info3);
        
                        try { Thread.sleep(3000); } catch (InterruptedException i) { System.out.println("Error in GO sleep"); }
                    }
                    
                    else {
                        usedFrame.add(combatEffect);
                        damage = damage * 3;
                        String infoBattle3 = "CRITICAL HIT!!! " + p.getTeam().get(attacker).getName() + " attacks again " + e.getEnemies().get(target).getName() + " for " + damage + " damages!";
                        e.getEnemies().get(target).setActualHP(Math.max(e.getEnemies().get(target).getActualHP() - damage, 0));
                        damage = damage / 3;
                        JLabel info3 = new JLabel(infoBattle3);
                        menuBar.add(info3);

                        usedFrame.repaint();
                        usedFrame.revalidate();

                        try { Thread.sleep(500); } catch (InterruptedException i) { System.out.println("Error in GO sleep"); }

                        usedFrame.remove(combatEffect);

                        usedFrame.repaint();
                        usedFrame.revalidate();
                        
                        menuBar.remove(info3);
                    }

                    if (e.getEnemies().get(target).getActualHP() == 0) {
                        int exp = e.getEnemies().get(target).getExpDrop();
                        String infoBattle4 = p.getTeam().get(attacker).getName() + " has defeated " + e.getEnemies().get(target).getName() + "!\n" + p.getTeam().get(attacker).getName() + " has earned " + exp + " experience points!";
                        p.getTeam().get(attacker).earnExp(exp);
                        e.getEnemies().remove(target);
                        JLabel info4 = new JLabel(infoBattle4);
                        menuBar.add(info4);

                        usedFrame.repaint();
                        usedFrame.revalidate();

                        JButton follow = new JButton("Next turn");
                        follow.addActionListener (new ActionListener() {
                            public void actionPerformed(ActionEvent a) {
                                if (attacker < p.getTeam().size() - 1) f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                                else System.out.println("THIS IS TE LIMIT");
                            }
                        });
                    }
                }
            }

        }

    }





    public static void fightOnScreen(Fight f, JFrame mainFrame, String backgroundFile) {
        System.out.println(f.getParty().toString());
        System.out.println(f.getEnemies().toString());

        JFrame usedFrame = mainFrame;
        f.startFightScreen(usedFrame);
        f.basicFightScreen(usedFrame, backgroundFile, f.getParty(), f.getEnemies(), 0, f);

    }
}

