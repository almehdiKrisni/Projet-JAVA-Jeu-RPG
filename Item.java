public abstract class Item {
    private String name;
    private int HPrecovery;

    // Le constructeur des objets servant à récupérer des HP ou réanimer des alliés

    public Item(String name, int level, int recovery) {
        this.name = name + " (Level " + level + ")"; 
        this.HPrecovery = recovery * level;
    }

    // Méthodes d'accès aux valeurs

    public int getHPRecovery() { return this.HPrecovery; }

    public String getName() { return this.name; }

    // Méthode toString de l'objet

    public String toString() { return this.name; }

    public String info() {
        return "Item name - " + this.name + "\nAllow a character to recover " + this.HPrecovery + " HP.\n";
    }
}