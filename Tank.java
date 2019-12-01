public class Tank extends Physical {
    private final String subClass = "Tank";

    public Tank(String name) {
        super(name);
        this.HP = (int)(this.HP * 1.6);
        this.actualHP = this.HP;
        this.DEF = (int)(this.DEF * 2.2);
        this.ATK = (int)(this.ATK * 0.8);
        this.SPEED = (int)(this.SPEED * 0.5);
    }

    // Partie de récupération des informations des personnages

    public String getSubClass() { return this.subClass; }

    // Partie de l'affichage des personnages

    public String toString() {
        String text = "Name - " + this.Name + "\tLEVEL - " + this.LEVEL + "\nClass - " + this.MainClass + "\tSpecialized Class - " + this.subClass + "\nHP  - " + this.actualHP + "/" + this.HP +"\tATK - " + this.ATK + "\tSPD - " + this.SPEED + "\nDEF - " + this.DEF + " \tLCK - " + this.LUCK + " \t(" + this.EXP + "/" + this.neededEXP + ")\n";
        if (this.getWeapon() != null) {
            return text + "Equiped Weapon - " + this.getWeapon().getName() +"\n";
        }
        else {
            return text + "Equiped Weapon - None\n";
        }
    }
}