public class Mage extends Magic {
    private final String subClass = "Mage";

    public Mage(String name) {
        super(name);
    }

    // Partie de récupération des informations des personnages

    public String getSubClass() {
        return this.subClass;
    }

    // Partie d'affichage des personnages

    public String toString() {
        return "Name - " + this.getName() + "\nClass - " + this.getMainClass() + "\tSpecialized Class - " + this.subClass + "\nLVL  - " + this.getLEVEL() + "\tEXP - " + this.getEXP() + "/" + this.getNeededEXP() + "\nHthP - " + this.getHP() + "\tATK - " + this.getATK() + "\nLUCK - " + this.getLUCK() + "\tDEF - " + this.getDEF() + "\n";
    }

    // Action en combat du personnage

    public void actionCombat() {
        return;
    }
}