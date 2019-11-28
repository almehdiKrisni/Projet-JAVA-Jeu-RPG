public abstract class Physical extends Personnage {
    private final String MainClass = "Weapon User";
    private Weapon weapon;

    public Physical(String name) {
        super(name);
        this.weapon = null;
    }

    // Partie de récupération des informations des personnages

    public String getMainClass() { return this.MainClass; }

    public Weapon getWeapon() {
        return this.weapon;
    }

    // Action en combat du personnage

    public abstract void actionCombat();

    // Partie d'affichage des informations des personnages

    public abstract String toString();

}