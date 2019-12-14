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

public class GameImage extends JLabel {

    public GameImage() {
        super();
    }

    public JLabel ImageMenu(JFrame mainFrame, Party p, Utilitaries uti, Utilitaries start) {
        Utilitaries u = uti;

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
                p.addThreeMembers(mainFrame, start, p);
                System.out.println("Story Mode");
                u.setValue(1);
            }
        });
        
        label.add(startButton, c1);

        // Bouton pour commencer le mode Versus
        JButton versusButton = new JButton("Versus Mode");
        versusButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                p.addMembers(mainFrame, start, p);
                System.out.println("Versus Mode");
                u.setValue(2);
            }
        });

        c1.gridx = 1;
        label.add(versusButton, c1);

        return label;
    }
}