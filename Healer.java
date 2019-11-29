public class Healer extends Magic {
    private final String subClass = "Healer";

    public Healer(String name) {
        super(name);
    }

    // Partie de récupération des informations des personnages

    public String getSubClass() {
        return this.subClass;
    }

    // Action en combat du personnage

    public void actionCombat() {
        return;
    }

    // Partie d'affichage des personnages

    public String toString() {
        return "Name - " + this.getName() + "\nClass - " + this.getMainClass() + "\tSpecialized Class - " + this.getSubClass() + "\nLVL  - " + this.getLEVEL() + "\tEXP - " + this.getEXP() + "/" + this.getNeededEXP() + "\nHthP - " + this.getHP() + "\tATK - " + this.getATK() + "\nLUCK - " + this.getLUCK() + "\tDEF - " + this.getDEF() + "\n";
    }
}