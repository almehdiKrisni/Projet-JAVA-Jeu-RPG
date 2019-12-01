public class Weapon {
    private int Might;
    private int Weigth;
    private String Name;
    private String Description;

    public Weapon(int might, int weigth, String name, String desc) {
        this.Might = might;
        this.Weigth = weigth;
        this.Name = name;
        this.Description = desc;
    }

    // Partie de récupération des informations des armes

    public int getMigth() { return this.Might; }

    public int getWeigth() { return this.Weigth; }

    public String getName() { return this.Name;}

    public String getDescription() { return this.Description; }

    // Affichage des informations des armes

    public String toString() { return "Weapon Name - " + this.Name + "\n" + this.Description + "\nMight - " + this.Might + "\tWeigth - " + this.Weigth + "\n"; }
    
}