import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Inventory {
    private int Size;
    private ArrayList<Item> Stuff;

    private static final int limitSize = 20;

    public Inventory() {
        this.Size = 0;
        this.Stuff = new ArrayList<Item>();
    }

    // Accesseurs aux variables

    public int getSize() { return this.Size; }

    public ArrayList<Item> getStuff() { return this.Stuff; }

    // Modificateurs de valeurs

    public void addItem(Item item) {
        if (this.Size == limitSize) {
            JOptionPane.showMessageDialog(null, "You have found a " + item.getName() + ".\nBut your inventory is full...");
        }
        else {
            this.Stuff.add(item);
            this.Size = this.Stuff.size();
            JOptionPane.showMessageDialog(null, "You have received " + item.getName() + ".\n" + item.toString());
        }
    }

    public void useItem(Item i) {
        this.Stuff.remove(i);
        this.Size = this.Stuff.size();
    }

    // Affichage de l'inventaire

    public String toString() {
        ArrayList<Item> liste = new ArrayList<Item>();
        for (Item i : this.Stuff) {
            if (liste.contains(i)) continue;
            else liste.add(i);
        }
        String text = "Inventory size - " + this.Size + "\n";
        for (Item i : liste) {
            int n = 0;
            for (Item e : this.Stuff) {
                if (i.getName() == e.getName()) n++;
            }
            text = text + i.getName() + " x " + n + "\n";
        }
        return text;
    }

    // On récupère un objet via son nom et on le retire également de la meme maniere (lors des combats)

    public Item findItem(String name) {
        for (Item i : this.Stuff) {
            if (i.getName() == name) return i;
        }
        return null;
    }

    public void deleteItem(String name) {
        for (Item i : this.Stuff) {
            if (i.getName() == name) this.Stuff.remove(i);
        }
    }
}