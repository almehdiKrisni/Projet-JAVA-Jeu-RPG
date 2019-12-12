public class Item {
    private String name;
    private int HPrecovery;
    private String desc;

    // Le constructeur des objets servant à récupérer des HP ou réanimer des alliés

    public Item(String name, int level, int recovery) {
        this.name = name + " (Level " + level + ")"; 
        this.HPrecovery = recovery + (int)(recovery * level * 0.5);
        this.desc = "Allow a character to recover " + this.HPrecovery + " HP.\n";
    }

    // Méthodes d'accès aux valeurs

    public int getHPRecovery() { return this.HPrecovery; }

    public String getName() { return this.name; }

    public String getDesc() { return this.desc; }

    // Méthode toString de l'objet

    public String toString() { return this.name; }

    public String info() {
        return "Item name - " + this.name + "\nAllow a character to recover " + this.HPrecovery + " HP.\n";
    }
}