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
    private Utilitaries ret ;

    public Fight(Party allies, Enemies enemies, Utilitaries u){
        this.Turn = 1;
        this.allies = allies;
        this.enemies = enemies;
        this.ret = u;
    }

    // Accesseurs aux éléments d'un objet de la classe

    public int getTurn() { return this.Turn; }

    public Party getParty() { return this.allies; } 
    
    public Enemies getEnemies() { return this.enemies; }

    public Utilitaries getRet() { return this.ret; }

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


    // Fonction allant gérer les combats sur l'interface graphique

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

            try { Thread.sleep(150); } catch (InterruptedException e) { System.out.println("Error in fight sleep"); }
        }
    }




    public static void basicFightScreen(JFrame mainFrame, String backgroundFile, Party p, Enemies e, int order, Fight f) {
        // On ajoute le fond de combat et la barre de menu

        if (p.getTeam().get(order).getIsDead() == true) {
            System.out.println("Here");
            f.basicFightScreen(mainFrame, backgroundFile, p, e, order + 1, f);
        }

        else if (order == p.getTeam().size()) {
            f.basicFightScreen(mainFrame, backgroundFile, p, e, 0, f);
        }

        else if (e.haveBeenDefeated()) {
            f.victory(mainFrame, backgroundFile, p, f);
        }

        else {
        Character c = p.getTeam().get(order);

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

        JButton restButton = new JButton("Rest");
        restButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                if (c.getActualHP() == c.getHP()) JOptionPane.showMessageDialog(null, c.getName() + " is already at full HP!");
                else f.healSelf(mainFrame, backgroundFile, p, e, order, f);
            }
        });
        menuBar.add(restButton);

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
            }
            y = y + 100;
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
    }

    // Fonction de soin des personnages

    public static void healSelf(JFrame mainFrame, String backgroundFile, Party p, Enemies e, int attacker, Fight f) {
        Character c = p.getTeam().get(attacker);
        int heal = c.getHP() / 2;
        int recovery = Math.min(heal, c.getHP() - c.getActualHP());
        c.setActualHP(Math.min(c.getActualHP() + heal, c.getHP()));

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

        JLabel info = new JLabel(c.getName() + " has recovered " + recovery + " HP. (" + c.getActualHP() + "/" + c.getHP() + ")");
        menuBar.add(info);
        JButton continueButton = new JButton("Continue ...");
        continueButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                if (attacker == p.getTeam().size() - 1) f.prepEnemyAttack(mainFrame, backgroundFile, 0, p, e, f);
                else f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
            }
        });
        menuBar.add(continueButton);


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
            }
            y = y + 100;
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

        JLabel combatEffect = new JLabel(new ImageIcon("Images/Heal.png"));
        combatEffect.setBounds(160, 110 + (100 * attacker), 50, 50);
        usedFrame.add(combatEffect);

        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    }



    // Fonction du choix de la cible pour les alliés

    public static void chooseTeamAttack(JFrame mainFrame, String backgroundFile, Party p, Enemies e, int attacker, Fight f) {
        Character c = p.getTeam().get(attacker);
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
            }
            y = y + 100;
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

        menuBar.add(new JLabel("It's " + c.getName() + " turn to attack ..."));

        // On ajoute les choix de combats
        int i = 0;

        for (Mob m : e.getEnemies()) {
            final int fi = i;
            JButton button = new JButton(m.toString());
            button.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent a) {
                    f.attackPrep(mainFrame, backgroundFile, attacker, fi, p, e, f);
                }
            });
            i++;
            menuBar.add(button);
        }

        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
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

        int x = 55; int y = 105; int largeur = 90; int longueur = 90;
        for (int i = 0; i < imagePList.length; i++) {
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
            else { y = y + 100; }
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

        String infoBattle = a.getName() + " attacks " + t.getName() + "!";

        JLabel info = new JLabel(infoBattle);
        menuBar.add(info);
        JButton continueButton = new JButton("Continue ...");
        continueButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                f.attaqueNormale_1(mainFrame, backgroundFile, attacker, target, p, e, f);
            }
        });
        menuBar.add(continueButton);
        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    } 

    // Fonction en cas de double attaque

    public static void attaqueNormale_1(JFrame mainFrame, String backgroundFile, int attacker, int target, Party p, Enemies e, Fight f) {
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

            int x = 55; int y = 105; int largeur = 90; int longueur = 90;
            for (int i = 0; i < imagePList.length; i++) {   
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
                else { y = y + 100; }
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

            JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit1_.png"));
            combatEffect.setBounds(395, 120 + (100 * target), 60, 60);
            usedFrame.add(combatEffect);

            int damage = 0;
            if (a instanceof Physical) {
                damage = Math.max(a.getATK() - t.getDEF(), 0);
            }
            else {
                if (a instanceof Mage) { damage = a.getATK(); }
                else { damage = a.getATK() / 2; }
            }

            String infoBattle = a.getName() + " attacks " + t.getName() + " for " + damage + " damages!";
            t.setActualHP(Math.max(0, t.getActualHP() - damage));

            JLabel info = new JLabel(infoBattle);
            menuBar.add(info);
            JButton continueButton = new JButton("Continue ...");
            continueButton.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (t.getActualHP() == 0) defeatedEnemy(mainFrame, backgroundFile, attacker, target, p, e, f);
                    else {
                        if (a.getSPEED() < t.getSPEED() + 5) {
                            if (attacker == p.getTeam().size() - 1) f.prepEnemyAttack(mainFrame, backgroundFile, 0, p, e, f);
                            else f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                        }
                        else {
                            if ((int)(Math.random() * 100) > a.getLUCK()) {
                                f.attaqueNormale_2(mainFrame, backgroundFile, attacker, target, p, e, f);
                            }
                            else {
                                f.attaqueCrit_2(mainFrame, backgroundFile, attacker, target, p, e, f);
                            }
                        }
                    }
                }
            });
            menuBar.add(continueButton);
            menuBar.setBounds(0, 500, 600, 100);
            background.add(menuBar);
            usedFrame.getContentPane().add(background);
            usedFrame.repaint();
            usedFrame.revalidate();
        
    }

    public static void attaqueCrit_1(JFrame mainFrame, String backgroundFile, int attacker, int target, Party p, Enemies e, Fight f) {
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

            int x = 55; int y = 105; int largeur = 90; int longueur = 90;
            for (int i = 0; i < imagePList.length; i++) {
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
                else { y = y + 100; }
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

            JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit2_.png"));
            combatEffect.setBounds(395, 120 + (100 * target), 60, 60);
            usedFrame.add(combatEffect);

            int damage = 0;
            if (a instanceof Physical) {
                damage = Math.max((a.getATK() - t.getDEF()) * 3, 0);
            }
            else {
                if (a instanceof Mage) { damage = a.getATK() * 3; }
                else { damage = (a.getATK() * 3) / 2; }
            }

            String infoBattle = a.getName() + " attacks " + t.getName() + " for " + damage + " damages!";
            t.setActualHP(Math.max(0, t.getActualHP() - damage));

            JLabel info = new JLabel(infoBattle);
            menuBar.add(info);
            JButton continueButton = new JButton("Continue ...");
            continueButton.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (t.getActualHP() == 0) defeatedEnemy(mainFrame, backgroundFile, attacker, target, p, e, f);
                    else {
                        if (a.getSPEED() < t.getSPEED() + 5) {
                            if (attacker == p.getTeam().size() - 1) f.prepEnemyAttack(mainFrame, backgroundFile, 0, p, e, f);
                            else f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                        }
                        else {
                            if ((int)(Math.random() * 100) > a.getLUCK()) {
                                f.attaqueNormale_2(mainFrame, backgroundFile, attacker, target, p, e, f);
                            }
                            else {
                                f.attaqueCrit_2(mainFrame, backgroundFile, attacker, target, p, e, f);
                            }
                        }
                    }
                }
            });
            menuBar.add(continueButton);
            menuBar.setBounds(0, 500, 600, 100);
            background.add(menuBar);
            usedFrame.getContentPane().add(background);
            usedFrame.repaint();
            usedFrame.revalidate();
        
    }

    // Fonction d'attaque pour la deuxième attaque en cas de double-attaque

    public static void attaqueNormale_2(JFrame mainFrame, String backgroundFile, int attacker, int target, Party p, Enemies e, Fight f) {
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


        int x = 55; int y = 105; int largeur = 90; int longueur = 90;
        for (int i = 0; i < imagePList.length; i++) {
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
            else { y = y + 100; }
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

        JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit_1.png"));
        combatEffect.setBounds(395, 120 + (100 * target), 60, 60);
        usedFrame.add(combatEffect);

        int damage = 0;
        if (a instanceof Physical) {
            damage = Math.max(a.getATK() - t.getDEF(), 0);
        }
        else {
            if (a instanceof Mage) { damage = a.getATK(); }
            else { damage = a.getATK() / 2; }
        }

        String infoBattle = "And a follow-up attack! " + a.getName() + " attacks " + t.getName() + " for " + damage + " damages!";
        t.setActualHP(Math.max(0, t.getActualHP() - damage));

        JLabel info = new JLabel(infoBattle);
        JButton continueButton = new JButton("Continue ...");
        continueButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (t.getActualHP() == 0) f.defeatedEnemy(mainFrame, backgroundFile, attacker, target, p, e, f);
                else {
                    if (attacker == p.getTeam().size() - 1) f.prepEnemyAttack(mainFrame, backgroundFile, 0, p, e, f);
                    else f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                }
            }
        });
        menuBar.add(info);
        menuBar.add(continueButton);
        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    }

    public static void attaqueCrit_2(JFrame mainFrame, String backgroundFile, int attacker, int target, Party p, Enemies e, Fight f) {
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

        int x = 55; int y = 105; int largeur = 90; int longueur = 90;
        for (int i = 0; i < imagePList.length; i++) {
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
            else {
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

        JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit_2.png"));
        combatEffect.setBounds(395, 120 + (100 * target), 60, 60);
        usedFrame.add(combatEffect);

        int damage = 0;
            if (a instanceof Physical) {
                damage = Math.max((a.getATK() - t.getDEF()) * 3, 0);
            }
            else {
                if (a instanceof Mage) { damage = a.getATK() * 3; }
                else { damage = (a.getATK() * 3) / 2; }
            }

        String infoBattle = "And a follow-up attack! CRITICAL HIT!!! " + a.getName() + " attacks " + t.getName() + " for " + damage + " damages!";
        t.setActualHP(Math.max(0, t.getActualHP() - damage));

        JLabel info = new JLabel(infoBattle);
        menuBar.add(info);
        JButton continueButton = new JButton("Continue ...");
        continueButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (t.getActualHP() == 0) f.defeatedEnemy(mainFrame, backgroundFile, attacker, target, p, e, f);
                else {
                    if (attacker == p.getTeam().size()- 1) f.prepEnemyAttack(mainFrame, backgroundFile, 0, p, e, f);                  
                    else f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                }
            }
        });
        menuBar.add(continueButton);
        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    }




    // On regle les attaques ennemiess

    public static void prepEnemyAttack(JFrame mainFrame, String backgroundFile, int mob, Party p, Enemies e, Fight f) {
        Mob a = e.getEnemies().get(mob);

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
            }
            y = y + 100;
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

        // Info et boutons de combats

        JLabel info = new JLabel(a.getName() + " is attacking ...");
        JButton continueButton = new JButton("Continue ...");
        continueButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                f.enemyAttack(mainFrame, backgroundFile, mob, p, e, f);
            }
        });

        menuBar.add(info);
        menuBar.add(continueButton);
        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    }




    public static void enemyAttack(JFrame mainFrame, String backgroundFile, int mob, Party p, Enemies e, Fight f) {
        if (p.haveLost()) {
            f.gameOverScreen(mainFrame);
        }

        Mob t = e.getEnemies().get(mob);
        int v = (int)(Math.random() * p.getTeam().size());
        while (p.getTeam().get(v).getIsDead() == true) {
            v = (int)(Math.random() * p.getTeam().size());
        }
        Character c = p.getTeam().get(v);

        int damage = 0;
        if (t.getSPEED() < c.getSPEED() + 5) damage = Math.max(0, t.getATK() - c.getDEF());
        else damage = Math.max(0, (t.getATK() - c.getDEF()) * 2);

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
            }
            y = y + 100;
        }

        JLabel[] imageEList = new JLabel[e.getEnemies().size()];
        for (int i = 0; i < e.getEnemies().size(); i++) {
            imageEList[i] = e.getEnemies().get(i).getImageN();
        }

        x = 455; y = 105; largeur = 90; longueur = 90;
        for (int i = 0; i < imageEList.length; i++) {
            if (i == mob) {
                imageEList[i].setBounds(255, 105 + v * 100, 90, 90);
                usedFrame.add(imageEList[i]);
                y = y + 100;
            }
            else {
                imageEList[i].setBounds(x, y, largeur, longueur);
                usedFrame.add(imageEList[i]);
                y = y + 100;
            }
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

        // Info de combat

        JLabel combatEffect = new JLabel(new ImageIcon("Images/Hit1_.png"));
        combatEffect.setBounds(155, 120 + (100 * v), 60, 60);
        usedFrame.add(combatEffect);

        JLabel info = new JLabel(t.getName() + " attacks " + c.getName() + " for " + damage + " damages!");
        JButton continueButton = new JButton("Continue ...");

        c.setActualHP(Math.max(c.getActualHP() - damage, 0));
        if (c.getActualHP() == 0) {
            c.setIsDead(true);
        }
        continueButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (p.haveLost()) {
                    JOptionPane.showMessageDialog(null, "You have lost ...");
                    f.gameOverScreen(mainFrame);
                }
                else if ( mob == e.getEnemies().size() - 1) {
                    f.basicFightScreen(mainFrame, backgroundFile, p, e, 0, f);
                }
                else {
                    f.prepEnemyAttack(mainFrame, backgroundFile, mob + 1, p, e, f);
                }
            }
        });

        menuBar.add(info);
        menuBar.add(continueButton);
        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    }







    // En cas de victoire contre un adversaire

    public static void defeatedEnemy(JFrame mainFrame, String backgroundFile, int attacker, int target, Party p, Enemies e, Fight f) {
        Character a = p.getTeam().get(attacker);
        Mob t = e.getEnemies().get(target);
        int expG = t.getExpDrop();

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
            }
            y = y + 100;
        }

        JLabel[] imageEList = new JLabel[e.getEnemies().size()];
        for (int i = 0; i < e.getEnemies().size(); i++) {
            imageEList[i] = e.getEnemies().get(i).getImageN();
        }

        x = 455; y = 105; largeur = 90; longueur = 90;
        for (int i = 0; i < imageEList.length; i++) {
            if (e.getEnemies().get(i).equals(t)) {
                y = y + 100;
            }
            else {
                imageEList[i].setBounds(x, y, largeur, longueur);
                usedFrame.add(imageEList[i]);
                y = y + 100;
            }
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

        a.setEXP(Math.min(a.getNeededEXP(), a.getEXP() + expG));
        menuBar.add(new JLabel(a.getName() + " has defeated " + t.getName() + "! " + a.getName() + " has earned " + expG + " experience points!\n"));
        JButton continueButton = new JButton("Continue ...");
        continueButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (a.getEXP() == a.getNeededEXP()) {
                    JOptionPane.showMessageDialog(null, a.levelUpForWindow());
                }
                if (e.haveBeenDefeated()) {
                    f.victory(mainFrame, backgroundFile, p, f);
                }
                else {
                    if (attacker == p.getTeam().size() - 1) {
                        f.prepEnemyAttack(mainFrame, backgroundFile, 0, p, e, f);
                    }
                    else {
                        f.basicFightScreen(mainFrame, backgroundFile, p, e, attacker + 1, f);
                    }
                }
            }
        });
        menuBar.add(continueButton);
        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();

        e.getEnemies().remove(target);
    }

    // Fonction servant à gérer les cas de victoire

    public static void victory(JFrame mainFrame, String backgroundFile, Party p, Fight f) {
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
            }
            y = y + 100;
        }

        // On affiche un message de victoire dans le menu

        int expG = (int)(Math.random() * 0.6 + 0.7) * 3 * (p.getAverageLevel() + 1);
        JLabel message;
        double r = Math.random();
        if (r < 0.33) {
            message = new JLabel("'Victory! To the next fight...'\nEveryone has earned " + expG + " experience points.");
        }
        else if (r < 0.66) {
            message = new JLabel("'The enemies have been defeated! Let's move on...'\nEveryone has earned " + expG + " experience points.");
        }
        else {
            message = new JLabel("'It's almost scary how strong we are...'\nEveryone has earned " + expG + " experience points.");
        }
        menuBar.add(message);
        JButton continueButton = new JButton("Continue ...");
        continueButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                for (Character c : p.getTeam()) {
                    c.setEXP(Math.min(c.getNeededEXP(), c.getEXP() + expG));
                    if (c.getEXP() >= c.neededEXP) JOptionPane.showMessageDialog(null, c.levelUpForWindow());
                }
                f.endOfFight(mainFrame, f);
            }
        });
        menuBar.add(continueButton);
        menuBar.setBounds(0, 500, 600, 100);
        background.add(menuBar);
        usedFrame.getContentPane().add(background);
        usedFrame.repaint();
        usedFrame.revalidate();
    }

    public static void endOfFight(JFrame usedFrame, Fight f) {
        usedFrame.getContentPane().removeAll();

        GamePanel panel = new GamePanel();
        panel.setSize(600, 600);

        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        JLabel img = new JLabel(new ImageIcon("Images/Victory.png"));
        img.setBounds(150, 150, 300, 300);
        panel.add(img);

        usedFrame.setLayout(null);
        panel.setBounds(0, 0, 600, 600);
        usedFrame.getContentPane().add(panel);

        usedFrame.repaint();
        usedFrame.revalidate();

        f.ret.setValue(1);
    }

    // On affiche l'écran de défaite en cas de Game Over

    public static void gameOverScreen(JFrame mainFrame) {
        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();

        JButton button = new JButton("Close the game");
        button.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                usedFrame.dispatchEvent(new WindowEvent(usedFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        button.setBounds(200, 500, 200, 60);
        usedFrame.add(button);

        JLabel goImage = new JLabel(new ImageIcon("Images/GameOverScreen.png"));
        goImage.setBounds(0, 0, 600, 600);
        usedFrame.setLayout(null);
        usedFrame.getContentPane().add(goImage);
        
        usedFrame.repaint();
        usedFrame.revalidate();
    }


    public static void fightOnScreen(Fight f, JFrame mainFrame, String backgroundFile) {
        
            System.out.println(f.getParty().toString());
            System.out.println(f.getEnemies().toString());

            JFrame usedFrame = mainFrame;
            f.startFightScreen(usedFrame);
            f.basicFightScreen(usedFrame, backgroundFile, f.getParty(), f.getEnemies(), 0, f);
    }
}