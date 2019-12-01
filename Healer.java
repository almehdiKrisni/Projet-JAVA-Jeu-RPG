public class Healer extends Magic {
    private final String subClass = "Healer";

    public Healer(String name) {
        super(name);
        this.HP = (int)(this.HP * 1.2);
        this.actualHP = this.HP;
        this.DEF = (int)(this.HP * 0.7);
        this.LUCK = (int)(this.LUCK * 3);
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
        return "Name - " + this.Name + "\tLEVEL - " + this.LEVEL + "\nClass - " + this.MainClass + "\tSpecialized Class - " + this.subClass + "\nHP  - " + this.actualHP + "/" + this.HP +"\tATK - " + this.ATK + "\tSPD - " + this.SPEED + "\nDEF - " + this.DEF + " \tLCK - " + this.LUCK + " \t(" + this.EXP + "/" + this.neededEXP + ")\n";
    }
}