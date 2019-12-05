public class Sweeper extends Physical {
    protected final String subClass = "Sweeper";

    public Sweeper(String name) {
        super(name);
        this.ATK = (int)(this.ATK * 1.3);
        this.SPEED = (int)(this.SPEED * 1.5);
        this.DEF = (int)(this.DEF * 0.8);
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

    // Méthode pour les montées de niveau

    public void levelUP() {
        this.neededEXP = this.neededEXP + 50;
        
        System.out.println(this.Name + " has leveled up!");
        int bhp = (int)(Math.random() * 3);
        this.HP = this.HP + bhp;
        this.actualHP = this.actualHP + bhp;
        System.out.println("HP  - " + this.HP + "\t(+" + bhp + ")");

        int batk = (int)(Math.random() * 2);
        this.ATK = this.ATK + batk;
        System.out.println("ATK - " + this.ATK + "\t(+" + batk + ")");

        int bdef = (int)(Math.random() * 2);
        this.DEF = this.DEF + bdef;
        System.out.println("DEF - " + this.DEF + "\t(+" + bdef + ")");

        int bspd = (int)(Math.random() * 3);
        this.SPEED = this.SPEED + bspd;
        System.out.println("SPD - " + this.SPEED + "\t(+" + bspd + ")");

        System.out.println();
    }
}