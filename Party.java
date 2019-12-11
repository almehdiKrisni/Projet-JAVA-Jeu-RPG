import java.util.ArrayList;
import java.util.Scanner;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.font.*;
import javax.swing.*;

public class Party {
    private ArrayList<Character> Team;
    private ArrayList<Physical> physicalFighters;
    private ArrayList<Magic> magicalFighters;
    private Inventory inventory;
    private ArrayList<Weapon> armory;
    private int money;

    // Je limite l'équipe à 4 membres

    private static final int maxTeamSize = 4;

    // Constructeur de la classe

    public Party() {
        this.inventory = new Inventory();
        this.Team = new ArrayList<Character>();
        this.physicalFighters = new ArrayList<Physical>();
        this.magicalFighters = new ArrayList<Magic>();
        this.armory = new ArrayList<Weapon>();
        this.money = 0;
    }

    // Méthode pour get et set l'argent possédé par l'équipe

    public int getMoney() { return this.money; }

    public void setMoney(int coins) { this.money = coins; }
    
    // Méthodes de la classe (ajout de personnages ou d'objets)

    public void addMember() {
        if (this.Team.size() == maxTeamSize) {
            System.out.println("The team is already full (only 4 members are accepted).\n");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> liste_choix = new ArrayList<Integer>();
        for (int v = 1; v <= 4; v++) { liste_choix.add(v); }
        
        System.out.println("Enter your name :"); System.out.println();
        String name = scanner.next();

        System.out.println("\nChoose your class (choose a number) :\n1 - Sweeper (High base Speed and Attack)\n2 - Tank (High base HP and Defense)\n3 - Mage (Ignore the enemy's defense, low base HP and DEF)\n4 - Healer (Heals an ally or inflict low damage to enemies)\n");
        int v = scanner.nextInt(); System.out.println();

        while ( liste_choix.contains(v) == false) {
            System.out.println("\nError while choosing your class (wrong value chosen)\nChoose your class :\n1 - Sweeper (High base Speed and Attack)\n2 - Tank (High base HP and Defense)\n3 - Mage (Ignore the enemy's defense, low base HP and DEF)\n4 - Healer (Heals an ally or inflict low damage to enemies)\n");
            v = scanner.nextInt(); System.out.println();
        }

        if ( v == 1) { Sweeper c = new Sweeper(name); this.Team.add(c); this.physicalFighters.add(c); System.out.println(c.getName() + " has joined the team !\n"); }
        else if ( v == 2 ) { Tank c = new Tank(name); this.Team.add(c); this.physicalFighters.add(c); System.out.println(c.getName() + " has joined the team !\n"); }
        else if ( v == 3 ) { Mage c = new Mage(name); this.Team.add(c); this.magicalFighters.add(c); System.out.println(c.getName() + " has joined the team !\n"); }
        else { Healer c = new Healer(name); this.Team.add(c); this.magicalFighters.add(c); System.out.println(c.getName() + " has joined the team !\n"); }
    }

    public String addMember(Character c) {
        this.Team.add(c);
        return c.getName() + " has joined your team!\n";
    }

    public void addMember(String name, String wannabe, int c) {
        String[] M_Sweeper = {"Character_Sprites/M/Sweeper/1.png", "Character_Sprites/M/Sweeper/2.png", "Character_Sprites/M/Sweeper/3.png", "Character_Sprites/M/Sweeper/4.png"};
        String[] M_Tank = {"Character_Sprites/M/Tank/1.png", "Character_Sprites/M/Tank/2.png", "Character_Sprites/M/Tank/3.png", "Character_Sprites/M/Tank/4.png"};
        String[] M_Mage = {"Character_Sprites/M/Mage/1.png", "Character_Sprites/M/Mage/2.png", "Character_Sprites/M/Mage/3.png", "Character_Sprites/M/Mage/4.png"};
        String[] M_Healer = {"Character_Sprites/M/Healer/1.png", "Character_Sprites/M/Healer/2.png", "Character_Sprites/M/Healer/3.png", "Character_Sprites/M/Healer/4.png"};

        String[] F_Sweeper = {"Character_Sprites/F/Sweeper/1.png", "Character_Sprites/F/Sweeper/2.png", "Character_Sprites/F/Sweeper/3.png", "Character_Sprites/F/Sweeper/4.png"};
        String[] F_Tank = {"Character_Sprites/F/Tank/1.png", "Character_Sprites/F/Tank/2.png", "Character_Sprites/F/Tank/3.png", "Character_Sprites/F/Tank/4.png"};
        String[] F_Mage = {"Character_Sprites/F/Mage/1.png", "Character_Sprites/F/Mage/2.png", "Character_Sprites/F/Mage/3.png", "Character_Sprites/F/Mage/4.png"};
        String[] F_Healer = {"Character_Sprites/F/Healer/1.png", "Character_Sprites/F/Healer/2.png", "Character_Sprites/F/Healer/3.png", "Character_Sprites/F/Healer/4.png"};

        if (wannabe == "Healer (M)") { Healer h = new Healer(name); this.Team.add(h); this.magicalFighters.add(h); h.setImageN(new JLabel(new ImageIcon(M_Healer[c]))); }
        else if (wannabe == "Mage (M)") { Mage m = new Mage(name); this.Team.add(m); this.magicalFighters.add(m); m.setImageN(new JLabel(new ImageIcon(M_Mage[c])));}
        else if (wannabe == "Sweeper (M)") { Sweeper s = new Sweeper(name); this.Team.add(s); this.physicalFighters.add(s); s.setImageN(new JLabel(new ImageIcon(M_Sweeper[c])));}
        else if (wannabe == "Tank (M)") { Tank t = new Tank(name); this.Team.add(t); this.physicalFighters.add(t); t.setImageN(new JLabel(new ImageIcon(M_Tank[c])));}
        else if (wannabe == "Healer (F)") { Healer h = new Healer(name); this.Team.add(h); this.magicalFighters.add(h); h.setImageN(new JLabel(new ImageIcon(F_Healer[c])));}
        else if (wannabe == "Mage (F)") { Mage m = new Mage(name); this.Team.add(m); this.magicalFighters.add(m); m.setImageN(new JLabel(new ImageIcon(F_Mage[c])));}
        else if (wannabe == "Sweeper (F)") { Sweeper s = new Sweeper(name); this.Team.add(s); this.physicalFighters.add(s); s.setImageN(new JLabel(new ImageIcon(F_Sweeper[c])));}
        else { Tank t = new Tank(name); this.Team.add(t); this.physicalFighters.add(t); t.setImageN(new JLabel(new ImageIcon(F_Tank[c])));}

    }

    public static void addMembers(JFrame inActionFrame, Utilitaries start, Party p) {
        Party party = p;

        JFrame usedFrame = inActionFrame;
        usedFrame.getContentPane().removeAll();
        usedFrame.repaint();

        // On cree les espaces pour la création de tout les personnages

        JLabel create1 = new JLabel("Character 1");
        JLabel create2 = new JLabel("Character 2");
        JLabel create3 = new JLabel("Character 3");
        JLabel create4 = new JLabel("Character 4");

        String[] choices = {"Healer (M)", "Mage (M)", "Sweeper (M)", "Tank (M)","Healer (F)", "Mage (F)", "Sweeper (F)", "Tank (F)"};
        JComboBox<String> classChoice1 = new JComboBox<String>(choices);
        JComboBox<String> classChoice2 = new JComboBox<String>(choices);
        JComboBox<String> classChoice3 = new JComboBox<String>(choices);
        JComboBox<String> classChoice4 = new JComboBox<String>(choices);

        JTextField enterName1 = new JTextField(15);
        JTextField enterName2 = new JTextField(15);
        JTextField enterName3 = new JTextField(15);
        JTextField enterName4 = new JTextField(15);

        JLabel enterYourName1 = new JLabel("Enter the name");
        JLabel enterYourName2 = new JLabel("Enter the name");
        JLabel enterYourName3 = new JLabel("Enter the name");
        JLabel enterYourName4 = new JLabel("Enter the name");

        JLabel choiceMessage1 = new JLabel("Select the class");
        JLabel choiceMessage2 = new JLabel("Select the class");
        JLabel choiceMessage3 = new JLabel("Select the class");
        JLabel choiceMessage4 = new JLabel("Select the class");

        JButton createButton = new JButton("Create the team");

        createButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (enterName1.getText().length() > 0) {
                    String name1 = enterName1.getText().toString();
                    String choice1 = classChoice1.getSelectedItem().toString();
                    party.addMember(name1, choice1, 0);
                }

                if (enterName2.getText().length() > 0) {
                    String name2 = enterName2.getText().toString();
                    String choice2 = classChoice2.getSelectedItem().toString();
                    party.addMember(name2, choice2, 1);
                }

                if (enterName3.getText().length() > 0) {
                    String name3 = enterName3.getText().toString();
                    String choice3 = classChoice3.getSelectedItem().toString();
                    party.addMember(name3, choice3, 2);
                }

                if (enterName4.getText().length() > 0) {
                    String name4 = enterName4.getText().toString();
                    String choice4 = classChoice4.getSelectedItem().toString();
                    party.addMember(name4, choice4, 3);
                }

                JOptionPane.showMessageDialog(null, "The team has been correctly created!");

                usedFrame.getContentPane().removeAll();
                usedFrame.setLayout(null);

                JPanel screen = new JPanel();
                screen.setBackground(Color.BLACK);
                screen.setLayout(new GridBagLayout());
                JLabel text = new JLabel("A KrisGAMES Production");
                text.setForeground(Color.WHITE);
                screen.add(text);
                
                screen.setSize(600, 600);

                usedFrame.getContentPane().add(screen);
                usedFrame.repaint();
                usedFrame.revalidate();

                start.setValue(1);
            }
        });

        JPanel creationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(0, 10, 10, 10);

        // Creation du personnage 1
        constraints.gridx = 0;
        constraints.gridy = 0;
        creationPanel.add(create1, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        creationPanel.add(enterYourName1, constraints);

        constraints.gridx = 1;
        creationPanel.add(enterName1, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        creationPanel.add(choiceMessage1, constraints);

        constraints.gridx = 1;
        creationPanel.add(classChoice1, constraints);

        // Creation du personnage 2
        constraints.insets = new Insets(20, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 3;
        creationPanel.add(create2, constraints);

        constraints.insets = new Insets(0, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 4;
        creationPanel.add(enterYourName2, constraints);

        constraints.gridx = 1;
        creationPanel.add(enterName2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        creationPanel.add(choiceMessage2, constraints);

        constraints.gridx = 1;
        creationPanel.add(classChoice2, constraints);

        // Creation du personnage 3
        constraints.insets = new Insets(20, 10, 10, 10);

        constraints.gridy = 6;
        constraints.gridx = 0;
        creationPanel.add(create3, constraints);

        constraints.insets = new Insets(0, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 7;
        creationPanel.add(enterYourName3, constraints);

        constraints.gridx = 1;
        creationPanel.add(enterName3, constraints);

        constraints.gridx = 0;
        constraints.gridy = 8;
        creationPanel.add(choiceMessage3, constraints);

        constraints.gridx = 1;
        creationPanel.add(classChoice3, constraints);

        // Creation du personnage 4
        constraints.insets = new Insets(20, 10, 10, 10);

        constraints.gridy = 9;
        constraints.gridx = 0;
        creationPanel.add(create4, constraints);

        constraints.insets = new Insets(0, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 10;
        creationPanel.add(enterYourName4, constraints);

        constraints.gridx = 1;
        creationPanel.add(enterName4, constraints);

        constraints.gridx = 0;
        constraints.gridy = 11;
        creationPanel.add(choiceMessage4, constraints);

        constraints.gridx = 1;
        creationPanel.add(classChoice4, constraints);

        // Bouton de validation des choix

        creationPanel.add(new JLabel(" "));
        creationPanel.add(createButton);

        usedFrame.setContentPane(creationPanel);
        usedFrame.repaint();
        usedFrame.revalidate();

        JOptionPane.showMessageDialog(null, "When creating your team, keep this in mind :\nSweepers have High Base Attack and Speed but can't take a lot of damage\nTanks have Very High Base HP and Defense but Low Base Speed\nMages' attacks are not affected by the enemy's defense when attacking but are very fragile\nHealers can deal low damage to enemies or heal allies");
    }

    public static void soloAddMembers(JFrame mainFrame, Party p, Utilitaries u) {
        Party party = p;

        JFrame usedFrame = mainFrame;
        usedFrame.getContentPane().removeAll();
        usedFrame.repaint();

        JLabel create1 = new JLabel("Your character");
        String[] choices = {"Healer (M)", "Mage (M)", "Sweeper (M)", "Tank (M)","Healer (F)", "Mage (F)", "Sweeper (F)", "Tank (F)"};
        JComboBox<String> classChoice1 = new JComboBox<String>(choices);
        JTextField enterName1 = new JTextField(15);
        JLabel enterYourName1 = new JLabel("Enter your name");
        JLabel choiceMessage1 = new JLabel("Select your class");
        JButton createButton = new JButton("Start the game");

        createButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (enterName1.getText().length() > 0) {
                    String name1 = enterName1.getText().toString();
                    String choice1 = classChoice1.getSelectedItem().toString();
                    party.addMember(name1, choice1, 0);

                    JOptionPane.showMessageDialog(null, "I wish you good luck. Trust me, you're going to need it.");

                    usedFrame.getContentPane().removeAll();
                    usedFrame.setLayout(null);

                    JPanel screen = new JPanel();
                    screen.setBackground(Color.BLACK);
                    screen.setLayout(new GridBagLayout());
                    JLabel text = new JLabel("A KrisGAMES Production");
                    text.setForeground(Color.WHITE);
                    screen.add(text);
                    
                    screen.setSize(600, 600);

                    usedFrame.getContentPane().add(screen);
                    usedFrame.repaint();
                    usedFrame.revalidate();

                    u.setValue(1);
                }
                else {
                    JOptionPane.showMessageDialog(null, "You must choose a name. Everyone has a name...");
                }
            }
        });

        JPanel creationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(0, 10, 10, 10);

        // Creation du personnage 1
        constraints.gridx = 0;
        constraints.gridy = 0;
        creationPanel.add(create1, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        creationPanel.add(enterYourName1, constraints);

        constraints.gridx = 1;
        creationPanel.add(enterName1, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        creationPanel.add(choiceMessage1, constraints);

        constraints.gridx = 1;
        creationPanel.add(classChoice1, constraints);

        // Bouton de validation des choix

        constraints.gridx = 1;
        constraints.gridy = 0;
        creationPanel.add(new JLabel(" "));
        
        constraints.gridx = 2;
        creationPanel.add(createButton);

        usedFrame.setContentPane(creationPanel);
        usedFrame.repaint();
        usedFrame.revalidate();

        JOptionPane.showMessageDialog(null, "In Story Mode, you can only have one member in your team when you start. Other allies will join you as the story progress.\n\nWhen creating your character, keep this in mind :\nSweepers have High Base Attack and Speed but can't take a lot of damage\nTanks have Very High Base HP and Defense but Low Base Speed\nMages' attacks are not affected by the enemy's defense when attacking but are very fragile\nHealers can deal low damage to enemies or heal allies");
    }

    public void addItem(Item i) {
        System.out.println("You have received " + i.getName() + ".\n");
        this.inventory.addItem(i);
    }

    public void addWeapon(Weapon w) {
        System.out.println("You have received " + w.toString() + ".\n");
        this.armory.add(w);
    }

    // Accesseurs aux éléments d'un objet de la class Party

    public ArrayList<Character> getTeam() { return this.Team; }

    public Inventory getInventory() { return this.inventory; }

    public ArrayList<Weapon> getArmory() { return this.armory; }

    // Savoir si l'équipe entière a été vaincu

    public Boolean haveLost() {
        for (Character c : this.Team) {
            if (c.isDead == false) {
                return false;
            }
        }
        return true;
    }

    // Accès au niveau moyen de l'équipe

    public int getAverageLevel() {
        int l = 0;
        for (Character c : this.Team) {
            l = l + c.getLEVEL();
        }
        return (int)(l / this.Team.size());
    }

    // Méthode toString de la classe

    public String toString() {
        String text = "";
        for (Character c : this.Team) {
            text = text + c.toString() + "\n";
        }
        return text;
    }

    // Equiper une arme sur un personnage si c'est possible

    public void equipWeapon(Weapon w) {
        if (this.physicalFighters.size() == 0) { System.out.println("No one in your team can hold a weapon. The weapon will be stored in your armory.\n"); this.addWeapon(w); }
        else {
            System.out.println("Who should hold the weapon?\n");
            int n = 1;
            for (Physical p : this.physicalFighters) {
                System.out.println(p.getName() + " (" + n + ")");
                n++;
            }
            Scanner scan = new Scanner(System.in);
            System.out.println(); int choice = scan.nextInt(); System.out.println();

            while ((choice <= 1) && (choice > this.physicalFighters.size())) {
                System.out.println("I don't understand. Could you repeat ?\n");
                for (Physical p : this.physicalFighters) {
                    System.out.println(p.getName() + " (" + n + ")");
                    n++;
                }
                System.out.println(); choice = scan.nextInt(); System.out.println();
            }

            if (this.physicalFighters.get(choice).getWeapon() != null) {
                Weapon ret = this.physicalFighters.get(choice).takeWeapon();
                this.addWeapon(ret);
                this.physicalFighters.get(choice).giveWeapon(w);
            }
            else {
                this.physicalFighters.get(choice).giveWeapon(w);
            }
        }
    }

    // Soigner l'équipe

    public void healTeam() {
        for (Character c : this.Team) {
            c.setActualHP(c.getHP());
        }
    }

    public Boolean isTeamAtFullHP() {
        for (int i = 0; i < this.Team.size(); i++) {
            if (this.Team.get(i).getActualHP() == this.Team.get(i).getHP()) {}
            else return false;
        }
        return true;
    }
}