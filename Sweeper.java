import javax.swing.JLabel;
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

public class Sweeper extends Physical {
    protected final String subClass = "Sweeper";

    public Sweeper(String name) {
        super(name);
        this.ATK = (int)(this.ATK * 1.3);
        this.SPEED = (int)(this.SPEED * 1.5);
        this.DEF = (int)(this.DEF * 0.8);
    }

    public Sweeper(String name, String fileImage) {
        super(name);
        this.ATK = (int)(this.ATK * 1.5);
        this.SPEED = (int)(this.SPEED * 1.5);
        this.LUCK = 3;

        this.imageN = new JLabel(new ImageIcon("Images/" + name + "_Sprite.png"));
    }

    // Partie de récupération des informations des personnages

    public String getSubClass() { return this.subClass; }

    // Partie de l'affichage des personnages

    public String toString() {
        String text = "Name - " + this.Name + "\tLEVEL - " + this.LEVEL + "\nClass - " + this.MainClass + "\tSpecialized Class - " + this.subClass + "\nHP  - " + this.actualHP + "/" + this.HP +"       ATK - " + this.ATK + "       SPD - " + this.SPEED + "\nDEF - " + this.DEF + "       LCK - " + this.LUCK + "       (" + this.EXP + "/" + this.neededEXP + ")\n";
        if (this.getWeapon() != null) {
            return text + "Equiped Weapon - " + this.getWeapon().getName() +"\n";
        }
        else {
            return text + "Equiped Weapon - None\n";
        }
    }

    // Méthode pour les montées de niveau

    public void levelUP() {
        this.neededEXP = this.neededEXP + 50;
        
        System.out.println(this.Name + " has leveled up!");
        int bhp = (int)(Math.random() * 3);
        this.HP = this.HP + bhp;
        this.actualHP = this.actualHP + bhp;
        System.out.println("HP  - " + this.HP + "\t(+" + bhp + ")");

        int batk = (int)(Math.random() * 2);
        this.ATK = this.ATK + batk;
        System.out.println("ATK - " + this.ATK + "\t(+" + batk + ")");

        int bdef = (int)(Math.random() * 2);
        this.DEF = this.DEF + bdef;
        System.out.println("DEF - " + this.DEF + "\t(+" + bdef + ")");

        int bspd = (int)(Math.random() * 3);
        this.SPEED = this.SPEED + bspd;
        System.out.println("SPD - " + this.SPEED + "\t(+" + bspd + ")");

        System.out.println();
    }

    public String levelUpForWindow() {
        this.EXP = 0;
        this.neededEXP = (int)(this.neededEXP * 1.5);

        String text = this.Name + " has leveled up!\n";
        int bhp = (int)(Math.random() * 3);
        this.HP = this.HP + bhp;
        this.actualHP = this.actualHP + bhp;
        text = text + "HP  - " + this.HP + "\t(+" + bhp + ")\n";

        int batk = (int)(Math.random() * 3);
        this.ATK = this.ATK + batk;
        text = text + "ATK - " + this.ATK + "\t(+" + batk + ")\n";

        int bdef = (int)(Math.random() * 2);
        this.DEF = this.DEF + bdef;
        text = text + "DEF - " + this.DEF + "\t(+" + bdef + ")\n";

        int bspd = (int)(Math.random() * 3);
        this.SPEED = this.SPEED + bspd;
        text = text + "SPD - " + this.SPEED + "\t(+" + bspd + ")\n";

        return text;
    }
}