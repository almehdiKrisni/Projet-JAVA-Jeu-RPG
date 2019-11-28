public class Weapon {
    private int Might;
    private int Weigth;
    private String Name;

    public Weapon(int might, int weigth, String name) {
        this.Might = might;
        this.Weigth = weigth;
        this.Name = name;
    }

    // Partie de récupération des informations des armes

    public int getMigth() { return this.Might; }

    public int getWeigth() { return this.Weigth; }

    public String getName() { return this.Name;}

    // Affichage des informations des armes

    public String toString() { return "Weapon Name - " + this.Name +"\nMight - " + this.Might + "\tWeigth - " + this.Weigth + "\n"; }
    
}