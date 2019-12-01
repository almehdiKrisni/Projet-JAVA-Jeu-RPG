public class Tank extends Physical {
    private final String subClass = "Tank";

    public Tank(String name) {
        super(name);
    }

    // Partie de récupération des informations des personnages

    public String getSubClass() { return this.subClass; }

    // Partie de l'affichage des personnages

    public String toString() {
        String text = "Name - " + this.Name + "\tLEVEL - " + this.LEVEL + "\nClass - " + this.MainClass + "\tSpecialized Class - " + this.subClass + "\nHP  - " + this.HP + "\tATK - " + this.ATK + "\tSPD - " + this.SPEED + "\nDEF - " + this.DEF + " \tLCK - " + this.LUCK + " \t(" + this.EXP + "/" + this.neededEXP + ")\n";
        if (this.getWeapon() != null) {
            return text + "Equiped Weapon - " + this.getWeapon().getName() + " (Migth - " + this.getWeapon().getMigth() + " , Weigth - " + this.getWeapon().getWeigth() + ")\n";
        }
        else {
            return text + "Equiped Weapon - None\n";
        }
    }
}