
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;

public class TestGameFrame {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("THE GAME");
        mainFrame.setVisible(true);
        mainFrame.setSize(600, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        JLabel label = new JLabel();
        label.setText("Centered");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);
        panel.add(label);

        mainFrame.setContentPane(panel);
        mainFrame.repaint();
        mainFrame.revalidate();

        try { Thread.sleep(1000); } catch (InterruptedException e) { System.out.println("Salut"); }

        Party p = new Party();
        p.addMembers(mainFrame);

        /*JFrame mainFrame = new JFrame("The Game");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel startPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        JButton b1 = new JButton("Start the game");
        c.gridx = 3;
        c.gridy = 0;
        startPanel.add(b1, c);

        JButton b2 = new JButton("Something else");
        c.gridx = 4;
        c.gridy = 0;
        startPanel.add(b2, c);

        JButton b3 = new JButton("I'm just a test");
        c.gridx = 3;
        c.gridy = 1;
        startPanel.add(b3, c);

        JLabel label = new JLabel("Hello Roy and Chrom", JLabel.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        startPanel.add(label, c);

        JTextField enterYourName = new JTextField();

        mainFrame.add(startPanel);

        mainFrame.setVisible(true);*/
    }
}