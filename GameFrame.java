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

public class GameFrame extends JFrame {

    public GameFrame() {
        super.setTitle("The Game");
        super.setSize(600, 600);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }
}