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
        return "Name - " + this.Name + "\tLEVEL - " + this.LEVEL + "\nClass - " + this.MainClass + "\tSpecialized Class - " + this.subClass + "\nHP  - " + this.actualHP + "/" + this.HP +"\tATK - " + this.ATK + "\tSPD - " + this.SPEED + "\nDEF - " + this.DEF + " \tLCK - " + this.LUCK + " \t(" + this.EXP + "/" + this.neededEXP + ")\n";
    }

    // Action en combat du personnage

    public void actionCombat() {
        return;
    }
}