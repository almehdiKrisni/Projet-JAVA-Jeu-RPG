public class Sweeper extends Physical {
    private final String subClass = "Sweeper";

    public Sweeper(String name) {
        super(name);
    }

    // Partie de récupération des informations des personnages

    public String getSubClass() { return this.subClass; }

    // Action en combat du personnage

    public void actionCombat() {
        return;
    }

    // Partie de l'affichage des personnages

    public String toString() {
        return "Name - " + this.getName() + "\nClass - " + this.getMainClass() + "\tSpecialized Class - " + this.getSubClass() + "\nLVL  - " + this.getLEVEL() + "\tEXP - " + this.getEXP() + "/" + this.getNeededEXP() + "\nHthP - " + this.getHP() + "\tATK - " + this.getATK() + "\nLUCK - " + this.getLUCK() + "\tDEF - " + this.getDEF() + "\n";
    }
}