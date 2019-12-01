import java.util.ArrayList;

public class Inventory {
    private int Size;
    private ArrayList<Item> Stuff;

    public Inventory() {
        this.Size = 0;
        this.Stuff = new ArrayList<Item>();
    }

    // Accesseurs aux variables

    public int getSize() { return this.Size; }

    public ArrayList<Item> getStuff() { return this.Stuff; }

    // Modificateurs de valeurs

    public void addItem(Item item) {
        this.Stuff.add(item);
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
}