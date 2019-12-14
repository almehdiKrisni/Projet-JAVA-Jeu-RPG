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

        try { Thread.sleep(3000); } catch (InterruptedException e) { System.out.println("Error"); }

        if (choice.getValue() == 1) {
            StoryWindow sw1 = new StoryWindow();
            sw1.ch1(mainFrame, p);
        }

        else {
            String[] backgrounds = {"Space", "Forest", "UPMC_Couloir", "Bibli", "City", "Restaurant"};
            String[] bossFights = {"Shrek", "Armored Beast", "Dragon", "Death"};

            for (int i = 1; i <= 12; i++) {
                // Il existe differentes type de fonds d'écran pour les combats (il faut remplacer le mot avant '_Background.png' dans les paramètres)
                // On peut choisir entre : Space, Forest, UPMC_Couloir, ...

                Fight f1;
                Utilitaries u = new Utilitaries();

                if ((i % 3) != 0) {
                    f1 = new Fight(p, new Enemies(p.getAverageLevel(), 4), u);
                }
                else {
                    f1 = new Fight(p, new Enemies(p.getAverageLevel(), bossFights[(i / 3) - 1]), u);
                }

                int r = (int)(Math.random() * backgrounds.length);
                f1.fightOnScreen(f1, mainFrame, "Images/" + backgrounds[r] + "_Background.png");

                while (u.getValue() == 0) {};

                try { Thread.sleep(3000); } catch (InterruptedException e) { System.out.println("Error"); }
            }
        }
    }
}