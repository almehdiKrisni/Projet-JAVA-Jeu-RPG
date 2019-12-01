public class HealingItem extends Item {
    private int HPrecovery;

    // Le constructeur des objets servant à récupérer des HP ou réanimer des alliés

    public HealingItem(String name, int level, int recovery) {
        super(name + " (Level " + level + ")");
        this.HPrecovery = recovery * level;
    }

    // Méthodes d'accès aux valeurs

    public int getHPRecovery() { return this.HPrecovery; }

    // Méthode toString de l'objet

    public String toString() {
        return "Item name - " + this.Name + "\nAllow a character to recover " + this.HPrecovery + " HP.\n";
    }
}